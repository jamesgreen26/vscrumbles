package net.g_mungus.vscrumbles.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;


import static net.g_mungus.vscrumbles.VSCrumbles.*;

public class BlockScan {
    private LinkedList<BlockPos> blocks;
    private LinkedList<BlockPos> checked;
    private int currentSize;

    public BlockScan(World world, BlockPos root) {
        blocks = new LinkedList<>();
        checked = new LinkedList<>();
        currentSize = 0;

        blocks.addAll(scanRec(world, root));

        if (currentSize == 0) {
            blocks.add(root);
        }
    }

    private LinkedList<BlockPos> scanRec(World world, BlockPos previous) {



        LinkedList<BlockPos> blocksToAdd = new LinkedList<>();


        if (currentSize > MAX_BLOCKS) {
            return new LinkedList<>();
        }




        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int c = -1; c <= 1; c++) {
                    if (a != 0 || b != 0 || c != 0) {
                        BlockPos block = previous.north(a).east(b).up(c);
                        if (!world.getBlockState(block).isAir() && !checked.contains(block)) {

                            blocksToAdd.add(block);
                            checked.add(block);
                            currentSize++;

                            blocksToAdd.addAll(scanRec(world, block));
                            if (currentSize > MAX_BLOCKS) {
                                return new LinkedList<>();
                            }
                        }
                    }
                }
            }
        }


        return blocksToAdd;
    }

    public LinkedList<BlockPos> getBlocks() {
        return blocks;
    }
}
