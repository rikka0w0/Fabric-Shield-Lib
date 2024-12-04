package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldDataComponents;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
}
