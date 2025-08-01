package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.object.BlockAttacksTooltip;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldModelRenderer;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldTags;
import com.github.crimsondawn45.fabricshieldlib.tests.FabricShieldLibClientTests;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.util.Identifier;

public class FabricShieldLibClient implements ClientModInitializer {
	/**
	 * In item model JSON: ROOT.model.CONDITION.model.type
	 */
	public static final Identifier FABRIC_BANNER_SHIELD_MODEL_TYPE =
		Identifier.of(FabricShieldLib.MOD_ID, "fabric_banner_shield");

	@SuppressWarnings("unchecked")
	@Override
	public void onInitializeClient() {
		/*
		 * Register our SpecialModelRenderer, to be used for all modded shields that
		 * support banner. Of course, the user can implement a custom SpecialModelRenderer.
		 */
		SpecialModelTypes.ID_MAPPER.put(
			FABRIC_BANNER_SHIELD_MODEL_TYPE,
			FabricShieldModelRenderer.Unbaked.CODEC
		);

		/*
		 * Register tooltip callback this is the same as mixing into the end of:
		 * ItemStack.getTooltip()
		 */
		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
			if (stack.get(DataComponentTypes.BLOCKS_ATTACKS) == null)
				return;

			if (!FabricShieldLibConfig.enable_tooltips)
				return;

			if (!stack.isIn(FabricShieldTags.NO_TOOLTIP))
				return;

			BlockAttacksTooltip.removeTooltip(tooltip);
		});

		// Register our own tooltips for DataComponentTypes.BLOCKS_ATTACKS
		ComponentTooltipAppenderRegistry.addAfter(
			DataComponentTypes.DAMAGE,
			(ComponentType<? extends TooltipAppender>) (Object) DataComponentTypes.BLOCKS_ATTACKS
		);

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			// Warn about dev code
			FabricShieldLib.logger.warn(
				"FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Client side banner code ran!");

			FabricShieldLibClientTests.runTests();
		}
	}
}