package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.*;

import java.util.List;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldDataComponents;

/**
 * Pre-made class for quickly making custom shields which support banners.
 */
public class FabricBannerShieldItem extends FabricShieldItem {
    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItems    item(s) for repairing shield.
     */
    public FabricBannerShieldItem(Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
		super(settings.component(FabricShieldDataComponents.SUPPORT_BANNER, Unit.INSTANCE),
				coolDownTicks, enchantability, repairItems);
    }

    /**
     * @param settings      item settings.
     * @param coolDownTicks ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param material      tool material.
     */
	public FabricBannerShieldItem(Settings settings, int coolDownTicks, ToolMaterial material) {
		super(settings.component(FabricShieldDataComponents.SUPPORT_BANNER, Unit.INSTANCE),
				coolDownTicks, material);
    }

    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItemTag  item tag for repairing shield.
     */
	public FabricBannerShieldItem(Settings settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
		super(settings.component(FabricShieldDataComponents.SUPPORT_BANNER, Unit.INSTANCE),
				coolDownTicks, enchantability, repairItemTag);
	}

    @Override
    public Text getName(ItemStack stack) {
        DyeColor dyeColor = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
        if (dyeColor != null) {
            String key = this.getTranslationKey();
            return Text.translatable(key + "." + dyeColor.getName());
        } else {
            return super.getName(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    	BannerItem.appendBannerTooltip(stack, tooltip);
    }
}