package net.g_mungus.vscrumbles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.wispforest.owo.config.Option;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.g_mungus.vscrumbles.config.VSCrumblesConfig;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static net.minecraft.command.argument.BlockArgumentParser.INVALID_BLOCK_ID_EXCEPTION;

public class VSCrumbles implements ModInitializer {
    public static final String MOD_ID = "vscrumbles";
    public static final String MOD_NAME = "VS - Crumbles";
    public static final VSCrumblesConfig CONFIG = VSCrumblesConfig.createAndLoad();

    private static List<Block> specialBlocks = new ArrayList<>();


    private void updateSpecialBlocks() {
        specialBlocks.clear();
        for (String entry: CONFIG.specialBlocks()) {
            try {
                StringReader reader = new StringReader(entry);
                Identifier id = Identifier.fromCommandInput(reader);
                Block block = (Block)((RegistryEntry.Reference<?>)Registries.BLOCK.getReadOnlyWrapper().getOptional(RegistryKey.of(RegistryKeys.BLOCK, id)).orElseThrow(() -> INVALID_BLOCK_ID_EXCEPTION.createWithContext(reader, id.toString()))).value();
                specialBlocks.add(block);
            } catch (CommandSyntaxException e) {
                Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + "Block id :" + entry + "not recognized.");
            }

        }
        Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + " specialBlocks:" + specialBlocks.toString());
    }

    public static List<Block> getSpecialBlocks() {
        return specialBlocks;
    }

    @Override
    public void onInitialize() {
        Logger.getLogger(MOD_ID).log(Level.INFO, "[" + MOD_NAME + "]" + "Initialized");

        updateSpecialBlocks();
        Objects.requireNonNull(CONFIG.optionForKey(CONFIG.keys.specialBlocks)).observe(a -> updateSpecialBlocks());


    }
}

