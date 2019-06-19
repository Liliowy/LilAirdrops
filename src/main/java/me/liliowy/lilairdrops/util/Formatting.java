package me.liliowy.lilairdrops.util;

import me.liliowy.lilairdrops.storage.Language;
import org.bukkit.ChatColor;

public class Formatting {

    public static String format(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String format(String string, boolean replacePrefix){
        if (replacePrefix){
            return format(string).replaceAll("%prefix%", Language.PREFIX);
        } else {
            return format(string);
        }
    }

    public static String unformat(String string){
        return string.replaceAll("§", "&");
        // This is bad but how tf else do i do it?
    }
}
