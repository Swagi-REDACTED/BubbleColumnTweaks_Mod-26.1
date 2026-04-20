package net.fuglong.bubblecolumntweaks.mixin;

import net.fuglong.bubblecolumntweaks.BubbleColumnTweaksMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    /**
     * Modifies the bubble column collision behavior to make magma blocks pull entities down
     * at the same speed that soul sand pushes them up.
     *
     * In vanilla:
     * - Soul sand (drag=false): y = min(0.7, y + 0.06) - upward push
     * - Magma (drag=true): y = max(-0.3, y - 0.03) - downward pull
     *
     * This mixin changes magma to: y = max(-0.7, y - 0.06) to match soul sand's speed.
     */
    @Inject(method = "applyBubbleColumnEffects(Lnet/minecraft/entity/Entity;Z)V", at = @At("HEAD"), cancellable = true)
    private static void modifyBubbleColumnDrag(Entity entity, boolean drag, CallbackInfo ci) {
        // Cancel vanilla behavior and apply our custom speeds
        ci.cancel();

        Vec3d velocity = entity.getVelocity();
        double newY;

        if (drag) {
            // Magma block (downward)
            // Apply configured acceleration and cap
            newY = Math.max(BubbleColumnTweaksMod.CONFIG.magmaSpeedCap,
                          velocity.y + BubbleColumnTweaksMod.CONFIG.magmaAcceleration);
        } else {
            // Soul sand (upward)
            // Apply configured acceleration and cap
            newY = Math.min(BubbleColumnTweaksMod.CONFIG.soulSandSpeedCap,
                          velocity.y + BubbleColumnTweaksMod.CONFIG.soulSandAcceleration);
        }

        entity.setVelocity(new Vec3d(velocity.x, newY, velocity.z));

        // Mark velocity as modified so it syncs to the client
        entity.velocityModified = true;
    }

    /**
     * Modifies the surface bubble column collision for more dramatic effect when entities
     * reach the surface above magma blocks.
     */
    @Inject(method = "applyBubbleColumnSurfaceEffects(Lnet/minecraft/entity/Entity;ZLnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
    private static void modifyBubbleColumnSurfaceDrag(Entity entity, boolean drag, BlockPos pos, CallbackInfo ci) {
        // Cancel vanilla behavior and apply our custom surface speeds
        ci.cancel();

        Vec3d velocity = entity.getVelocity();
        double newY;

        if (drag) {
            // Magma block surface (stronger downward pull at surface)
            newY = Math.max(BubbleColumnTweaksMod.CONFIG.magmaSurfaceSpeedCap,
                          velocity.y + BubbleColumnTweaksMod.CONFIG.magmaSurfaceAcceleration);
        } else {
            // Soul sand surface (stronger upward launch at surface)
            newY = Math.min(BubbleColumnTweaksMod.CONFIG.soulSandSurfaceSpeedCap,
                          velocity.y + BubbleColumnTweaksMod.CONFIG.soulSandSurfaceAcceleration);
        }

        entity.setVelocity(new Vec3d(velocity.x, newY, velocity.z));

        // Mark velocity as modified so it syncs to the client
        entity.velocityModified = true;
    }
}
