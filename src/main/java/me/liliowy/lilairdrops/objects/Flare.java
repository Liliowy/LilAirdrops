package me.liliowy.lilairdrops.objects;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.events.AirdropFlareFireEvent;
import me.liliowy.lilairdrops.util.SpawnType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Flare {

    private String airdropType;
    private ItemStack item;
    private Particle trailParticle;
    private String trailParticleData;
    private Particle explosionParticle;
    private String explosionParticleData;
    private Item droppedItem;

    public Flare(String airdropType, ItemStack item){
        this.airdropType = airdropType;
        this.item = item;
    }

    public void fire(Location location){
        AirdropFlareFireEvent flareFireEvent = new AirdropFlareFireEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(flareFireEvent);

        if (!flareFireEvent.isCancelled()){
            emitParticles(location);
        }
    }

    private void callAirdrop(){
        LilAirdrops.getInstance().getAirdropHandler().getAirdropTypes().get(airdropType).summon(SpawnType.FLARE);
    }

    private void emitParticles(Location location){
        droppedItem = location.getWorld().dropItemNaturally(location, item);

        BukkitTask emitParticlesTask = Bukkit.getScheduler().runTaskTimer(LilAirdrops.getInstance(), () -> emitParticlesOnTimer(), 0L, 20L);
        Bukkit.getScheduler().runTaskLater(LilAirdrops.getInstance(), () -> emitParticlesTask.cancel(), 60*20L);
        Bukkit.getScheduler().runTaskLater(LilAirdrops.getInstance(), () -> explodeParticles(), 60*20L);
    }

    private void emitParticlesOnTimer(){
        World world = droppedItem.getWorld();
        droppedItem.setVelocity(new Vector(0, 2, 0));
        world.spawnParticle(trailParticle, droppedItem.getLocation(), 1);
    }

    private void explodeParticles(){
        World world = droppedItem.getWorld();
        world.spawnParticle(explosionParticle, droppedItem.getLocation(), 1);
        droppedItem.remove();
        callAirdrop();
    }

    public String getAirdropType(){
        return airdropType;
    }

    public void setAirdropType(String airdropType){
        this.airdropType = airdropType;
    }

    public ItemStack getItem(){
        return item;
    }

    public void setItem(ItemStack item){
        this.item = item;
    }

    public Particle getTrailParticle(){
        return trailParticle;
    }

    public void setTrailParticle(Particle trailParticle){
        this.trailParticle = trailParticle;
    }

    public String getTrailParticleData(){
        return trailParticleData;
    }

    public void setTrailParticleData(String trailParticleData){
        this.trailParticleData = trailParticleData;
    }

    public Particle getExplosionParticle(){
        return explosionParticle;
    }

    public void setExplosionParticle(Particle explosionParticle){
        this.explosionParticle = explosionParticle;
    }

    public String getExplosionParticleData(){
        return explosionParticleData;
    }

    public void setExplosionParticleData(String explosionParticleData){
        this.explosionParticleData = explosionParticleData;
    }
}
