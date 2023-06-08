package com.dearxuan.easydropper.Config;

@ModSaver.ModConfigAnnotation(
        ModId = "easydropper"
)
public class ModConfig {
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
}
