package net.fenrir.blockphysics;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockPhysics implements ModInitializer {
    public static final String MOD_ID = "blockphysics";
    public static final String MOD_NAME = "Block Physics";


    @Override
    public void onInitialize() {
        Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + "Initialized");
    }
}

