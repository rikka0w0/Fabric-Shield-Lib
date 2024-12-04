package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldUtils;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Mixin that allows custom shields to be damaged, and to be disabled with axes.
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	/**
	 * <blockquote>
	 *
	 * <pre>
	 *     protected void damageShield(float amount) {
	 * -       if (this.activeItemStack.isOf(Items.SHIELD))
	 * +       if (damageFabricShield(this.activeItemStack.isOf(Items.SHIELD), damageAmount))
	 * </pre>
	 *
	 * </blockquote>
	 */
	@ModifyExpressionValue(
			allow = 1,
			require = 1,
			method = "damageShield(F)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
		)
	private boolean damageFabricShield(boolean isVanillaShield) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack activeItemStack = player.getActiveItem();
		Item activeItem = activeItemStack.getItem();
		return isVanillaShield || FabricShieldUtils.isShieldItem(activeItem);
	}

    /**
     * @param callbackInfo callback information
     */
    @Inject(at = @At(value = "HEAD"), method = "disableShield(Lnet/minecraft/item/ItemStack;)V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void disableShieldHead(ItemStack itemStack, CallbackInfo callbackInfo) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		Item item = itemStack.getItem();

		ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), itemStack);

		if (!FabricShieldUtils.isShieldItem(item))
			return;

		if (FabricShieldLibConfig.universal_disable) {
			disableAllShields(player);
			callbackInfo.cancel();
			return;
		}

		FabricShieldUtils.disableShield(player, itemStack);
		callbackInfo.cancel();
	}

	@Unique
	private void disableAllShields(PlayerEntity player) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack itemStack = player.getInventory().getStack(i);
			if (itemStack.isIn(ConventionalItemTags.SHIELD_TOOLS)) {
				FabricShieldUtils.disableShield(player, itemStack);
			}
		}
	}
}
