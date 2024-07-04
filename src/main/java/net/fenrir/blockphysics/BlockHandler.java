package net.fenrir.blockphysics;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.BlockingQueue;

public class BlockHandler {

    public static boolean blockFloats(Block origin) {
        return origin.getSlipperiness() > 0.6f ||origin.getDefaultState().isIn(BlockPhysics.FLOATS);
    }

    public static boolean blockNotFall(World world, BlockPos pos, Block origin) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir()) {
            return false;
        }
        if (state.isLiquid()) {
            return blockFloats(origin);
        }
        return true;
    }

    public static boolean blockViable(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (blockNotFall(world,pos,block)) {
            float resis = state.getBlock().getBlastResistance();
            return resis > 0.0f;
        }
        return false;
    }

    public static boolean upEncased(World world, BlockPos pos) {
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int c = 0; c <= 1; c++) {
                    if (!blockViable(world, pos.north(a).east(b).up(c))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean upCheck(World world, BlockPos pos) {
        if (!blockViable(world,pos)) {
            return false;
        }
        float resis = world.getBlockState(pos).getBlock().getBlastResistance();
        if (resis >= 1200 || upEncased(world,pos)) {
            return true;
        }
        return upCheck(world,pos.up());
    }

    public static boolean isSticky(BlockState state) {
        return state.isIn(BlockPhysics.STICKY_BLOCKS) || state.getSoundGroup() == BlockSoundGroup.GLASS;
    }

    public static boolean check(World world, BlockPos pos, int Recursion) {
        if (!blockViable(world, pos)) {
            if (isSticky(world.getBlockState(pos.up(Recursion)))) {
                return upCheck(world, pos.up(Recursion));
            } else if (isSticky(world.getBlockState(pos.up(Recursion + 1)))) {
                return upCheck(world, pos.up(Recursion + 1));
            }
            return false;
        }
        float resis = world.getBlockState(pos).getBlock().getBlastResistance();
        if (resis >= 1200 || Encased(world, pos)) {
            return true;
        }
        return check(world, pos.down(), Recursion + 1);
    }

    public static int Reach(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        double resis = block.getBlastResistance();
        if (state.isIn(BlockPhysics.PROTECTED_BLOCKS)) {
            resis = Math.max(resis,3.0D);
        }
        return (int) Math.min(Math.ceil(resis),50);
    }

    public static boolean Encased(World world, BlockPos pos) {
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int c = 0; c <= 1; c++) {
                    if (!blockViable(world, pos.north(a).east(b).down(c))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean scan(World world, BlockPos pos) {
        if (
                (world.getBlockState(pos).isIn(BlockPhysics.STICKY_BLOCKS) && upCheck(world,pos)) ||
                (world.getBlockState(pos.up()).isIn(BlockPhysics.STICKY_BLOCKS)) && upCheck(world,pos.up())
        ) {
            return true;
        }
        int reach = Reach(world,pos);
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a != 0 || b != 0) {
                    int i = 1;
                    int goal = reach;
                    while (i <= goal) {
                        if (blockViable(world,pos.north(a * i).east(b * i))) {
                            if (check(world, pos.north(a * i).east(b * i),0)) {
                                return true;
                            }
                            int oReach = Reach(world,pos.north(a * i).east(b * i));
                            if (oReach > goal) {
                                goal = oReach;
                            }
                        } else {
                            break;
                        }
                        i = i + 1;
                    }
                }
            }
        }
        return false;
    }
    public static void fall(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block.getDefaultState().isIn(BlockPhysics.USE_DEFAULT_STATE)) {
            state = block.getDefaultState();
        }
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
        if (block.getBlastResistance() < 1200 && blockViable(world,pos)) {
            if (!blockNotFall(world,pos.down(),block) && !scan(world,pos)) {
                if (!(block instanceof BlockWithEntity)) {
                    fall(world, pos);
                } else {
                    world.breakBlock(pos,true);
                }
                updateNeighbors(world,pos);
            }
        }
    }
}
