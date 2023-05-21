package com.dearxuan.easydropper.client;

import com.dearxuan.easydropper.Config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class EasyDropperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.init();
    }
}
