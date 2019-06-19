package me.liliowy.lilairdrops.storage;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.objects.Airdrop;
import me.liliowy.lilairdrops.objects.Flare;
import me.liliowy.lilairdrops.util.AnnouncementLocation;
import me.liliowy.lilairdrops.util.Formatting;
import me.liliowy.lilairdrops.util.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirdropHandler {

    private LilAirdrops instance;
    private List<Airdrop> currentAirdrops;
    private Map<String, Airdrop> airdropTypes;

    public AirdropHandler(){
        instance = LilAirdrops.getInstance();
        currentAirdrops = new ArrayList<>();
        airdropTypes = new HashMap<>();
        load();
    }

    public void load(){
        int count = 0;

        for (LilFile file : instance.getAirdropFiles()){
            count+=1;
            FileConfiguration config = file.getConfig();
            Airdrop airdrop = new Airdrop(file.getFile().getName().replaceAll(".yml", ""));
            airdrop.setPermission(config.getString("Settings.permission"));
            airdrop.setIntervalMax(config.getLong("Settings.interval-max"));
            airdrop.setIntervalMin(config.getLong("Settings.interval-min"));
            airdrop.setRemovalTime(config.getLong("Settings.remove-after"));
            airdrop.setShouldDestroyOnClose(config.getBoolean("Settings.destroy-on-close"));
            airdrop.setShouldDropItemsOnClose(config.getBoolean("Settings.drop-on-close"));
            airdrop.setShouldItemsGoToInventoryOnClose(config.getBoolean("Settings.inventory-on-close"));
            airdrop.setShouldItemsDropWithFullInventoryOnClose(config.getBoolean("Settings.full-inventory-drop-on-close"));
            airdrop.setCanDropInWater(config.getBoolean("Settings.can-drop-in-water"));
            airdrop.setCanDropInLava(config.getBoolean("Settings.can-drop-in-lava"));
            airdrop.setSlowMultiplier(config.getInt("Settings.slowness-multiplier"));
            airdrop.setCanOthersOpen(config.getBoolean("Settings.can-others-open"));
            airdrop.setAnnouncementMessage(config.getString("Settings.announcement-message"));
            airdrop.setAnnouncementLocation(AnnouncementLocation.valueOf(config.getString("Settings.announcement-location")));
            airdrop.setShouldAnnounceToOtherWorlds(config.getBoolean("Settings.announce-to-other-worlds"));
            airdrop.setMaterial(Material.valueOf(config.getString("Settings.block")));
            airdrop.setFallingParticle(Particle.valueOf(config.getString("Settings.falling-particle")));
            airdrop.setOnLandParticle(Particle.valueOf(config.getString("Settings.on-land-particle")));
            airdrop.setLandedParticle(Particle.valueOf(config.getString("Settings.landed-particle")));
            airdrop.setHologramText(config.getString("Settings.hologram-text"));
            airdrop.setShouldUseGUI(config.getBoolean("Settings.use-gui"));
            airdrop.setGUITitle(config.getString("Settings.gui-title"));
            airdrop.setShouldUseOffsetWithFlare(config.getBoolean("Settings.use-flare-offset"));

            for (String content : config.getConfigurationSection("Inventory-Contents").getKeys(false)){
                if (config.getString("Inventory-Contents." + content + ".command") != null){
                    airdrop.getPossibleCommands().put(config.getString("Inventory-Contents." + content + ".command"), config.getInt("Inventory-Contents." + content + ".chance"));
                } else if (config.getString("Inventory-Contents." + content + ".item") != null){
                    ItemStack item = ItemHandler.deserializeItemStack(file.toString(), config.getConfigurationSection("Inventory-Contents." + content + ".item"));
                    int chance = config.getInt("Inventory-Contents." + content + ".item.chance");
                    airdrop.getPossibleItems().put(item, chance);
                }
            }

            ItemStack flareItem = ItemHandler.deserializeItemStack(file.getFile().getName(), config.getConfigurationSection("Flare"));
            Flare flare = new Flare(airdrop.getType(), flareItem);
            flare.setTrailParticle(Particle.valueOf(config.getString("Flare.trail-particle")));
            flare.setExplosionParticle(Particle.valueOf(config.getString("Flare.explosion-particle")));
            airdrop.setFlare(flare);

            airdrop.setWorld(Bukkit.getWorld(config.getString("Location.world")));
            airdrop.setMaximumBoundX(config.getInt("Location.max-bound-x"));
            airdrop.setMinimumBoundX(config.getInt("Location.min-bound-x"));
            airdrop.setMaximumBoundZ(config.getInt("Locastion.max-bound-z"));
            airdrop.setMinimumBoundZ(config.getInt("Location.min-bound-z"));
            airdrop.setMaximumBoundY(config.getInt("Location.max-bound-y"));
            airdrop.setOffset(config.getInt("Location.offset-from-announcement"));
            airdrop.setShouldBypassWorldGuardRegions(config.getBoolean("Hooks.worldguard.bypass-regions"));
            airdrop.setDisabledWorldGuardRegions(config.getStringList("Hooks.worldguard.disabled-regions"));
            airdrop.setEnabledWorldGuardRegions(config.getStringList("Hooks.worldguard.enabled-regions"));
            airdrop.setShouldDropOnIslands(config.getBoolean("Hooks.skyblock.drop-on-islands"));
            airdrop.setCanLockAirdrop(config.getBoolean("Hooks.lockette.lock-airdrop"));

            airdropTypes.put(file.getFile().getName().replaceAll(".yml", ""), airdrop);
        }

        for (String current : instance.getAirdropsFile().getConfig().getStringList("Airdrops")) {
            String[] currentSplit = current.split(",");
            String type = currentSplit[0];
            int x = Integer.parseInt(currentSplit[1]);
            int y = Integer.parseInt(currentSplit[2]);
            int z = Integer.parseInt(currentSplit[3]);
            World world = Bukkit.getWorld(currentSplit[4]);

            Airdrop airdrop = new Airdrop(type);
            for (String name : airdropTypes.keySet()) {
                if (type == name) {
                    airdrop = airdropTypes.get(name);
                    break;
                }
            }

            airdrop.setEndLocation(new Location(world, x, y, z));
            currentAirdrops.add(airdrop);
        }

        instance.sendConsoleMessage(Formatting.format("[LilAirdrops] &e" + count + " airdrop(s) have been loaded."));
    }

    public List<Airdrop> getCurrentAirdrops(){
        return currentAirdrops;
    }

    public Map<String, Airdrop> getAirdropTypes(){
        return airdropTypes;
    }
}
