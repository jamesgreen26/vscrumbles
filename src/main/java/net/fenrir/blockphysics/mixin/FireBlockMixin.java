package net.fenrir.blockphysics.mixin;

import net.fenrir.blockphysics.BlockHandler;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.valkyrienskies.core.api.world.ServerShipWorld;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.util.DimensionIdProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends BlockMixin {

    @Inject(method = "trySpreadingFire", at = @At("RETURN"), cancellable = true)
    protected void fireSpread(World world, BlockPos pos, int spreadFactor, Random random, int currentAge, CallbackInfo ci) {
        if (!world.isClient()) {
            ServerShipWorld serverShipWorld = (ServerShipWorld) ValkyrienSkiesMod.getVsCore().getHooks().getCurrentShipServerWorld();
            DimensionIdProvider provider = (DimensionIdProvider) world;

            if (serverShipWorld != null && serverShipWorld.isBlockInShipyard(pos.getX(), pos.getY(), pos.getZ(), provider.getDimensionId())) {
                if (world.getBlockState(pos).isAir()) {
                    BlockHandler.updateNeighbors(world, pos);
                }
            }
        }
    }
}
