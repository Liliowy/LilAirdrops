package me.liliowy.lilairdrops.listeners;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class EventInventoryClose implements Listener {

    private LilAirdrops instance;

    public EventInventoryClose(){
        instance = LilAirdrops.getInstance();
        instance.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        String title = ChatColor.stripColor(event.getView().getTitle());

        if (inventory.getHolder() instanceof LilInventoryHolder){
            if (title.equalsIgnoreCase("Random Airdrop") || title.startsWith("Airdrop Chances")){
                instance.getRandomAirdropHandler().save();
            }
        }
    }
}
