package me.liliowy.lilairdrops.events;

import me.liliowy.lilairdrops.objects.Airdrop;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AirdropDestroyEvent extends Event implements Cancellable {

    private Airdrop airdrop;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public AirdropDestroyEvent(Airdrop airdrop){
        this.airdrop = airdrop;
    }

    @Override
    public boolean isCancelled(){
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers(){
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    public Airdrop getAirdrop(){
        return airdrop;
    }
}
