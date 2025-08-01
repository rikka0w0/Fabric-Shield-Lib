package com.github.crimsondawn45.fabricshieldlib.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.object.BlockAttacksTooltip;

import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

@Mixin(BlocksAttacksComponent.class)
public class BlocksAttacksComponentMixin implements TooltipAppender {
	@Override
	public void appendTooltip(
		TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components
	) {
		BlocksAttacksComponent me = (BlocksAttacksComponent)(Object)this;
		BlockAttacksTooltip.appendTooltip(me, textConsumer, type);
	}
}
