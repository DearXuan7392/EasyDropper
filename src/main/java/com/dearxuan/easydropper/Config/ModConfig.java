package com.dearxuan.easydropper.Config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
        name = "easydropper"
)
public class ModConfig implements ConfigData {
    @Excluded
    public static ModConfig INSTANCE;

    @Comment("当投掷器中含有物品时,会立即自动投掷")
    public boolean DROPPER_AUTO_DISPENSE = false;

    @Comment("若启用,则投掷器抛出的物品不会左右偏移,且抛射曲线固定")
    public boolean BETTER_ITEM_POSITION = false;

    @Comment("设置投掷器自动投掷的冷却时间")
    public int DROPPER_COOLDOWN = 8;

    @Comment("抛射速度,直接影响到抛射曲线高度;若为负,则会向下抛射")
    public int DISPENSE_SPEED = 2;

    @Comment("抛射距离,与抛射速度一同作用;若为负,则会落于投掷器面前")
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
