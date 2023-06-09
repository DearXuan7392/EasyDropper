package com.dearxuan.easydropper.Config;


import com.dearxuan.easydropper.EntryPoint.EasyDropper;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.AbstractFieldBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.lang.reflect.Field;

@Environment(EnvType.CLIENT)
public class ModMenu implements ModMenuApi {

    private static final String ModId = "easydropper";

    private static final String ModName = "Easy Dropper";

    public static boolean CanModRun = false;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return parent -> {
            try{
                CanModRun = MinecraftClient.getInstance().world == null || MinecraftClient.getInstance().isInSingleplayer();
                ConfigBuilder builder = ConfigBuilder
                        .create()
                        .setParentScreen(parent)
                        .setTitle(Text.translatable(ModId + ".title"));
                builder.setSavingRunnable(ModSaver::Save);
                ConfigCategory general = builder
                        .getOrCreateCategory(Text.translatable(ModId + ".title"));
                ConfigEntryBuilder entryBuilder = builder.entryBuilder();
                general
                        .addEntry(BuildConfig(entryBuilder, "DROPPER_AUTO_DISPENSE", false))
                        //.addEntry(BuildConfig(entryBuilder, "DISPENSER_AUTO_DISPENSE", false))
                        .addEntry(BuildConfig(entryBuilder, "BETTER_ITEM_POSITION", false))
                        .addEntry(BuildConfig(entryBuilder, "DROPPER_COOLDOWN", 8))
                        //.addEntry(BuildConfig(entryBuilder, "DISPENSER_COOLDOWN", 8))
                        .addEntry(BuildConfig(entryBuilder, "DISPENSE_SPEED", 2))
                        .addEntry(BuildConfig(entryBuilder, "DISPENSE_DISTANCE", 0.2));
                Screen screen = builder.build();
                return screen;
            }catch (Exception e){
                EasyDropper.LOGGER.error(e.getMessage());
                return null;
            }
        };
    }

    private static <T> AbstractConfigListEntry BuildConfig(ConfigEntryBuilder configEntryBuilder, String name, T defaultValue){
        try{
            switch (ModConfig.class.getDeclaredField(name).getType().getSimpleName()){
                case "int":
                    return BuildConfig(configEntryBuilder, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
                case "double":
                    return BuildConfig(configEntryBuilder, name, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE);
                case "boolean":
                    return BuildConfig(configEntryBuilder, name, defaultValue, null, null);
                default:
                    return null;
            }
        } catch (NoSuchFieldException e) {
            EasyDropper.LOGGER.error(e.getMessage());
            return null;
        }
    }

    private static <T> AbstractConfigListEntry BuildConfig(ConfigEntryBuilder configEntryBuilder, String name, T defaultValue, T min, T max){
        try{
            Field field = ModConfig.class.getDeclaredField(name);
            AbstractFieldBuilder builder;
            switch (field.getType().getSimpleName()){
                case "int":
                    builder = configEntryBuilder
                            .startIntField(GetString(name), field.getInt(ModConfig.INSTANCE))
                            .setDefaultValue((Integer) defaultValue)
                            .setMin((Integer) min)
                            .setMax((Integer) max)
                            .setTooltip(GetTooltip(name));
                    break;
                case "double":
                    builder = configEntryBuilder
                            .startDoubleField(GetString(name), field.getDouble(ModConfig.INSTANCE))
                            .setDefaultValue((Double) defaultValue)
                            .setMin((Double) min)
                            .setMax((Double) max)
                            .setTooltip(GetTooltip(name));
                    break;
                case "boolean":
                    builder = configEntryBuilder
                            .startBooleanToggle(GetString(name), field.getBoolean(ModConfig.INSTANCE))
                            .setDefaultValue((Boolean) defaultValue)
                            .setTooltip(GetTooltip(name));
                    break;
                default:
                    return null;
            }
            builder.setSaveConsumer(value -> {
                try {
                    field.set(ModConfig.INSTANCE, value);
                } catch (IllegalAccessException e) {
                    EasyDropper.LOGGER.error(e.getMessage());
                }
            });
            return builder.build();
        } catch (Exception e) {
            EasyDropper.LOGGER.error(e.getMessage());
            return null;
        }
    }

    private static MutableText GetString(String key){
        MutableText mutableText = Text.translatable(ModId + "." + key);
        // 非单人模式或房主, 无法修改游戏功能
        if(!CanModRun){
            mutableText.setStyle(Style.EMPTY.withFormatting(Formatting.STRIKETHROUGH));
        }
        return mutableText;
    }

    private static MutableText GetTooltip(String key){
        MutableText mutableText = Text.translatable(ModId + "." + key + ".tooltip");
        // 非单人模式或房主, 无法修改游戏功能
        if(!CanModRun){
            MutableText warn = Text
                    .translatable("easymod.DONT_WORK_IN_SERVER")
                    .setStyle(Style.EMPTY.withColor(0xFF0000));
            mutableText.append("\n").append(warn);
        }
        return mutableText;
    }
}