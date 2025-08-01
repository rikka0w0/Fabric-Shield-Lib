package com.github.crimsondawn45.fabricshieldlib.lib.object;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;

import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

public class BlockAttacksTooltip {
	public static void appendTooltip(BlocksAttacksComponent blocksAttacksComponent,
		Consumer<Text> tooltipAdder, TooltipType type
	) {
		if (!FabricShieldLibConfig.enable_tooltips)
			return;

		float cooldownTicks = 100.0F * blocksAttacksComponent.disableCooldownScale();

		// Add disabled cooldown tooltip
		tooltipAdder.accept(Text.literal(""));
		tooltipAdder.accept(Text.translatable("fabricshieldlib.shield_tooltip.start")
			.append(Text.literal(":"))
			.formatted(Formatting.GRAY)
		);

		/*
		 * All of this is so if there is a .0 instead of there being a need for a
		 * decimal remove the .0
		 */
		String cooldown = String.valueOf((Double) (cooldownTicks / 20.0));
		char[] splitCooldown = cooldown.toCharArray();
		if (splitCooldown.length >= 3) {
			if (splitCooldown[2] == '0') {
				if (!(splitCooldown.length >= 4)) {
					cooldown = String.valueOf(splitCooldown[0]);
				}
			}
		}

		tooltipAdder.accept(Text.literal(" " + cooldown)
			.formatted(Formatting.DARK_GREEN)
			.append(Text.translatable("fabricshieldlib.shield_tooltip.unit"))
			.append(" ")
			.append(Text.translatable("fabricshieldlib.shield_tooltip.end"))
		);
	}

	/**
	 * Remove our tooltips bounded to DataComponentTypes.BLOCKS_ATTACKS
	 * @param tooltipList
	 */
	public static void removeTooltip(List<Text> tooltipList) {
		int state = 0;
		Text previous = null;
		Text emptyNewLine = null;
		loop: for (Iterator<Text> iterator = tooltipList.iterator(); iterator.hasNext();) {
			Text current = iterator.next();

			switch (state) {
			case 0:
				if (current.getContent() instanceof TranslatableTextContent translatable &&
					"fabricshieldlib.shield_tooltip.start".equals(translatable.getKey())) {
					iterator.remove();
					emptyNewLine = previous;
					state = 1;
				}
				break;
			case 1:
				iterator.remove();
				break loop;
			default:
				break;
			}
			previous = current;
		}

		if (emptyNewLine != null) {
			tooltipList.remove(emptyNewLine);
		}
	}
}
