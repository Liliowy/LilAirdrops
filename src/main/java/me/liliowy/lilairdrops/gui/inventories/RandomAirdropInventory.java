package me.liliowy.lilairdrops.gui.inventories;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.gui.LilGUI;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import me.liliowy.lilairdrops.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RandomAirdropInventory extends LilGUI {

    private LilAirdrops instance;

    public RandomAirdropInventory(){
        super("Random Airdrop");
        instance = LilAirdrops.getInstance();
        setHandle(Bukkit.createInventory(new LilInventoryHolder(), 18, "Random Airdrop"));
    }

    @Override
    public void buildItems(){
        ItemStack enabled;

        if (instance.getRandomAirdropHandler().isEnabled()){
            enabled = new ItemBuilder(Material.LIME_DYE)
                    .setDisplayName("&eUse Random Airdrops")
                    .setLore(Arrays.asList("&7Enabled: &aTrue", "&7Click to Toggle")).build();
        } else {
            enabled = new ItemBuilder(Material.RED_DYE)
                    .setDisplayName("&eUse Random Airdrops")
                    .setLore(Arrays.asList("&7Enabled: &cFalse", "&7Click to Toggle")).build();
        }

        ItemStack chances = new ItemBuilder(Material.CHEST)
                .setDisplayName("&eAirdrop Chances")
                .setLore(Arrays.asList("&7Click to view and edit the airdrop chances.")).build();

        ItemStack flare = new ItemBuilder(Material.FIREWORK_ROCKET)
                .setDisplayName("&eFlare")
                .setLore(Arrays.asList("&7Click to view and edit", "&7the flare for the random airdrop.")).build();

        ItemStack intervalMax = new ItemBuilder(Material.CLOCK)
                .setDisplayName("&eInterval Max")
                .setLore(Arrays.asList("&7Interval Max: " + instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMax(), "&7The maximum time taken for an airdrop to spawn.",
                        "", "&a&lLMB &7to Increase by 1", "&c&lRMB &7to Decrease by 1", "&a&lShift LMB &7to Increase by 10", "&c&lShift RMB &7to Decrease by 10")).build();

        ItemStack intervalMin = new ItemBuilder(Material.COMPASS)
                .setDisplayName("&eInterval Min")
                .setLore(Arrays.asList("&7Interval Min: " + instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMin(), "&7The maximum time taken for an airdrop to spawn.",
                        "", "&a&lLMB &7to Increase by 1", "&c&lRMB &7to Decrease by 1", "&a&lShift LMB &7to Increase by 10", "&c&lShift RMB &7to Decrease by 10")).build();

        Map<Integer, ItemStack> page0Contents = new HashMap<>();
        page0Contents.put(0, enabled);
        page0Contents.put(2, chances);
        page0Contents.put(4, flare);
        page0Contents.put(6, intervalMax);
        page0Contents.put(7, intervalMin);
        page0Contents.put(13, getReturnItem());
        getPageContents().clear();
        getPageContents().add(page0Contents);
    }

    @Override
    public void open(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        buildItems();
        setItems(0);
        player.openInventory(getHandle());
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
