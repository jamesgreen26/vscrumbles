package net.g_mungus.vscrumbles.util;


import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;

import static net.g_mungus.vscrumbles.util.Crumble.*;

public class CrumbleChecker {
    BlockPos origin;
    World world;
    LinkedList<BlockPos> blocksToCrumble;


    public CrumbleChecker(World world, BlockPos origin) {
        this.origin = origin;
        this.world = world;
        blocksToCrumble = new LinkedList<>();



        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int c = -1; c <= 1; c++) {
                    if (a != 0 || b != 0 || c != 0) {
                        BlockPos pos = origin.north(a).east(b).up(c);
                        if (!world.getBlockState(pos).isAir() && !blocksToCrumble.contains(pos)) {
                            BlockScan scan = new BlockScan(world, pos);
                            blocksToCrumble.addAll(scan.getBlocks());
                        }
                    }
                }
            }
        }

        breakOff(world,blocksToCrumble);

    }


}
