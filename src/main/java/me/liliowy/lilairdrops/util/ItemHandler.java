package me.liliowy.lilairdrops.util;

import me.liliowy.lilairdrops.LilAirdrops;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler {

    public static ItemStack deserializeItemStack(String file, ConfigurationSection fromSection){
       ItemBuilder itemBuilder = new ItemBuilder(Material.STONE);
        if (fromSection == null) return null;
        if (fromSection.contains("item") && Material.valueOf(fromSection.getString("item")) != null){
            itemBuilder.setMaterial(Material.valueOf(fromSection.getString("item")));
        } else {
            LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Item '" + fromSection.getString("item") + "' in " + file + ". &7Setting to STONE."));
            itemBuilder.setMaterial(Material.STONE);
        }

        if (fromSection.contains("amount") && fromSection.getInt("amount") > 0 && fromSection.getInt("amount") < 65){
            itemBuilder.setAmount(fromSection.getInt("amount"));
        } else {
            itemBuilder.setAmount(1);
        }

        if (fromSection.contains("durability")){
            itemBuilder.setDurability(fromSection.getInt("durability"));
        }

        if (fromSection.contains("display-name")) itemBuilder.setDisplayName(fromSection.getString("display-name"));

        if (fromSection.contains("lore")){
            if (fromSection.getStringList("lore") != null){
                itemBuilder.setLore(fromSection.getStringList("lore"));
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Lore in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("enchantments")){
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            for (String enchantmentStr : fromSection.getStringList("enchantments")){
                String[] enchantmentSplit = enchantmentStr.split(":");
                if (EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchantmentSplit[0])) != null){
                    Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchantmentSplit[0]));

                    try {
                        int level = Integer.parseInt(enchantmentSplit[1]);
                        enchantments.put(enchantment, level);
                    } catch (NumberFormatException e){
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Enchantment Level '" + enchantmentSplit[1] + "' in " + file + ". Ignoring."));
                    }
                } else {
                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Enchantment '" + enchantmentSplit[0] + "' in " + file + ". Ignoring."));
                }
            }

            itemBuilder.setEnchantments(enchantments);
        }

        if (fromSection.contains("item-flags")){
            List<ItemFlag> itemFlags = new ArrayList<>();
            if (fromSection.getStringList("item-flags") != null){
                for (String flag : fromSection.getStringList("item-flags")){
                    if (ItemFlag.valueOf(flag) != null){
                        itemFlags.add(ItemFlag.valueOf(flag));
                    } else {
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Item Flag '" + flag + "' in " + file + ". Ignoring."));
                    }
                }
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Item Flags in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("unbreakable")) itemBuilder.setUnbreakable(fromSection.getBoolean("unbreakable"));
        if (fromSection.contains("glowing")) itemBuilder.setGlowing(fromSection.getBoolean("glowing"));
        if (fromSection.contains("book.title")) itemBuilder.setBookTitle(fromSection.getString("book.title"));
        if (fromSection.contains("book.author")) itemBuilder.setBookAuthor(fromSection.getString("book.author"));

        if (fromSection.contains("book.pages")){
            if (fromSection.getStringList("book.pages") != null){
                itemBuilder.setBookPages(fromSection.getStringList("book.pages"));
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Book Pages in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("armor-color")){
            String[] armorColor = fromSection.getString("armor-color").split(",");
            if (armorColor.length == 3){
                try {
                    int armorR = Integer.parseInt(armorColor[0]);
                    int armorG = Integer.parseInt(armorColor[1]);
                    int armorB = Integer.parseInt(armorColor[2]);

                    Color color = Color.fromRGB(armorR, armorG, armorB);
                    itemBuilder.setLeatherArmorColor(color);
                } catch (NumberFormatException e){
                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Armor Color in " + file + ". Ignoring."));
                }
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Armor Color in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("potion-color")){
            String[] potionColor = fromSection.getString("potion-color").split(",");
            if (potionColor.length == 3){
                try {
                    int potionR = Integer.parseInt(potionColor[0]);
                    int potionG = Integer.parseInt(potionColor[1]);
                    int potionB = Integer.parseInt(potionColor[2]);

                    Color color = Color.fromRGB(potionR, potionG, potionB);
                    itemBuilder.setPotionColor(color);
                } catch (NumberFormatException e){
                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Potion Color in " + file + ". Ignoring."));
                }
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Potion Color in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("potion")){
            List<PotionEffect> potionEffects = new ArrayList<>();

            for (String potionStr : fromSection.getConfigurationSection("potion").getKeys(false)){
                if (fromSection.contains("potion." + potionStr + ".type")){
                    if (PotionEffectType.getByName(fromSection.getString("potion." + potionStr + ".type")) != null){
                        PotionEffectType type = PotionEffectType.getByName(fromSection.getString("potion." + potionStr + ".type"));
                        int duration = fromSection.contains("potion." + potionStr + ".duration") ? fromSection.getInt("potion." + potionStr + ".duration") : 20*60;
                        int amplifier = fromSection.contains("potion." + potionStr + ".amplifier") ? fromSection.getInt("potion." + potionStr + ".amplifier") : 1;
                        boolean ambient = fromSection.contains("potion." + potionStr + ".ambient") ? fromSection.getBoolean("potion." + potionStr + ".ambient") : false;
                        boolean particles = fromSection.contains("potion." + potionStr + ".particles") ? fromSection.getBoolean("potion." + potionStr + ".particles") : true;
                        boolean icon = fromSection.contains("potion." + potionStr + ".icon") ? fromSection.getBoolean("potion." + potionStr + ".icon") : true;

                        potionEffects.add(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
                    } else {
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Potion Type in " + file + ". Ignoring."));
                    }
                } else {
                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Potion Type in " + file + ". Ignoring."));
                }
            }

            itemBuilder.setPotionEffects(potionEffects);
        }

        if (fromSection.contains("skull-owner")){
            OfflinePlayer player = Bukkit.getOfflinePlayer(fromSection.getString("skull-owner"));
            itemBuilder.setSkullOwner(player);
        }

        if (fromSection.contains("banner")){
            if (fromSection.getStringList("banner") != null){
                List<Pattern> patterns = new ArrayList<>();

                for (String bannerStr : fromSection.getStringList("banner")){
                    String[] bannerSplit = bannerStr.split(":");
                    if (bannerSplit.length == 2){
                        if (DyeColor.valueOf(bannerSplit[0]) != null){
                            DyeColor color = DyeColor.valueOf(bannerSplit[0]);

                            if (PatternType.valueOf(bannerSplit[1]) != null){
                                PatternType type = PatternType.valueOf(bannerSplit[1]);
                                Pattern pattern = new Pattern(color, type);
                                patterns.add(pattern);
                            } else {
                                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Banner Pattern Type in " + file + ". Ignoring."));
                            }
                        } else {
                            LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Banner Dye Color in " + file + ". Ignoring."));
                        }
                    } else {
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Banner in " + file + ". Ignoring."));
                    }
                }

                itemBuilder.setBannerPatterns(patterns);
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Banner in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("tropical-fish")){
            DyeColor bodyColor = null;
            DyeColor patternColor = null;
            TropicalFish.Pattern pattern = null;

            if (fromSection.contains("tropical-fish.body-color")){
                if (DyeColor.valueOf(fromSection.getString("tropical-fish.body-color")) != null) {
                    bodyColor = DyeColor.valueOf(fromSection.getString("tropical-fish.body-color"));
                }
            }

            if (fromSection.contains("tropical-fish.pattern-color")){
                if (DyeColor.valueOf(fromSection.getString("tropical-fish.pattern-color")) != null){
                    patternColor = DyeColor.valueOf(fromSection.getString("tropical-fish.pattern-color"));
                }
            }

            if (fromSection.contains("tropical-fish.pattern")){
                if (TropicalFish.Pattern.valueOf(fromSection.getString("tropical-fish.pattern")) != null){
                    pattern = TropicalFish.Pattern.valueOf(fromSection.getString("tropical-fish.pattern"));
                }
            }

            itemBuilder.setTropicalFishData(patternColor, pattern, bodyColor);
        }

        if (fromSection.contains("spawner-type")){
            if (EntityType.valueOf(fromSection.getString("spawner-type")) != null){
                itemBuilder.setSpawnerType(EntityType.valueOf(fromSection.getString("spawner-type")));
            } else {
                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Spawner Type in " + file + ". Ignoring."));
            }
        }

        if (fromSection.contains("firework")){
            int power = (fromSection.contains("firework.power")) ? fromSection.getInt("firework.power") : 1;
            itemBuilder.setFireworkPower(power);

            List<FireworkEffect> fireworkEffects = new ArrayList<>();
            for (String fireworkStr : fromSection.getConfigurationSection("firework").getKeys(false)) {
                if (fromSection.getStringList("firework." + fireworkStr + ".colors") != null) {
                    boolean flicker = false;
                    boolean trail = false;
                    FireworkEffect.Type type = null;
                    Color[] fadeColors = null;
                    Color[] colors = new Color[fromSection.getStringList("firework." + fireworkStr + ".colors").size()];

                    if (fromSection.getStringList("firework." + fireworkStr + ".fade-colors") != null) {
                        fadeColors = new Color[fromSection.getStringList("firework." + fireworkStr + ".fade-colors").size()];

                        for (String fadeColorStr : fromSection.getStringList("firework." + fireworkStr + ".fade-colors")) {
                            String[] colorSplit = fadeColorStr.split(",");
                            if (colorSplit.length == 3) {
                                try {
                                    int colorR = Integer.parseInt(colorSplit[0]);
                                    int colorG = Integer.parseInt(colorSplit[1]);
                                    int colorB = Integer.parseInt(colorSplit[2]);
                                    Color color = Color.fromRGB(colorR, colorG, colorB);
                                    fadeColors[fadeColors.length] = color;
                                } catch (NumberFormatException e) {
                                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Colors in " + file + ". Ignoring."));
                                }
                            } else {
                                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Colors in " + file + ". Ignoring."));
                            }
                        }
                    }

                    if (fromSection.contains("firework." + fireworkStr + ".flicker"))
                        flicker = fromSection.getBoolean("firework." + fireworkStr + ".flicker");
                    if (fromSection.contains("firework." + fireworkStr + ".trail"))
                        trail = fromSection.getBoolean("firework." + fireworkStr + ".trail");
                    if (fromSection.contains("firework." + fireworkStr + ".type") || FireworkEffect.Type.valueOf(fromSection.getString("firework." + fireworkStr + ".type")) != null) {
                        type = FireworkEffect.Type.valueOf(fromSection.getString("firework." + fireworkStr + ".type"));
                    } else {
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Type in " + file + ". Ignoring."));
                    }

                    for (String colorStr : fromSection.getStringList("firework." + fireworkStr + ".fade-colors")) {
                        String[] colorSplit = colorStr.split(",");
                        if (colorSplit.length == 3) {
                            try {
                                int colorR = Integer.parseInt(colorSplit[0]);
                                int colorG = Integer.parseInt(colorSplit[1]);
                                int colorB = Integer.parseInt(colorSplit[2]);
                                Color color = Color.fromRGB(colorR, colorG, colorB);
                                colors[colors.length] = color;
                            } catch (NumberFormatException e) {
                                LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Colors in " + file + ". Ignoring."));
                            }
                        } else {
                            LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Colors in " + file + ". Ignoring."));
                        }
                    }

                    if (type != null){
                        if (fadeColors != null || fadeColors.length != 0){
                            FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(flicker).trail(trail).with(type).withFade(fadeColors).withColor(colors).build();
                        } else {
                            FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(flicker).trail(trail).with(type).withColor(colors).build();
                        }
                    } else {
                        LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Type in " + file + ". Ignoring."));
                    }
                } else {
                    LilAirdrops.getInstance().sendConsoleMessage(Formatting.format("[LilAirdrops] &cInvalid Firework Colors in " + file + ". Ignoring."));
                }
            }
        }

        return itemBuilder.build();
    }

    public static Map<String, Object> serializeItemStack(ItemStack itemStack){
        if (itemStack == null) return null;
        Map<String, Object> itemData = new HashMap<>();

        if (itemStack.getType() != null) itemData.put("item", itemStack.getType().toString());
        itemData.put("amount", itemStack.getAmount());
        if (itemStack.hasItemMeta()){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta instanceof Damageable) itemData.put("durability", ((Damageable) itemStack.getItemMeta()).getDamage());
            if (itemMeta.hasDisplayName()) itemData.put("display-name", itemStack.getItemMeta().getDisplayName());
            if (itemMeta.hasLore()) itemData.put("lore", itemMeta.getLore());
            if (itemMeta.hasEnchants()) itemData.put("enchantments", itemMeta.getEnchants());
            if (itemMeta instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) itemMeta).hasStoredEnchants()) itemData.put("enchantments", ((EnchantmentStorageMeta) itemMeta).getStoredEnchants());
            if (itemMeta.getItemFlags().size() > 0) itemData.put("item-flags", itemMeta.getItemFlags());
            if (itemMeta.isUnbreakable()) itemData.put("unbreakable", true);
            if (itemMeta instanceof BookMeta && ((BookMeta) itemMeta).hasTitle()) itemData.put("book-title", ((BookMeta) itemMeta).getTitle());
            if (itemMeta instanceof BookMeta && ((BookMeta) itemMeta).hasAuthor()) itemData.put("book-author", ((BookMeta) itemMeta).getAuthor());
            if (itemMeta instanceof BookMeta && ((BookMeta) itemMeta).hasPages()) itemData.put("book-pages", ((BookMeta) itemMeta).getPages());
            if (itemMeta instanceof FireworkEffectMeta && ((FireworkEffectMeta) itemMeta).hasEffect()) itemData.put("firework-effects", Arrays.asList(((FireworkEffectMeta) itemMeta).getEffect()));
            if (itemMeta instanceof FireworkMeta && ((FireworkMeta) itemMeta).hasEffects()) itemData.put("firework-effects", ((FireworkMeta) itemMeta).getEffects());
            if (itemMeta instanceof FireworkMeta) itemData.put("firework-power", ((FireworkMeta) itemMeta).getPower());
            if (itemMeta instanceof LeatherArmorMeta) itemData.put("armor-color", ((LeatherArmorMeta) itemMeta).getColor());
            if (itemMeta instanceof PotionMeta && ((PotionMeta) itemMeta).hasCustomEffects()) itemData.put("potion-effects", ((PotionMeta) itemMeta).getCustomEffects());
            if (itemMeta instanceof PotionMeta && ((PotionMeta) itemMeta).hasColor()) itemData.put("potion-color", ((PotionMeta) itemMeta).getColor());
            if (itemMeta instanceof SkullMeta && ((SkullMeta) itemMeta).hasOwner()) itemData.put("skull-owner", ((SkullMeta) itemMeta).getOwningPlayer().getName());
            if (itemMeta instanceof BannerMeta && ((BannerMeta) itemMeta).getPatterns().size() > 0) itemData.put("banner", ((BannerMeta) itemMeta).getPatterns());
            if (itemMeta instanceof TropicalFishBucketMeta) {
                itemData.put("fish-pattern-color", ((TropicalFishBucketMeta) itemMeta).getPatternColor());
                itemData.put("fish-pattern", ((TropicalFishBucketMeta) itemMeta).getPattern());
                itemData.put("fish-body-color", ((TropicalFishBucketMeta) itemMeta).getBodyColor());
            }
            if (itemStack.getType() == Material.SPAWNER){
                BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
                CreatureSpawner creatureSpawner = (CreatureSpawner) blockStateMeta.getBlockState();
                EntityType entity = creatureSpawner.getSpawnedType();
                itemData.put("spawner-type", entity.toString());
            }
        }

        return itemData;
    }
}
