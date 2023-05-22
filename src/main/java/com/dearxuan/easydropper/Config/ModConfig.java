package com.dearxuan.easydropper.Config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(
        name = "easydropper"
)
public class ModConfig implements ConfigData {
    @Excluded
    public static ModConfig INSTANCE;

    public boolean DROPPER_AUTO_DISPENSE = false;

    public boolean DISPENSER_AUTO_DISPENSE = false;

    public boolean BETTER_ITEM_POSITION = false;

    public int DROPPER_COOLDOWN = 8;

    public int DISPENSER_COOLDOWN = 8;

    public int DISPENSE_SPEED = 2;

    public double DISPENSE_DISTANCE = 0.2;

    public ModConfig(){

    }

    public static void init(){
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        INSTANCE = (ModConfig) AutoConfig
                .getConfigHolder(ModConfig.class)
                .getConfig();
    }
}
