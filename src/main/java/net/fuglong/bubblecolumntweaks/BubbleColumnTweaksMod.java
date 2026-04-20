package net.fuglong.bubblecolumntweaks;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BubbleColumnTweaksMod implements ModInitializer {
    public static final String MOD_ID = "bubblecolumntweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static BubbleColumnConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = BubbleColumnConfig.load();
        LOGGER.info("Bubble Column Tweaks mod initialized!");
        LOGGER.info("Config - Magma: cap={}, accel={}", CONFIG.magmaSpeedCap, CONFIG.magmaAcceleration);
        LOGGER.info("Config - Soul Sand: cap={}, accel={}", CONFIG.soulSandSpeedCap, CONFIG.soulSandAcceleration);
    }
}
