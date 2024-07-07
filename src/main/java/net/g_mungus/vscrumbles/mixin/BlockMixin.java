package net.g_mungus.vscrumbles.mixin;

import net.g_mungus.vscrumbles.util.CrumbleChecker;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.core.api.world.ServerShipWorld;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.util.DimensionIdProvider;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "onBroken", at = @At("RETURN"), cancellable = true)
    protected void blockBroken(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {

        if (!world.isClient()) {
            if (VSGameUtilsKt.isBlockInShipyard((World) world, pos)) {
                CrumbleChecker checker = new CrumbleChecker((World) world, pos);
            }
        }
    }

    @Inject(method = "onDestroyedByExplosion", at = @At("RETURN"), cancellable = true)
    protected void blockExploded(World world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if (!world.isClient()) {
            ServerShipWorld serverShipWorld = (ServerShipWorld) ValkyrienSkiesMod.getVsCore().getHooks().getCurrentShipServerWorld();
            DimensionIdProvider provider = (DimensionIdProvider) world;
            if (VSGameUtilsKt.isBlockInShipyard((World) world, pos)) {
                CrumbleChecker checker = new CrumbleChecker(world, pos);
            }
        }
    }
}
