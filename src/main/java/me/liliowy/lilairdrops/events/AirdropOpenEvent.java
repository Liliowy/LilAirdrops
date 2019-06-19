package me.liliowy.lilairdrops.events;

import me.liliowy.lilairdrops.objects.Airdrop;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AirdropOpenEvent extends Event implements Cancellable {

    private Player player;
    private Airdrop airdrop;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public AirdropOpenEvent(Airdrop airdrop, Player player) {
        this.airdrop = airdrop;
        this.player = player;
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

    public Airdrop getAirdrop() {
        return airdrop;
    }

    public Player getPlayer() {
        return player;
    }
}