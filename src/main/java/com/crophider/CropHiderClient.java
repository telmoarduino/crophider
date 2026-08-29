package com.crophider;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CropHiderClient implements ClientModInitializer {
    public static boolean hideCrops = false;
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.crophider.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.crophider.title"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                hideCrops = !hideCrops;
                
                if (client.player != null) {
                    client.player.sendMessage(
                        Text.literal("§e[CropHider] §fCultivos: " + (hideCrops ? "§cOCULTOS" : "§aVISIBLES")),
                        true
                    );
                }
                
                if (client.worldRenderer != null) {
                    client.worldRenderer.reload();
                }
            }
        });
    }
}
