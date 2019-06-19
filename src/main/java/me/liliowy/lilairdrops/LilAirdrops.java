package me.liliowy.lilairdrops;

import me.liliowy.lilairdrops.commands.CommandLilAirdrops;
import me.liliowy.lilairdrops.gui.GUIHandler;
import me.liliowy.lilairdrops.listeners.EventInventoryClick;
import me.liliowy.lilairdrops.listeners.EventInventoryClose;
import me.liliowy.lilairdrops.storage.AirdropHandler;
import me.liliowy.lilairdrops.storage.Language;
import me.liliowy.lilairdrops.storage.LilFile;
import me.liliowy.lilairdrops.storage.RandomAirdropHandler;
import me.liliowy.lilairdrops.util.EnchantmentGlow;
import me.liliowy.lilairdrops.util.Formatting;
import me.liliowy.lilairdrops.util.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LilAirdrops extends JavaPlugin {

    private static LilAirdrops instance;
    private LilFile randomAirdropFile, airdropsFile, languageFile, templateFile;
    private List<LilFile> airdropFiles;
    private PluginManager pluginManager;
    private ConsoleCommandSender console;
    private EnchantmentGlow glowEnchantment;
    private Language language;
    private AirdropHandler airdropHandler;
    private RandomAirdropHandler randomAirdropHandler;
    private GUIHandler guiHandler;

    @Override
    public void onEnable(){
        instance = this;
        pluginManager = getServer().getPluginManager();
        console = Bukkit.getConsoleSender();
        MetricsLite metrics = new MetricsLite(this);
        glowEnchantment = new EnchantmentGlow(new NamespacedKey(this, "lilairdrops-glow"));

        init();

        getCommand("lilairdrops").setExecutor(new CommandLilAirdrops());
        EventInventoryClick inventoryClick = new EventInventoryClick();
        EventInventoryClose inventoryClose = new EventInventoryClose();
    }

    public void init(){
        registerGlowEnchantment();
        languageFile = new LilFile("language.yml");
        airdropsFile = new LilFile("airdrops.yml");
        randomAirdropFile = new LilFile("random.yml");

        File airdropsFolder = new File(getDataFolder() + "/airdrops");
        airdropFiles = new ArrayList<>();

        if (airdropsFolder.exists()){
            if (airdropsFolder.list().length > 0){
                for (String fileName : airdropsFolder.list()){
                    airdropFiles.add(new LilFile("airdrops\\" + fileName));
                }
            } else {
                createTemplateFile();
            }
        } else {
            createTemplateFile();
        }

        language = new Language();
        airdropHandler = new AirdropHandler();
        randomAirdropHandler = new RandomAirdropHandler();

        guiHandler = new GUIHandler();
    }

    public void createTemplateFile(){
        templateFile = new LilFile("airdrops\\basic.yml");
        airdropFiles.add(templateFile);
    }

    public static LilAirdrops getInstance(){
        return instance;
    }

    public void sendConsoleMessage(String message){
        console.sendMessage(message);
    }

    public Language getLanguage(){
        return language;
    }

    public AirdropHandler getAirdropHandler(){
        return airdropHandler;
    }

    public RandomAirdropHandler getRandomAirdropHandler(){
        return randomAirdropHandler;
    }

    public LilFile getRandomAirdropFile(){
        return randomAirdropFile;
    }

    public LilFile getAirdropsFile(){
        return airdropsFile;
    }

    public LilFile getLanguageFile(){
        return languageFile;
    }

    public LilFile getTemplateFile(){
        return templateFile;
    }

    public List<LilFile> getAirdropFiles(){
        return airdropFiles;
    }

    public EnchantmentGlow getGlowEnchantment(){
        return glowEnchantment;
    }

    public GUIHandler getGuiHandler(){
        return guiHandler;
    }

    public PluginManager getPluginManager(){
        return pluginManager;
    }

    private void registerGlowEnchantment(){
        try {
            Field acceptingNewField = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNewField.setAccessible(true);
            acceptingNewField.set(null, true);
            acceptingNewField.setAccessible(false);

            Field byKeyField = Enchantment.class.getDeclaredField("byKey");
            byKeyField.setAccessible(true);

            Map<NamespacedKey, Enchantment> byKey = (Map<NamespacedKey, Enchantment>) byKeyField.get(null);

            if (!byKey.containsValue(glowEnchantment)){
                Enchantment.registerEnchantment(glowEnchantment);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
            sendConsoleMessage(Formatting.format(Language.FAILED_LOAD_GLOW_ENCHANTMENT));
        }
    }
}
