package me.liliowy.lilairdrops.util;

import me.liliowy.lilairdrops.LilAirdrops;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material){
        if (material != null){
            itemStack = new ItemStack(material);
            itemMeta = itemStack.getItemMeta();
        }
    }

    public ItemBuilder(ItemStack itemStack){
        if (itemStack != null){
            this.itemStack = itemStack;
            itemMeta = itemStack.getItemMeta();
        }
    }

    public ItemBuilder setMaterial(Material material){
        if (material != null){
            itemStack.setType(material);
        }

        return this;
    }

    public ItemBuilder setItemStack(ItemStack itemStack){
        if (itemStack != null) this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta){
        if (itemMeta != null) this.itemMeta = itemMeta;
        return this;
    }

    public ItemBuilder setAmount(int amount){
        if (amount > 0) itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(int durability){
        if (itemMeta instanceof Damageable){
            ((Damageable) itemMeta).setDamage(durability);
        }
        return this;
    }

    public ItemBuilder setDisplayName(String displayName){
        itemMeta.setDisplayName(Formatting.format(displayName));
        return this;
    }

    public ItemBuilder addLoreLine(String lore){
        if (itemMeta != null && !itemMeta.hasLore()){
            itemMeta.setLore(new ArrayList<>());
        }

        List<String> newLore = itemMeta.getLore();
        newLore.add(Formatting.format(lore));
        itemMeta.setLore(newLore);

        return this;
    }

    public ItemBuilder removeLoreLine(int line){
        if (itemStack.hasItemMeta() && itemMeta != null){
            if (itemMeta.hasLore()){
                List<String> lore = itemMeta.getLore();
                if (lore.size() > line) {
                    lore.remove(line);
                    itemMeta.setLore(lore);
                }
            }
        }

        return this;
    }

    public ItemBuilder removeLoreLine(String lore){
        if (itemStack.hasItemMeta() && itemMeta != null){
            if (itemMeta.hasLore()){
                List<String> loreLines = itemMeta.getLore();
                if (loreLines.contains(Formatting.format(lore))){
                    loreLines.remove(Formatting.format(lore));
                    itemMeta.setLore(loreLines);
                }
            }
        }

        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<String>());
        List<String> newLore = new ArrayList<String>();

        if (lore != null && !lore.isEmpty()){
            for (String loreLine : lore){
                newLore.add(Formatting.format(loreLine));
            }
        }

        itemMeta.setLore(newLore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        if (enchantment != null){
            if (itemMeta instanceof EnchantmentStorageMeta){
                ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, level, true);
            } else {
                itemMeta.addEnchant(enchantment, level, true);
            }
        }

        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments){
        if (enchantments == null || enchantments.isEmpty()){
            if (itemMeta instanceof EnchantmentStorageMeta){
                ((EnchantmentStorageMeta) itemMeta).getStoredEnchants().clear();

                for (Enchantment enchantment : enchantments.keySet()){
                    addEnchantment(enchantment, enchantments.get(enchantment));
                }
            } else {
                itemMeta.getEnchants().clear();

                for (Enchantment enchantment : enchantments.keySet()){
                    addEnchantment(enchantment, enchantments.get(enchantment));
                }
            }
        }

        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment){
        if (enchantment != null){
            if (itemMeta instanceof EnchantmentStorageMeta){
                if (((EnchantmentStorageMeta) itemMeta).hasStoredEnchant(enchantment)){
                    ((EnchantmentStorageMeta) itemMeta).removeStoredEnchant(enchantment);
                } else {
                    if (itemMeta.hasEnchant(enchantment)){
                        itemMeta.removeEnchant(enchantment);
                    }
                }
            }
        }

        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag){
        if (itemFlag != null){
            itemMeta.addItemFlags(itemFlag);
        }

        return this;
    }

    public ItemBuilder setItemFlags(List<ItemFlag> itemFlags){
        if (itemFlags == null || itemFlags.isEmpty()){
            itemMeta.getItemFlags().clear();
        } else {
            for (ItemFlag itemFlag : itemFlags){
                itemMeta.addItemFlags(itemFlag);
            }
        }

        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag itemFlag){
        if (itemFlag != null){
            if (itemMeta.hasItemFlag(itemFlag)){
                itemMeta.removeItemFlags(itemFlag);
            }
        }

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setGlowing(boolean glow){
        EnchantmentGlow glowEnchantment = LilAirdrops.getInstance().getGlowEnchantment();
        if (glow){
            if (!itemMeta.hasEnchant(glowEnchantment)){
                addEnchantment(glowEnchantment, 1);
            }
        } else {
            if (itemMeta.hasEnchant(glowEnchantment)){
                removeEnchantment(glowEnchantment);
            }
        }

        return this;
    }

    public ItemBuilder setBookTitle(String title){
        if (itemMeta instanceof BookMeta){
            ((BookMeta) itemMeta).setTitle(Formatting.format(title));
        }

        return this;
    }

    public ItemBuilder setBookAuthor(String author){
        if (itemMeta instanceof BookMeta){
            ((BookMeta) itemMeta).setAuthor(Formatting.format(author));
        }

        return this;
    }

    public ItemBuilder addBookPage(String page){
        if (itemMeta instanceof BookMeta){
            ((BookMeta) itemMeta).addPage(Formatting.format(page));
        }

        return this;
    }

    public ItemBuilder setBookPages(List<String> pages){
        if (itemMeta instanceof BookMeta){
            ((BookMeta) itemMeta).getPages().clear();
            for (String page : pages){
                addBookPage(page);
            }
        }

        return this;
    }

    public ItemBuilder removeBookPage(int index){
        if (itemMeta instanceof BookMeta){
            ((BookMeta) itemMeta).getPages().remove(index);
        }
        return this;
    }

    public ItemBuilder addFireworkEffect(FireworkEffect effect){
        if (effect != null){
            if (itemMeta instanceof FireworkEffectMeta){
                ((FireworkEffectMeta) itemMeta).setEffect(effect);
            } else if (itemMeta instanceof FireworkMeta){
                ((FireworkMeta) itemMeta).addEffect(effect);
            }
        }

        return this;
    }

    public ItemBuilder setFireworkEffects(List<FireworkEffect> effects){
        if (itemMeta instanceof FireworkMeta){
            ((FireworkMeta) itemMeta).clearEffects();
            ((FireworkMeta) itemMeta).addEffects(effects);
        } else if (itemMeta instanceof FireworkEffectMeta) {
            if (effects == null || effects.isEmpty()){
                ((FireworkEffectMeta) itemMeta).setEffect(null);
            } else {
                ((FireworkEffectMeta) itemMeta).setEffect(effects.get(0));
            }
        }

        return this;
    }

    public ItemBuilder setFireworkPower(int power){
        if (itemMeta instanceof FireworkMeta){
            ((FireworkMeta) itemMeta).setPower(power);
        }

        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color){
        if (color != null){
            if (itemMeta instanceof LeatherArmorMeta){
                ((LeatherArmorMeta) itemMeta).setColor(color);
            }
        }

        return this;
    }

    public ItemBuilder addPotionEffect(PotionEffect effect){
        if (effect != null){
            if (itemMeta instanceof PotionMeta){
                ((PotionMeta) itemMeta).addCustomEffect(effect, false);
            }
        }

        return this;
    }

    public ItemBuilder setPotionEffects(List<PotionEffect> effects){
        if (itemMeta instanceof PotionMeta){
            ((PotionMeta) itemMeta).clearCustomEffects();
            for (PotionEffect effect : effects){
                addPotionEffect(effect);
            }
        }

        return this;
    }

    public ItemBuilder removePotionEffect(PotionEffectType type){
        if (type != null){
            if (itemMeta instanceof PotionMeta){
                if (((PotionMeta) itemMeta).hasCustomEffect(type)){
                    ((PotionMeta) itemMeta).removeCustomEffect(type);
                }
            }
        }

        return this;
    }

    public ItemBuilder setPotionColor(Color color){
        if (itemMeta instanceof PotionMeta){
            ((PotionMeta) itemMeta).setColor(color);
        }
        return this;
    }

    public ItemBuilder setSkullOwner(OfflinePlayer player){
        if (itemMeta instanceof SkullMeta){
            ((SkullMeta) itemMeta).setOwningPlayer(player);
        }

        return this;
    }

    public ItemBuilder addBannerPattern(Pattern pattern){
        if (pattern != null){
            if (itemMeta instanceof BannerMeta){
                ((BannerMeta) itemMeta).addPattern(pattern);
            }
        }

        return this;
    }

    public ItemBuilder setBannerPatterns(List<Pattern> patterns){
        if (itemMeta instanceof BannerMeta){
            ((BannerMeta) itemMeta).getPatterns().clear();
            for (Pattern pattern : patterns){
                addBannerPattern(pattern);
            }
        }

        return this;
    }

    public ItemBuilder setTropicalFishData(DyeColor patternColor, TropicalFish.Pattern pattern, DyeColor bodyColor){
        if (itemMeta instanceof TropicalFishBucketMeta){
            if (patternColor != null){
                ((TropicalFishBucketMeta) itemMeta).setPatternColor(patternColor);
            }

            if (pattern != null){
                ((TropicalFishBucketMeta) itemMeta).setPattern(pattern);
            }

            if (bodyColor != null){
                ((TropicalFishBucketMeta) itemMeta).setBodyColor(bodyColor);
            }
        }

        return this;
    }

    public ItemBuilder setSpawnerType(EntityType entity){
        if (itemStack.getType() == Material.SPAWNER){
            BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
            CreatureSpawner creatureSpawner = (CreatureSpawner) blockStateMeta.getBlockState();
            creatureSpawner.setSpawnedType(entity);
            blockStateMeta.setBlockState(creatureSpawner);
            itemMeta = blockStateMeta;
        }

        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
