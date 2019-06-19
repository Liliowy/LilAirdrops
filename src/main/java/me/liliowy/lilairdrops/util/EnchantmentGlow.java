package me.liliowy.lilairdrops.util;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentGlow extends Enchantment {

    public EnchantmentGlow(NamespacedKey key){
        super(key);
    }

    @Override
    public boolean canEnchantItem(ItemStack arg0){
        return true;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public int getMaxLevel(){
        return 1;
    }

    @Override
    public String getName() {
        return "LilAirdrops-Glow";
    }

    @Override
    public int getStartLevel(){
        return 1;
    }

    @Override
    public boolean isCursed(){
        return false;
    }

    @Override
    public boolean isTreasure(){
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment arg0){
        return false;
    }
}
