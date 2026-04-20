package net.fuglong.bubblecolumntweaks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BubbleColumnConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("bubblecolumntweaks.json");

    // Magma block (downward) settings
    public double magmaSpeedCap = -0.8;
    public double magmaAcceleration = -0.08;

    // Soul sand (upward) settings
    public double soulSandSpeedCap = 0.8;
    public double soulSandAcceleration = 0.08;

    // Surface effects (when entity reaches the top/surface)
    public double magmaSurfaceSpeedCap = -1.8;
    public double magmaSurfaceAcceleration = -0.1;

    public double soulSandSurfaceSpeedCap = 1.8;
    public double soulSandSurfaceAcceleration = 0.1;

    public static BubbleColumnConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                BubbleColumnConfig config = GSON.fromJson(json, BubbleColumnConfig.class);
                BubbleColumnTweaksMod.LOGGER.info("Loaded config from {}", CONFIG_PATH);
                return config;
            } catch (IOException e) {
                BubbleColumnTweaksMod.LOGGER.error("Failed to load config, using defaults", e);
                return createDefault();
            }
        } else {
            return createDefault();
        }
    }

    private static BubbleColumnConfig createDefault() {
        BubbleColumnConfig config = new BubbleColumnConfig();
        config.save();
        BubbleColumnTweaksMod.LOGGER.info("Created default config at {}", CONFIG_PATH);
        return config;
    }

    public void save() {
        try {
            String json = GSON.toJson(this);
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            BubbleColumnTweaksMod.LOGGER.error("Failed to save config", e);
        }
    }
}
