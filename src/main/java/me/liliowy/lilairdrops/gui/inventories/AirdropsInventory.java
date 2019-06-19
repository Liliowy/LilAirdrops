package me.liliowy.lilairdrops.gui.inventories;

import me.liliowy.lilairdrops.gui.LilGUI;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import org.bukkit.Bukkit;

public class AirdropsInventory extends LilGUI {

    public AirdropsInventory(){
        super("Airdrops");
        setHandle(Bukkit.createInventory(new LilInventoryHolder(), 9, "La"));
    }
}
