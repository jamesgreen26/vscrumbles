package net.g_mungus.vscrumbles.util;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Crumble {


    public static void breakOff(World world, List<BlockPos> blocks) {
        for (BlockPos pos : blocks) {
            BlockState state = world.getBlockState(pos);
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            fallingBlockEntity.timeFalling = 1;
            fallingBlockEntity.setHurtEntities(5.0f, 5);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.spawnEntity(fallingBlockEntity);
        }
    }
}
