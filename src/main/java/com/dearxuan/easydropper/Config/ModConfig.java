package com.dearxuan.easydropper.Config;

import com.dearxuan.easydropper.EntryPoint.EasyDropper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    @Excluded
    public static ModConfig INSTANCE;

    @Excluded
    private final static String configPath = FabricLoader.getInstance().getGameDir() + "/config/easydropper.json";

    public boolean DROPPER_AUTO_DISPENSE = false;

    public boolean DISPENSER_AUTO_DISPENSE = false;

    public boolean BETTER_ITEM_POSITION = false;

    public int DROPPER_COOLDOWN = 8;

    public int DISPENSER_COOLDOWN = 8;

    public int DISPENSE_SPEED = 2;

    public double DISPENSE_DISTANCE = 0.2;

    public ModConfig(){

    }

    public static boolean Save(){
        return WriteConfig();
    }

    public static void init() {
        if(!ReadConfig()){
            ModConfig.INSTANCE = new ModConfig();
            WriteConfig();
        }
    }

    private static boolean WriteConfig(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try(FileWriter writer = new FileWriter(configPath)){
                writer.write(gson.toJson(ModConfig.INSTANCE));
                return true;
            }
        } catch (Exception e) {
            EasyDropper.LOGGER.error(e.getMessage());
        }
        return false;
    }

    private static boolean ReadConfig(){
        File file = new File(configPath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if(file.exists()){
                try (BufferedReader reader = Files.newBufferedReader(Path.of(configPath))) {
                    ModConfig modConfig = gson.fromJson(reader, ModConfig.class);
                    ModConfig.INSTANCE = modConfig;
                    return true;
                }
            }
        } catch (Exception e){
            EasyDropper.LOGGER.error(e.getMessage());
        }
        return false;
    }
}
