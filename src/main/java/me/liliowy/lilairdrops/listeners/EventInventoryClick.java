package me.liliowy.lilairdrops.listeners;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.gui.LilInventoryHolder;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;

public class EventInventoryClick implements Listener {

    private LilAirdrops instance;

    public EventInventoryClick(){
        instance = LilAirdrops.getInstance();
        instance.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getInventory() != null) {
            Inventory inventory = event.getInventory();
            String title = ChatColor.stripColor(event.getView().getTitle());

            if (inventory.getHolder() instanceof LilInventoryHolder && event.getCurrentItem() != null){
                event.setCancelled(true);

                if (title.equalsIgnoreCase("LilAirdrops")){
                    instance.getGuiHandler().getMainInventory().onClick(event);
                } else if (title.equalsIgnoreCase("Random Airdrop")){
                    instance.getGuiHandler().getRandomAirdropInventory().onClick(event);
                } else if (title.equalsIgnoreCase("Airdrop Chances")){
                    instance.getGuiHandler().getAirdropChancesInventory().onClick(event);
                }
            }
        }

        /*

        if (event.getInventory() != null){
            Inventory inventory = event.getInventory();
            String title = ChatColor.stripColor(event.getView().getTitle());

            if (inventory.getHolder() instanceof LilInventoryHolder && event.getCurrentItem() != null){
                ItemStack item = event.getCurrentItem();
                event.setCancelled(true);

                if (title.equalsIgnoreCase("LilAirdrops")){
                    switch (item.getType()){
                        case ENDER_CHEST:
                            instance.getGuiHandler().getRandomAirdropInventory().open(player);
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

                else if (title.equalsIgnoreCase("Random Airdrop")){
                    if (item.getType() == Material.LIME_DYE) {
                        inventory.clear();
                        instance.getRandomAirdropHandler().setEnabled(false);
                        instance.getGuiHandler().getRandomAirdropInventory().update(0);
                    } else if (item.getType() == Material.RED_DYE) {
                        instance.getRandomAirdropHandler().setEnabled(true);
                        instance.getGuiHandler().getRandomAirdropInventory().update(0);
                    } else if (item.getType() == Material.CHEST){
                        instance.getGuiHandler().getAirdropChancesInventory().update(0);
                        instance.getGuiHandler().getAirdropChancesInventory().open(player);
                    } else if (item.getType() == Material.FIREWORK_ROCKET){

                    } else if (item.getType() == Material.CLOCK) {
                        long intervalMax = instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMax();

                        if (event.getClick() == ClickType.RIGHT){
                            if (instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMax() != 0){
                                instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMax(intervalMax-1);
                            }
                        } else if (event.getClick() == ClickType.LEFT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMax(intervalMax+1);
                        } else if (event.getClick() == ClickType.SHIFT_RIGHT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMax(intervalMax-10);
                            if (instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMax() < 0){
                                instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMax(0);
                            }
                        } else if (event.getClick() == ClickType.SHIFT_LEFT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMax(intervalMax+10);
                        }

                        instance.getGuiHandler().getRandomAirdropInventory().update(0);
                    } else if (item.getType() == Material.COMPASS) {
                        long intervalMin = instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMin();

                        if (event.getClick() == ClickType.RIGHT){
                            if (instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMin() != 0){
                                instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMin(intervalMin-1);
                            }
                        } else if (event.getClick() == ClickType.LEFT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMin(intervalMin+1);
                        } else if (event.getClick() == ClickType.SHIFT_RIGHT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMin(intervalMin-10);
                            if (instance.getRandomAirdropHandler().getRandomAirdrop().getIntervalMin() < 0){
                                instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMin(0);
                            }
                        } else if (event.getClick() == ClickType.SHIFT_LEFT){
                            instance.getRandomAirdropHandler().getRandomAirdrop().setIntervalMin(intervalMin+10);
                        }

                        instance.getGuiHandler().getRandomAirdropInventory().update(0);
                    } else if (item.getType() == Material.DARK_OAK_DOOR){
                        instance.getGuiHandler().getMainInventory().open(player);
                    }
                }

                else if (title.startsWith("Airdrop Chances")){
                    int currentPage = 0;
                    if (!title.equalsIgnoreCase("Airdrop Chances")) {
                        if (instance.getGuiHandler().getAirdropChancesInventory().getPageContents().contains(player.getUniqueId())){
                            currentPage = instance.getGuiHandler().getAirdropChancesInventory().getPlayerPages().get(player.getUniqueId());
                        }
                    }

                    if (item.getType() == Material.CHEST) {
                        String airdrop = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                        int current = instance.getRandomAirdropHandler().getChances().get(airdrop);

                        if (event.getClick() == ClickType.RIGHT){
                            if (current != 0){
                                instance.getRandomAirdropHandler().getChances().put(airdrop, current-1);
                            }
                        } else if (event.getClick() == ClickType.LEFT){
                            if (current < 100){
                                instance.getRandomAirdropHandler().getChances().put(airdrop, current+1);
                            }
                        } else if (event.getClick() == ClickType.SHIFT_RIGHT){
                            instance.getRandomAirdropHandler().getChances().put(airdrop, current-10);
                            if (current-10 < 0){
                                instance.getRandomAirdropHandler().getChances().put(airdrop, 0);
                            }
                        } else if (event.getClick() == ClickType.SHIFT_LEFT){
                            instance.getRandomAirdropHandler().getChances().put(airdrop, current+10);
                            if (current+10 > 100){
                                instance.getRandomAirdropHandler().getChances().put(airdrop, 100);
                            }
                        }

                        Bukkit.broadcastMessage("the fucking current page: " + currentPage);
                        instance.getGuiHandler().getAirdropChancesInventory().update(currentPage);
                    } else if (item.getType() == Material.DARK_OAK_DOOR){
                        instance.getGuiHandler().getRandomAirdropInventory().open(player);
                    } else if (item.getType() == Material.ARROW && item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
                        int newPage = 0;
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Next")){
                            newPage = currentPage + 1;
                        } else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Previous")){
                            newPage = currentPage;
                        }

                        Bukkit.broadcastMessage("New Page: " + newPage);
                        instance.getGuiHandler().getAirdropChancesInventory().update(newPage, player);
                        int newPageText = newPage + 1;
                        updateTitle(title.replace(currentPage + "", newPageText + ""), player);
                    }
                }

                else if (title.equalsIgnoreCase("Airdrops")){

                }

                else if (title.equalsIgnoreCase("Current Airdrops")){

                }

                else if (title.equalsIgnoreCase("Flares")){

                }
            }
        }*/
    }

    private void updateTitle(String title, Player player){
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId, Containers.GENERIC_9X6, new ChatMessage(title));
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.updateInventory(entityPlayer.activeContainer);
    }
}
