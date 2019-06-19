package me.liliowy.lilairdrops.storage;

import me.liliowy.lilairdrops.LilAirdrops;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LilFile {

    private LilAirdrops instance;
    private File file;
    private FileConfiguration config;

    public LilFile(String fileName){
        instance = LilAirdrops.getInstance();

        if (!instance.getDataFolder().exists()){
            instance.getDataFolder().mkdir();
        }

        if (file == null){
            file = new File(instance.getDataFolder(), fileName);
        }

        if (!file.exists()){
            instance.saveResource(fileName, false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return config;
    }

    public File getFile(){
        return file;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
