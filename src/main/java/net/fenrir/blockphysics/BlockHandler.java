package net.fenrir.blockphysics;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHandler {


    public static void fall(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        fallingBlockEntity.timeFalling = 1;
        fallingBlockEntity.setHurtEntities(5.0f, 5);
        world.setBlockState(pos,Blocks.AIR.getDefaultState());
        world.spawnEntity(fallingBlockEntity);
    }

    public static void updateNeighbors(World world, BlockPos pos) {
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int c = -1; c <= 1; c++) {
                    if (a != 0 || b != 0 || c != 0) {
                        update(world, pos.north(a).east(b).up(c));
                    }
                }
            }
        }
    }
    public static void update(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(!state.isAir()){
            int touchingBlocks = 0;

            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    for (int c = -1; c <= 1; c++) {
                        if (a != 0 || b != 0 || c != 0) {
                            if (world.getBlockState(pos.north(a).east(b).up(c)).isAir()){
                                touchingBlocks ++;
                            }
                        }
                    }
                }
            }
            if (touchingBlocks == 26) {
                fall(world, pos);
            }
        }
    }
}
