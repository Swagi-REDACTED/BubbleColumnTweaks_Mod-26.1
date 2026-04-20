package net.fuglong.bubblecolumntweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fuglong.bubblecolumntweaks.network.BubbleColumnSyncPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BubbleColumnTweaksMod implements ModInitializer {
    public static final String MOD_ID = "bubblecolumntweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static BubbleColumnConfig CONFIG;
    private static BubbleColumnConfig serverConfig = null;

    @Override
    public void onInitialize() {
        CONFIG = BubbleColumnConfig.load();
        
        // Register networking
        PayloadTypeRegistry.clientboundPlay().register(BubbleColumnSyncPayload.ID, BubbleColumnSyncPayload.CODEC);
        
        // Server join event to sync config
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (CONFIG.overrideClient) {
                sender.sendPacket(new BubbleColumnSyncPayload(CONFIG));
            }
        });

        LOGGER.info("Bubble Column Tweaks mod initialized for 26.1!");
    }

    public static void setServerConfig(BubbleColumnConfig config) {
        serverConfig = config;
    }

    public static BubbleColumnConfig getActiveConfig() {
        return serverConfig != null ? serverConfig : CONFIG;
    }
}