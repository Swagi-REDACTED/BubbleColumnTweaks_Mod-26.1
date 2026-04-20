package net.fuglong.bubblecolumntweaks.network;

import net.fuglong.bubblecolumntweaks.BubbleColumnConfig;
import net.fuglong.bubblecolumntweaks.BubbleColumnTweaksMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record BubbleColumnSyncPayload(BubbleColumnConfig config) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BubbleColumnSyncPayload> ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BubbleColumnTweaksMod.MOD_ID, "sync"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, BubbleColumnSyncPayload> CODEC = StreamCodec.of(
        (buf, payload) -> {
            buf.writeBoolean(payload.config.overrideClient);
            buf.writeDouble(payload.config.magmaSpeedCap);
            buf.writeDouble(payload.config.magmaAcceleration);
            buf.writeDouble(payload.config.soulSandSpeedCap);
            buf.writeDouble(payload.config.soulSandAcceleration);
            buf.writeDouble(payload.config.magmaSurfaceSpeedCap);
            buf.writeDouble(payload.config.magmaSurfaceAcceleration);
            buf.writeDouble(payload.config.soulSandSurfaceSpeedCap);
            buf.writeDouble(payload.config.soulSandSurfaceAcceleration);
        },
        buf -> {
            BubbleColumnConfig config = new BubbleColumnConfig();
            config.overrideClient = buf.readBoolean();
            config.magmaSpeedCap = buf.readDouble();
            config.magmaAcceleration = buf.readDouble();
            config.soulSandSpeedCap = buf.readDouble();
            config.soulSandAcceleration = buf.readDouble();
            config.magmaSurfaceSpeedCap = buf.readDouble();
            config.magmaSurfaceAcceleration = buf.readDouble();
            config.soulSandSurfaceSpeedCap = buf.readDouble();
            config.soulSandSurfaceAcceleration = buf.readDouble();
            return new BubbleColumnSyncPayload(config);
        }
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
