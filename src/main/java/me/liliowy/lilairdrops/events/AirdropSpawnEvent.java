package me.liliowy.lilairdrops.events;

import me.liliowy.lilairdrops.objects.Airdrop;
import me.liliowy.lilairdrops.util.SpawnType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AirdropSpawnEvent extends Event implements Cancellable {

    private SpawnType spawnType;
    private Airdrop airdrop;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public AirdropSpawnEvent(Airdrop airdrop, SpawnType spawnType) {
        this.spawnType = spawnType;
        this.airdrop = airdrop;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public SpawnType getSpawnType(){
        return spawnType;
    }

    public Airdrop getAirdrop() {
        return airdrop;
    }
}