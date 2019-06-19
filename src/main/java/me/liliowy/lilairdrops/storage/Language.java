package me.liliowy.lilairdrops.storage;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.util.Formatting;
import org.bukkit.configuration.file.FileConfiguration;

public class Language {

    private LilAirdrops instance;
    private FileConfiguration config;
    public static String PREFIX;
    public static String FAILED_LOAD_GLOW_ENCHANTMENT;
    public static String FAILED_SAVE;
    public static String COMMAND_NO_PERMISSION;
    public static String PLAYER_ONLY;
    public static String RELOADED;

    public Language(){
        instance = LilAirdrops.getInstance();
        config = instance.getLanguageFile().getConfig();
        load();
    }

    public void load(){
        PREFIX = config.contains("messages.prefix") ? config.getString("messages.prefix") : "[LilAirdrops]";
        FAILED_LOAD_GLOW_ENCHANTMENT = config.contains("messages.failed-to-load-glow-enchantment") ? config.getString("messages.failed-to-load-glow-enchantment") : "%prefix% &cError: &7The glow enchantment could not be loaded.";
        FAILED_SAVE = config.contains("messages.failed-to-save") ? config.getString("messages.failed-to-save") : "&7Failed to save.";
        COMMAND_NO_PERMISSION = config.contains("messages.no-permission-on-command") ? config.getString("messages.no-permission-on-command") : "%prefix% &cYou do not have permission to do this!";
        PLAYER_ONLY = config.contains("messages.player-only") ? config.getString("messages.player-only") : "%prefix% &cThe console cannot do this!";
        RELOADED = config.contains("messages.reloaded") ? config.getString("messages.reloaded") : "%prefix% &aReloaded.";

        instance.sendConsoleMessage(Formatting.format("[LilAirdrops] &eLanguage file loaded, using: &7" + config.getString("lang")));
    }

    public void save(){
        config.set("messages.prefix", PREFIX);
        config.set("messages.failed-to-load-glow-enchantment", FAILED_LOAD_GLOW_ENCHANTMENT);
        config.set("messages.failed-to-save", FAILED_SAVE);
        config.set("messages.no-permission-on-command", COMMAND_NO_PERMISSION);
        config.set("messages.player-only", PLAYER_ONLY);
        config.set("messages.reloaded", RELOADED);
    }
}
