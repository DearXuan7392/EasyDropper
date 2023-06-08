package com.dearxuan.easydropper.EntryPoint;

import com.dearxuan.easydropper.Config.ModConfig;
import com.dearxuan.easydropper.Config.ModSaver;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyDropper implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("easydropper");

    @Override
    public void onInitialize() {
        ModSaver.InitModConfig(ModConfig.class);
    }
}
