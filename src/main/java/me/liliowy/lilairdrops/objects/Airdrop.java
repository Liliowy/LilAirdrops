package me.liliowy.lilairdrops.objects;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.events.AirdropDestroyEvent;
import me.liliowy.lilairdrops.events.AirdropLandEvent;
import me.liliowy.lilairdrops.events.AirdropOpenEvent;
import me.liliowy.lilairdrops.events.AirdropSpawnEvent;
import me.liliowy.lilairdrops.util.AnnouncementLocation;
import me.liliowy.lilairdrops.util.SpawnType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Airdrop {

    private String type;
    private String permission;
    private long intervalMax;
    private long intervalMin;
    private long removalTime;
    private boolean destroyOnClose;
    private boolean dropOnClose;
    private boolean inventoryOnClose;
    private boolean fullInventoryDropOnClose;
    private boolean dropInWater;
    private boolean dropInLava;
    private boolean othersOpen;
    private String announcement;
    private AnnouncementLocation announcementLocation;
    private boolean announceToOtherWorlds;
    private Material material;
    private Particle fallingParticle;
    private String fallingParticleData;
    private int fallingParticleCount;
    private Particle onLandParticle;
    private String onLandParticleData;
    private int onLandParticleCount;
    private Particle landedParticle;
    private String landedParticleData;
    private int landedParticleCount;
    private String hologramText;
    private boolean useGUI;
    private String guiTitle;
    private Map<ItemStack, Integer> possibleItems;
    private Map<String, Integer> possibleCommands;
    private Flare flare;
    private boolean useFlareOffset;
    private World world;
    private int maximumBoundX;
    private int minimumBoundX;
    private int maximumBoundZ;
    private int minimumBoundZ;
    private int maximumBoundY;
    private int offset;
    private boolean bypassWGRegions;
    private List<String> disabledWGRegions;
    private List<String> enabledWGRegions;
    private boolean dropOnIslands;
    private boolean lockAirdrop;
    private int slowMultiplier;

    private LilAirdrops instance;
    private ArmorStand fallingAirdrop;
    private Block landedAirdrop;
    private Location startLocation;
    private Location realStartLocation;
    private Location endLocation;
    private boolean open;
    private boolean inAir;
    private Inventory inventory;

    public Airdrop(String type) {
        instance = LilAirdrops.getInstance();
        possibleItems = new HashMap<>();
        possibleCommands = new HashMap<>();
    }

    public void summon(SpawnType spawnType){
        AirdropSpawnEvent spawnEvent = new AirdropSpawnEvent(this, spawnType);
        Bukkit.getServer().getPluginManager().callEvent(spawnEvent);

        if (!spawnEvent.isCancelled()){

        }
    }

    public void emitFallingParticles(){
        if (inAir && landedAirdrop == null && fallingAirdrop != null){

        }
    }

    public void emitOnLandParticles(){
        if (!inAir && landedAirdrop != null){

        }
    }

    public void emitLandedParticles(){
        if (!inAir && landedAirdrop != null){

        }
    }

    public void onLand(){
        AirdropLandEvent landEvent = new AirdropLandEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(landEvent);

        if (!landEvent.isCancelled()){

        }
    }

    public void open(Player player){
        AirdropOpenEvent openEvent = new AirdropOpenEvent(this, player);
        Bukkit.getServer().getPluginManager().callEvent(openEvent);

        if (!openEvent.isCancelled()){
            if (!open || (open && othersOpen)){
                generateContent();
                if (useGUI){

                } else {

                }
            }
        }
    }

    public void close(){
        if (destroyOnClose){
            AirdropDestroyEvent destroyEvent = new AirdropDestroyEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(destroyEvent);

            if (!destroyEvent.isCancelled()){
                if (fallingAirdrop != null) fallingAirdrop.remove();
                if (landedAirdrop != null) {
                    landedAirdrop.setType(Material.AIR);
                }
            }
        }

        if (inventoryOnClose){

        }
    }

    public void generateContent(){

    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getPermission(){
        return permission;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public long getIntervalMax(){
        return intervalMax;
    }

    public void setIntervalMax(long intervalMax){
        this.intervalMax = intervalMax;
    }

    public long getIntervalMin(){
        return intervalMin;
    }

    public void setIntervalMin(long intervalMin){
        this.intervalMin = intervalMin;
    }

    public long getRemovalTime(){
        return removalTime;
    }

    public void setRemovalTime(long removalTime){
        this.removalTime = removalTime;
    }

    public boolean shouldDestroyOnClose(){
        return destroyOnClose;
    }

    public void setShouldDestroyOnClose(boolean destroyOnClose){
        this.destroyOnClose = destroyOnClose;
    }

    public boolean shouldDropItemsOnClose(){
        return dropOnClose;
    }

    public void setShouldDropItemsOnClose(boolean dropOnClose){
        this.dropOnClose = dropOnClose;
    }

    public boolean shouldItemsGoToInventoryOnClose(){
        return inventoryOnClose;
    }

    public void setShouldItemsGoToInventoryOnClose(boolean inventoryOnClose){
        this.inventoryOnClose = inventoryOnClose;
    }

    public boolean shouldItemsDropWithFullInventoryOnClose(){
        return fullInventoryDropOnClose;
    }

    public void setShouldItemsDropWithFullInventoryOnClose(boolean fullInventoryDropOnClose){
        this.fullInventoryDropOnClose = fullInventoryDropOnClose;
    }

    public boolean canDropInWater(){
        return dropInWater;
    }

    public void setCanDropInWater(boolean dropInWater){
        this.dropInWater = dropInWater;
    }

    public boolean canDropInLava(){
        return dropInLava;
    }

    public void setCanDropInLava(boolean dropInLava){
        this.dropInLava = dropInLava;
    }

    public boolean canOthersOpen(){
        return othersOpen;
    }

    public void setCanOthersOpen(boolean othersOpen){
        this.othersOpen = othersOpen;
    }

    public String getAnnouncementMessage(){
        return announcement;
    }

    public void setAnnouncementMessage(String announcement){
        this.announcement = announcement;
    }

    public AnnouncementLocation getAnnouncementLocation(){
        return announcementLocation;
    }

    public void setAnnouncementLocation(AnnouncementLocation announcementLocation){
        this.announcementLocation = announcementLocation;
    }

    public boolean shouldAnnounceToOtherWorlds(){
        return announceToOtherWorlds;
    }

    public void setShouldAnnounceToOtherWorlds(boolean announceToOtherWorlds){
        this.announceToOtherWorlds = announceToOtherWorlds;
    }

    public Material getMaterial(){
        return material;
    }

    public void setMaterial(Material material){
        this.material = material;
    }

    public Particle getFallingParticle(){
        return fallingParticle;
    }

    public void setFallingParticle(Particle fallingParticle){
        this.fallingParticle = fallingParticle;
    }

    public String getFallingParticleData(){
        return fallingParticleData;
    }

    public void setFallingParticleData(String fallingParticleData){
        this.fallingParticleData = fallingParticleData;
    }

    public int getFallingParticleCount(){
        return fallingParticleCount;
    }

    public void setFallingParticleCount(int fallingParticleCount){
        this.fallingParticleCount = fallingParticleCount;
    }

    public Particle getOnLandParticle(){
        return onLandParticle;
    }

    public void setOnLandParticle(Particle onLandParticle){
        this.onLandParticle = onLandParticle;
    }

    public String getOnLandParticleData(){
        return onLandParticleData;
    }

    public void setOnLandParticleData(String onLandParticleData){
        this.onLandParticleData = onLandParticleData;
    }

    public int getOnLandParticleCount(){
        return onLandParticleCount;
    }

    public void setOnLandParticleCount(int onLandParticleCount){
        this.onLandParticleCount = onLandParticleCount;
    }

    public Particle getLandedParticle(){
        return landedParticle;
    }

    public void setLandedParticle(Particle landedParticle){
        this.landedParticle = landedParticle;
    }

    public String getLandedParticleData(){
        return landedParticleData;
    }

    public void setLandedParticleData(String landedParticleData){
        this.landedParticleData = landedParticleData;
    }

    public int getLandedParticleCount(){
        return landedParticleCount;
    }

    public void setLandedParticleCount(int landedParticleCount){
        this.landedParticleCount = landedParticleCount;
    }

    public String getHologramText(){
        return hologramText;
    }

    public void setHologramText(String hologramText){
        this.hologramText = hologramText;
    }

    public boolean shouldUseGUI(){
        return useGUI;
    }

    public void setShouldUseGUI(boolean suseGUI){
        this.useGUI = useGUI;
    }

    public String getGUITitle(){
        return guiTitle;
    }

    public void setGUITitle(String guiTitle){
        this.guiTitle = guiTitle;
    }

    public Map<ItemStack, Integer> getPossibleItems(){
        return possibleItems;
    }

    public Map<String, Integer> getPossibleCommands(){
        return possibleCommands;
    }

    public Flare getFlare(){
        return flare;
    }

    public void setFlare(Flare flare){
        this.flare = flare;
    }

    public boolean shouldUseOffsetWithFlare(){
        return useFlareOffset;
    }

    public void setShouldUseOffsetWithFlare(boolean useFlareOffset){
        this.useFlareOffset = useFlareOffset;
    }

    public World getWorld(){
        return world;
    }

    public void setWorld(World world){
        this.world = world;
    }

    public int getMaximumBoundX(){
        return maximumBoundX;
    }

    public void setMaximumBoundX(int maximumBoundX){
        this.maximumBoundX = maximumBoundX;
    }

    public int getMinimumBoundX(){
        return minimumBoundX;
    }

    public void setMinimumBoundX(int minimumBoundX){
        this.minimumBoundX = minimumBoundX;
    }

    public int getMaximumBoundZ(){
        return maximumBoundZ;
    }

    public void setMaximumBoundZ(int maximumBoundZ){
        this.maximumBoundZ = maximumBoundZ;
    }

    public int getMinimumBoundZ(){
        return minimumBoundZ;
    }

    public void setMinimumBoundZ(int minimumBoundZ){
        this.minimumBoundZ = minimumBoundZ;
    }

    public int getMaximumBoundY(){
        return maximumBoundY;
    }

    public void setMaximumBoundY(int maximumBoundY){
        this.maximumBoundY = maximumBoundY;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public boolean shouldBypassWorldGuardRegions(){
        return bypassWGRegions;
    }

    public void setShouldBypassWorldGuardRegions(boolean bypassWGRegions){
        this.bypassWGRegions = bypassWGRegions;
    }

    public List<String> getDisabledWorldGuardRegions(){
        return disabledWGRegions;
    }

    public void setDisabledWorldGuardRegions(List<String> disabledWGRegions){
        this.disabledWGRegions = disabledWGRegions;
    }

    public List<String> getEnabledWorldGuardRegions(){
        return enabledWGRegions;
    }

    public void setEnabledWorldGuardRegions(List<String> enabledWGRegions){
        this.enabledWGRegions = enabledWGRegions;
    }

    public boolean shouldDropOnIslands(){
        return dropOnIslands;
    }

    public void setShouldDropOnIslands(boolean dropOnIslands){
        this.dropOnIslands = dropOnIslands;
    }

    public boolean canLockAirdrop(){
        return lockAirdrop;
    }

    public void setCanLockAirdrop(boolean lockAirdrop){
        this.lockAirdrop = lockAirdrop;
    }

    public int getSlowMultiplier(){
        return slowMultiplier;
    }

    public void setSlowMultiplier(int slowMultiplier){
        this.slowMultiplier = slowMultiplier;
    }

    public ArmorStand getFallingAirdrop(){
        return fallingAirdrop;
    }

    public Block getLandedAirdrop(){
        return landedAirdrop;
    }

    public Location getStartLocation(){
        return startLocation;
    }

    public void setStartLocation(Location startLocation){
        this.startLocation = startLocation;
    }

    public Location getRealStartLocation(){
        return realStartLocation;
    }

    public void setRealStartLocation(Location realStartLocation){
        this.realStartLocation = realStartLocation;
    }

    public Location getEndLocation(){
        return endLocation;
    }

    public void setEndLocation(Location endLocation){
        this.endLocation = endLocation;
    }

    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean open){
        this.open = open;
    }

    public boolean isInAir(){
        return inAir;
    }

    public void setIsInAir(boolean inAir){
        this.inAir = inAir;
    }

    public Inventory getInventory(){
        return inventory;
    }
}
