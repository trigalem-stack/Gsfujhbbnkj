package com.example.shieldgamma;

import com.example.shieldgamma.config.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ShieldGammaMod.MODID)
public class ShieldGammaMod {
    public static final String MODID = "shieldgamma";

    public ShieldGammaMod() {
        // Register the client-side configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
    }
}