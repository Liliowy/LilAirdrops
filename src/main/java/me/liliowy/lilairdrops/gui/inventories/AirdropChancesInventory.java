package me.liliowy.lilairdrops.gui.inventories;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.gui.LilGUI;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import me.liliowy.lilairdrops.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirdropChancesInventory extends LilGUI {

    private LilAirdrops instance;
    private int size;

    public AirdropChancesInventory(){
        super("Airdrop Chances");
        instance = LilAirdrops.getInstance();
        size = calculateSize(instance.getRandomAirdropHandler().getChances().size()+9);
        String title = "Airdrop Chances";

        if (size > 44) title = "Airdrop Chances - Page 1";

        setHandle(Bukkit.createInventory(new LilInventoryHolder(), size, title));
    }

    @Override
    public void buildItems(){

        getPageContents().clear();

        if (instance.getRandomAirdropHandler().getChances() != null){
            int page = 0;
            int index = 0;
            int count = 0;

            for (String airdrop : instance.getRandomAirdropHandler().getChances().keySet()){
                ItemStack item = new ItemBuilder(instance.getAirdropHandler().getAirdropTypes().get(airdrop).getMaterial())
                        .setDisplayName("&e" + airdrop)
                        .setLore(Arrays.asList("&7Chance: &f" + instance.getRandomAirdropHandler().getChances().get(airdrop), "&7The chance of this airdrop being summoned.",
                                "", "&a&lLMB &7to Increase by 1", "&c&lRMB &7to Decrease by 1", "&a&lShift LMB &7to Increase by 10", "&c&lShift RMB &7to Decrease by 10"))
                        .build();

                page = (int) Math.ceil(index/45);
                // pages will start at one, so gotta subtract for page 0.
                if (page > 1) page--;
                if (getPageContents().size() == 0 || getPageContents().size() < page+1) getPageContents().add(new HashMap<>());
                getPageContents().get(page).put(count, item);

                index++;

                if (count > 43){
                    count = 0;
                } else {
                    count ++;
                }
            }
        }
    }

    @Override
    public void setBottomRowItems(){
        if (instance.getRandomAirdropHandler().getChances().size() > 44) {
            int index = 0;
            for (Map<Integer, ItemStack> page : getPageContents()) {
                if (hasPreviousPage(index)) page.put(45, getPreviousItem());
                page.put(49, getReturnItem());
                if (hasNextPage(index)) page.put(53, getNextItem());
                index++;
            }
        } else {
            getPageContents().get(0).put(size-5, getReturnItem());
        }
    }
}
