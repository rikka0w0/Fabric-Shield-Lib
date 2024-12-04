package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldDataComponents;
import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldSetModelCallback;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class FabricShieldUtils {
	public final static int VANILLA_COOLDOWN_TICKS = 100;

	@SuppressWarnings("deprecation")
	public static boolean isShieldItem(Item item) {
		return item.getRegistryEntry().isIn(ConventionalItemTags.SHIELD_TOOLS);
	}

	public static int getCooldownTicks(ItemStack itemStack) {
		return itemStack.getOrDefault(FabricShieldDataComponents.COOLDOWN_TICKS, VANILLA_COOLDOWN_TICKS);
	}

	public static void disableShield(PlayerEntity player, ItemStack itemStack) {
		int cooldownTicks = getCooldownTicks(itemStack);
		player.getItemCooldownManager().set(itemStack, cooldownTicks);
		player.clearActiveItem();
		player.getWorld().sendEntityStatus(player, EntityStatuses.BREAK_SHIELD);
	}

	private static class ShieldEntityModelStorage {
		public ShieldEntityModel value = null;
	}

	public static void registerShieldWithPatternOnClient(Item item, EntityModelLayer modelLayer,
			SpriteIdentifier sprite, SpriteIdentifier spriteNoPattern) {
		ShieldEntityModelStorage shieldEntityModel = new ShieldEntityModelStorage();

		// Registers sprite directories and model layer
		EntityModelLayerRegistry.registerModelLayer(modelLayer, ShieldEntityModel::getTexturedModelData);

		// Set model
		ShieldSetModelCallback.EVENT.register((loader) -> {
			shieldEntityModel.value = new ShieldEntityModel(loader.getModelPart(modelLayer));
			return ActionResult.PASS;
		});

		// Register renderer
		BuiltinItemRendererRegistry.INSTANCE.register(item,
			(stack, mode, matrices, vertexConsumers, light, overlay) ->
				FabricShieldLibClient.renderBanner(stack, matrices, vertexConsumers, light,
						overlay, shieldEntityModel.value, sprite, spriteNoPattern));
	}
}
