package com.dearxuan.easydropper.Config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
        name = "easyhopper"
)
public class ModConfig implements ConfigData {
    @Excluded
    public static ModConfig INSTANCE;

    @Comment("当投掷器中含有物品时,会立即自动投掷")
    public boolean DROPPER_AUTO_DISPENSE = false;

    @Comment("设置投掷器自动投掷的冷却时间")
    public int DISPENSE_COOLDOWN = 8;

    public ModConfig(){

    }

    public static void init(){
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        INSTANCE = (ModConfig) AutoConfig
                .getConfigHolder(ModConfig.class)
                .getConfig();
    }
}
