package io.thijzert;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import org.lwjgl.glfw.GLFW;

public class ClearDespawnReworkedClient implements ClientModInitializer {
	private static KeyBinding configKeyBinding;
	public static ModConfig config;
	static {
		AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityType.ITEM, new FlashingItemEntityRenderer.Factory());

		configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.clear-despawn-reworked",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_UNKNOWN,
				"category.clear-despawn-reworked.keybindings"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (configKeyBinding.wasPressed()) {
				MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(ModConfig.class, MinecraftClient.getInstance().currentScreen).get());
			}
		});
	}
}