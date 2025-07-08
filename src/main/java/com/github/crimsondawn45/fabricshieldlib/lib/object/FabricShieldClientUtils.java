package com.github.crimsondawn45.fabricshieldlib.lib.object;

import java.util.Optional;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

/**
 * Everything in this class will remain binary-compatible to the maximum extent.
 */
public class FabricShieldClientUtils {
	public static void registerBannerShieldRenderer(
		Identifier modelType,
		Identifier baseTexture, Identifier baseTextureNoPat,
		EntityModelLayer entityModelLayer
	) {
		MapCodec<FabricShieldModelRenderer.UnbakedInstance.Unbaked> codec =
			new FabricShieldModelRenderer.UnbakedInstance(
				baseTexture, baseTextureNoPat, entityModelLayer
			).codec;

		SpecialModelTypes.ID_MAPPER.put(modelType, codec);

		EntityModelLayerRegistry.registerModelLayer(entityModelLayer,
			ShieldEntityModel::getTexturedModelData);
	}

	public static void registerBannerShieldRenderer(
		Identifier modelType, Identifier baseTexture, Identifier baseTextureNoPat
	) {
		registerBannerShieldRenderer(modelType, baseTexture, baseTextureNoPat,
			new EntityModelLayer(modelType, "main"));
	}

	public static void registerBannerShieldRenderer(
		Item shieldItem, Identifier baseTexture, Identifier baseTextureNoPat
	) {
		Optional<RegistryKey<Item>> regKey = Registries.ITEM.getKey(shieldItem);
		Identifier itemKey = regKey.get().getValue();
		registerBannerShieldRenderer(itemKey, baseTexture, baseTextureNoPat,
			new EntityModelLayer(itemKey, "main"));
	}
}
