package net.g_mungus.vscrumbles;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VSCrumbles implements ModInitializer {
    public static final String MOD_ID = "vscrumbles";
    public static final String MOD_NAME = "VS - Crumbles";
    public static final int MAX_BLOCKS = 5;


    @Override
    public void onInitialize() {
        Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + "Initialized");
    }
}

