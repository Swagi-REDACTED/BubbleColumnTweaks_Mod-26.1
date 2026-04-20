package net.fuglong.bubblecolumntweaks.mixin;

import net.fuglong.bubblecolumntweaks.BubbleColumnTweaksMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Mixin to tweak bubble column physics for Minecraft 26.1 (unobfuscated)
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "onInsideBubbleColumn", at = @At("HEAD"), cancellable = true)
    private void modifyBubbleColumnDrag(boolean down, CallbackInfo ci) {
        ci.cancel();

        Entity entity = (Entity) (Object) this;
        Vec3 velocity = entity.getDeltaMovement();

        double newY = down
            ? Math.max(BubbleColumnTweaksMod.getActiveConfig().magmaSpeedCap, velocity.y + BubbleColumnTweaksMod.getActiveConfig().magmaAcceleration)
            : Math.min(BubbleColumnTweaksMod.getActiveConfig().soulSandSpeedCap, velocity.y + BubbleColumnTweaksMod.getActiveConfig().soulSandAcceleration);

        entity.setDeltaMovement(new Vec3(velocity.x, newY, velocity.z));
        entity.resetFallDistance();
    }

    @Inject(method = "onAboveBubbleColumn", at = @At("HEAD"), cancellable = true)
    private void modifyBubbleColumnSurfaceDrag(boolean down, BlockPos pos, CallbackInfo ci) {
        ci.cancel();

        Entity entity = (Entity) (Object) this;
        Vec3 velocity = entity.getDeltaMovement();

        double newY = down
            ? Math.max(BubbleColumnTweaksMod.getActiveConfig().magmaSurfaceSpeedCap, velocity.y + BubbleColumnTweaksMod.getActiveConfig().magmaSurfaceAcceleration)
            : Math.min(BubbleColumnTweaksMod.getActiveConfig().soulSandSurfaceSpeedCap, velocity.y + BubbleColumnTweaksMod.getActiveConfig().soulSandSurfaceAcceleration);

        entity.setDeltaMovement(new Vec3(velocity.x, newY, velocity.z));
    }
}