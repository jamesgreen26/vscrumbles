package net.fenrir.blockphysics;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockPhysics implements ModInitializer {
    public static final String MOD_ID = "blockphysics";
    public static final String MOD_NAME = "Block Physics";

    //       OLD

    //  public class ModBlockTags {
    //    public static final Tag<Block> EXAMPLE_ORES = TagRegistry.block(new Identifier("tutorial", "example_ores"));
    //  }


    //       New
    //  public class ModBlockTags {
    //    public static final TagKey<Block> EXAMPLE_ORES = TagKey.of(RegistryKeys.BLOCK, new Identifier("tutorial", "example_ores"));
    //  }



    public static final TagKey<Block> STICKY_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID,"sticky_blocks"));

    public static final TagKey<Block> PROTECTED_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID,"protected_blocks"));
    public static final TagKey<Block> FLOATS = TagKey.of(RegistryKeys.BLOCK,new Identifier(MOD_ID,"floats"));
    public static final TagKey<Block> USE_DEFAULT_STATE = TagKey.of(RegistryKeys.BLOCK,new Identifier(MOD_ID,"use_default_state"));

    @Override
    public void onInitialize() {
        Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + "Initialized");
    }
}

