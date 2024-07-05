package net.fenrir.blockphysics

import net.minecraft.block.Blocks
import net.minecraft.entity.FallingBlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockHandler {


    companion object {
        private fun fall(world: World, pos: BlockPos?) {
            val state = world.getBlockState(pos)
            val fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state)
            fallingBlockEntity.timeFalling = 1
            fallingBlockEntity.setHurtEntities(5.0f, 5)
            world.setBlockState(pos, Blocks.AIR.defaultState)
            world.spawnEntity(fallingBlockEntity)
        }

        @JvmStatic
        fun updateNeighbors(world: World, pos: BlockPos) {
            for (a in -1..1) {
                for (b in -1..1) {
                    for (c in -1..1) {
                        if (a != 0 || b != 0 || c != 0) {
                            update(world, pos.north(a).east(b).up(c))
                        }
                    }
                }
            }
        }

        @JvmStatic
        fun update(world: World, pos: BlockPos) {
            val state = world.getBlockState(pos)
            val block = state.block

            if (!state.isAir) {
                var touchingBlocks = 0

                for (a in -1..1) {
                    for (b in -1..1) {
                        for (c in -1..1) {
                            if (a != 0 || b != 0 || c != 0) {
                                if (world.getBlockState(pos.north(a).east(b).up(c)).isAir) {
                                    touchingBlocks++
                                }
                            }
                        }
                    }
                }
                if (touchingBlocks == 26) {
                    fall(world, pos)
                }
            }
        }
    }
}