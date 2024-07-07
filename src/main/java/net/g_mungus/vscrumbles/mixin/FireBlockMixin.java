package net.g_mungus.vscrumbles.mixin;

import net.g_mungus.vscrumbles.util.CrumbleChecker;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.valkyrienskies.mod.common.VSGameUtilsKt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends BlockMixin {

    @Inject(method = "trySpreadingFire", at = @At("RETURN"), cancellable = true)
    protected void fireSpread(World world, BlockPos pos, int spreadFactor, Random random, int currentAge, CallbackInfo ci) {
        if (!world.isClient()) {

            if (VSGameUtilsKt.isBlockInShipyard((World) world, pos)) {
                if (world.getBlockState(pos).isAir()) {
                    CrumbleChecker checker = new CrumbleChecker(world, pos);
                }
            }
        }
    }
}
