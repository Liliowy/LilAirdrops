package me.liliowy.lilairdrops.gui.inventories;

import me.liliowy.lilairdrops.gui.LilGUI;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import me.liliowy.lilairdrops.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainInventory extends LilGUI {

    public MainInventory(){
        super("LilAirdrops");
        setHandle(Bukkit.createInventory(new LilInventoryHolder(), 9, "LilAirdrops"));
        buildItems();
        setItems(0);
    }

    @Override
    public void buildItems(){
        ItemStack random = new ItemBuilder(Material.ENDER_CHEST)
                .setDisplayName("&aRandom Airdrop")
                .setLore(Arrays.asList("&7Click to view and edit the", "     &7settings for the random airdrops.")).build();

        ItemStack edit = new ItemBuilder(Material.CHEST)
                .setDisplayName("&bAirdrops")
                .setLore(Arrays.asList("&7Click to view and edit the", "     &7settings for specific airdrops.")).build();

        ItemStack all = new ItemBuilder(Material.BOOK)
                .setDisplayName("&cAll Airdrops")
                .setLore(Arrays.asList("&7Click to view and edit the", "     &7settings for airdrops in the world.")).build();

        ItemStack flares = new ItemBuilder(Material.FIREWORK_ROCKET)
                .setDisplayName("&dFlares")
                .setLore(Arrays.asList("&7Click to view the gui", "     &7to give a player a flare.")).build();

        Map<Integer, ItemStack> page0Contents = new HashMap<>();
        page0Contents.put(1, random);
        page0Contents.put(3, edit);
        page0Contents.put(5, all);
        page0Contents.put(7, flares);
        getPageContents().add(page0Contents);
    }


    @Override
    public void onClick(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        switch (item.getType()){
            case ENDER_CHEST:
                getInstance().getGuiHandler().getRandomAirdropInventory().open(player);
                break;
            case CHEST:
                // Open all airdrops GUI.
                break;
            case BOOK:
                // Open current airdrops GUI.
                break;
            case FIREWORK_ROCKET:
                // Open flares gui.
                break;
        }
    }
}
