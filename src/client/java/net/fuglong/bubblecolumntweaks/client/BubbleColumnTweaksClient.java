package net.fuglong.bubblecolumntweaks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuglong.bubblecolumntweaks.BubbleColumnTweaksMod;
import net.fuglong.bubblecolumntweaks.network.BubbleColumnSyncPayload;

public class BubbleColumnTweaksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(BubbleColumnSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                BubbleColumnTweaksMod.setServerConfig(payload.config());
                BubbleColumnTweaksMod.LOGGER.info("Synced config from server");
            });
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            BubbleColumnTweaksMod.setServerConfig(null);
            BubbleColumnTweaksMod.LOGGER.info("Cleared server config");
        });
    }
}
