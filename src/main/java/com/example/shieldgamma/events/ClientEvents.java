package com.example.shieldgamma.events;

import com.example.shieldgamma.ShieldGammaMod;
import com.example.shieldgamma.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = ShieldGammaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        public static final KeyMapping GAMMA_TOGGLE = new KeyMapping(
                "key.shieldgamma.toggle",
                GLFW.GLFW_KEY_V,
                "key.categories.shieldgamma"
        );

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(GAMMA_TOGGLE);
        }

        @SubscribeEvent
        public static void onItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    if (mc.player.getCooldowns().isOnCooldown(Items.SHIELD)) {
                        return parseHex(Config.colorCooldownHex.get(), 0xFF0000);
                    } else {
                        return parseHex(Config.colorReadyHex.get(), 0x00FF00);
                    }
                }
                return 0xFFFFFF; 
            }, Items.SHIELD);
        }
    }

    @Mod.EventBusSubscriber(modid = ShieldGammaMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        private static boolean gammaOn = false;
        private static double oldGamma = 1.0; 

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            
            while (ClientModEvents.GAMMA_TOGGLE.consumeClick()) {
                gammaOn = !gammaOn;
                
                if (gammaOn) {
                    oldGamma = mc.options.gamma().get();
                    mc.options.gamma().set(100.0);
                    mc.gui.setOverlayMessage(Component.literal("Gamma ON").withStyle(ChatFormatting.GREEN), false);
                } else {
                    mc.options.gamma().set(oldGamma);
                    mc.gui.setOverlayMessage(Component.literal("Gamma OFF").withStyle(ChatFormatting.RED), false);
                }
            }
        }
    }

    private static int parseHex(String hex, int fallback) {
        try {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}