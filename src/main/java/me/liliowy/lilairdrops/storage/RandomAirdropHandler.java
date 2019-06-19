package me.liliowy.lilairdrops.storage;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.objects.Airdrop;
import me.liliowy.lilairdrops.objects.Flare;
import me.liliowy.lilairdrops.util.Formatting;
import me.liliowy.lilairdrops.util.ItemHandler;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RandomAirdropHandler {

    private LilAirdrops instance;
    private FileConfiguration config;
    private Airdrop randomAirdrop;
    private boolean useRandomAirdrops;
    private Map<String, Integer> chances;

    public RandomAirdropHandler(){
        instance = LilAirdrops.getInstance();
        config = instance.getRandomAirdropFile().getConfig();
        chances = new HashMap<>();
        load();
    }

    public void load(){
        useRandomAirdrops = config.contains("Settings.use-random-airdrops") ? config.getBoolean("Settings.use-random-airdrops") : false;
        randomAirdrop = new Airdrop("Random#" + UUID.randomUUID().toString());
        randomAirdrop.setIntervalMax(config.getLong("Settings.interval-max"));
        randomAirdrop.setIntervalMin(config.getLong("Settings.interval-max"));

        if (config.contains("Airdrop-Chances")){
            for (String airdrop : config.getConfigurationSection("Airdrop-Chances").getKeys(false)){
                if (instance.getAirdropHandler().getAirdropTypes().containsKey(airdrop)){
                    chances.put(airdrop, config.getInt("Airdrop-Chances." + airdrop));
                }
            }
        }

        if (randomAirdrop != null){
            ItemStack flareItem = ItemHandler.deserializeItemStack("random.yml", config.getConfigurationSection("Flare"));
            Flare flare = new Flare(randomAirdrop.getType(), flareItem);

            if (config.contains("Flare.trail-particle")) flare.setTrailParticle(Particle.valueOf(config.getString("Flare.trail-particle")));
            if (config.contains("Flare.trail-particle-data")) flare.setTrailParticleData(config.getString("Flare.trail-particle-data"));
            if (config.contains("Flare.explosion-particle")) flare.setExplosionParticle(Particle.valueOf(config.getString("Flare.explosion-particle")));
            if (config.contains("Flare.explosion-particle-data")) flare.setExplosionParticleData(config.getString("Flare.explosion-particle-data"));

            randomAirdrop.setFlare(flare);
            instance.sendConsoleMessage(Formatting.format("[LilAirdrops] &eThe random airdrop file has been loaded."));
        }
    }

    public void save(){
        Map<String, Object> itemData = ItemHandler.serializeItemStack(randomAirdrop.getFlare().getItem());

        config.set("Settings.use-random-airdrops", useRandomAirdrops);
        config.set("Settings.interval-max", randomAirdrop.getIntervalMax());
        config.set("Settings.interval-min", randomAirdrop.getIntervalMin());

        for (String section : itemData.keySet()){
            section.replaceAll(".type", "").replaceAll(".duration", "").replaceAll(".ambient", "").replaceAll(".particles", "").replaceAll(".icon", "");
            section.replaceAll(".flicker", "").replaceAll(".trail", "").replaceAll(".fade-colors", "").replaceAll(".colors", "");
            config.set("Flare." + section, itemData.get(section));
        }

        for (String airdrop : chances.keySet()){
            config.set("Airdrop-Chances." + airdrop, chances.get(airdrop));
        }

        config.set("Flare.trail-particle", randomAirdrop.getFlare().getTrailParticle().toString());
        config.set("Flare.trail-particle-data", randomAirdrop.getFlare().getTrailParticleData());
        config.set("Flare.explosion-particle", randomAirdrop.getFlare().getExplosionParticle().toString());
        config.set("Flare.explosion-particle-data", randomAirdrop.getFlare().getExplosionParticleData());

        instance.getRandomAirdropFile().save();
        instance.sendConsoleMessage(Formatting.format("[LilAirdrops] &eThe random airdrop file was saved."));
    }

    public Airdrop getRandomAirdrop(){
        return randomAirdrop;
    }

    public void setRandomAirdrop(Airdrop airdrop){
        randomAirdrop = airdrop;
    }

    public Map<String, Integer> getChances(){
        return chances;
    }

    public boolean isEnabled(){
        return useRandomAirdrops;
    }

    public void setEnabled(boolean useRandomAirdrops){
        this.useRandomAirdrops = useRandomAirdrops;
    }
}
