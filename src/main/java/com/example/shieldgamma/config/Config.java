package com.example.shieldgamma.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> colorReadyHex;
    public static final ForgeConfigSpec.ConfigValue<String> colorCooldownHex;

    static {
        BUILDER.push("shield_colors");

        colorReadyHex = BUILDER
                .comment("Hex color applied to the shield when ready (Default: #00FF00)")
                .define("colorReadyHex", "#00FF00");

        colorCooldownHex = BUILDER
                .comment("Hex color applied to the shield when on axe cooldown/broken (Default: #FF0000)")
                .define("colorCooldownHex", "#FF0000");

        BUILDER.pop();
        CLIENT_SPEC = BUILDER.build();
    }
}