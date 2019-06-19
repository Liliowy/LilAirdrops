package me.liliowy.lilairdrops.events;

import me.liliowy.lilairdrops.objects.Flare;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AirdropFlareFireEvent extends Event implements Cancellable {

    private Flare flare;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public AirdropFlareFireEvent(Flare flare) {
        this.flare = flare;
        this.cancelled = false;
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

    public Flare getFlare() {
        return flare;
    }
}