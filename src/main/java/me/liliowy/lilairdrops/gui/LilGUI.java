package me.liliowy.lilairdrops.gui;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.util.ItemBuilder;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LilGUI {

    private LilAirdrops instance;
    private Inventory handle;
    private List<Map<Integer, ItemStack>> pageContents;
    private ItemStack returnItem;
    private ItemStack nextItem;
    private ItemStack previousItem;
    private Map<UUID, Integer> playerPages;

    public LilGUI(String title){
        returnItem = new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("&cBack").addLoreLine("&7Return to the previous menu.").build();
        nextItem = new ItemBuilder(Material.ARROW).setDisplayName("&cNext").addLoreLine("&7View the next page.").build();
        previousItem = new ItemBuilder(Material.ARROW).setDisplayName("&cPrevious").addLoreLine("&7View the previous page.").build();
        pageContents = new ArrayList<>();
        playerPages = new HashMap<UUID, Integer>();
    }

    public void buildItems(){

    }

    public void setItems(int page){
        setBottomRowItems();

        for (int index : pageContents.get(page).keySet()){
            handle.setItem(index, pageContents.get(page).get(index));
        }
    }

    public void setBottomRowItems(){

    }

    public void update(int page){
        if (handle != null) handle.clear();
        buildItems();
        setItems(page);
    }

    public void update(int page, Player player){
        if (handle != null) handle.clear();
        buildItems();
        setItems(page);
        getPlayerPages().put(player.getUniqueId(), page);
    }

    public void open(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        player.openInventory(handle);
    }

    public int calculateSize(int size) {
        if (size > 0 && size < 10) {
            return 9;
        } else if (size > 9 && size < 19) {
            return 18;
        } else if (size > 18 && size < 28) {
            return 27;
        } else if (size > 27 && size < 37) {
            return 36;
        } else if (size > 36 && size < 46) {
            return 45;
        } else if (size > 45 && size < 55) {
            return 54;
        } else {
            return 54;
        }
    }

    public void onClick(InventoryClickEvent event){

    }

    private void updateClientTitle(String title, Player player){
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId, Containers.GENERIC_9X6, new ChatMessage(title));
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.updateInventory(entityPlayer.activeContainer);
    }

    public boolean hasNextPage(int currentPage){
        return getPageContents().size() > currentPage+1;
    }

    public boolean hasPreviousPage(int currentPage){
        return currentPage != 0;
    }

    public Inventory getHandle(){
        return handle;
    }

    public void setHandle(Inventory handle){
        this.handle = handle;
    }

    public List<Map<Integer, ItemStack>> getPageContents(){
        return pageContents;
    }

    public ItemStack getReturnItem(){
        return returnItem;
    }

    public ItemStack getNextItem() {
        return nextItem;
    }

    public ItemStack getPreviousItem(){
        return previousItem;
    }

    public boolean isPaged(){
        return getPageContents().size() > 1;
    }

    public Map<UUID, Integer> getPlayerPages(){
        return playerPages;
    }

    public LilAirdrops getInstance(){
        return instance;
    }
}
