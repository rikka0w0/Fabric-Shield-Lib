package com.github.crimsondawn45.fabricshieldlib.tests;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldClientUtils;

import net.minecraft.util.Identifier;

/**
 * These are test codes used internally by FabricShieldLib developers.
 * <p>
 * Modders are not supposed to reference this class!
 */
public class FabricShieldLibClientTests {
	// private static ShieldEntityModel modelFabricShield;

	public static void runTests() {
		/**
		 * To render a banner shield properly, the user needs to call this.
		 * In the test code, "fabric_banner_shield" and "fabric_component_shield" both use
		 * model type "fabric_banner_shield".
		 */
		FabricShieldClientUtils.registerBannerShieldRenderer(
			FabricShieldLibTests.FABRIC_BANNER_SHIELD,
			Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base"),
			Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern")
		);

		/*
		// Set model
		ShieldSetModelCallback.EVENT.register((loader) -> {
			modelFabricShield = new ShieldEntityModel(
				loader.getModelPart(fabric_banner_shield_model_layer));
			return ActionResult.PASS;
		});
		*/
	}
}
