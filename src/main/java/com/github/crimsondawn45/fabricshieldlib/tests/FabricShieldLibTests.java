package com.github.crimsondawn45.fabricshieldlib.tests;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldUtils;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

/**
 * These are test codes used internally by FabricShieldLib developers.
 * <p>
 * Modders are not supposed to reference this class!
 */
public class FabricShieldLibTests {
	/**
	 * Test shield item that does not support banners.
	 */
	protected final static FabricShieldItem FABRIC_SHIELD = registerItem("fabric_shield",
		(props) -> new FabricShieldItem(props.maxDamage(200), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));

	/**
	 * Test shield item that supports banners.
	 */
	protected final static FabricShieldItem FABRIC_BANNER_SHIELD = registerItem("fabric_banner_shield",
		(props) -> new FabricShieldItem(props
				.maxDamage(FabricShieldUtils.VANILLA_SHIELD_DURABILITY)
			, 85, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));

	/**
	 * Test shield item using the {@link BlocksAttacksComponent}
	 */
	protected final static FabricShieldItem FABRIC_COMPONENT_SHIELD = registerItem("fabric_component_shield",
		(props) -> new FabricShieldItem(props.maxDamage(100)
			.component(DataComponentTypes.BLOCKS_ATTACKS,
				new BlocksAttacksComponent(0.25F, 150F / 100F,
					List.of(new BlocksAttacksComponent.DamageReduction(180.0F, Optional.empty(), 0.0F, 0.5F)),
					new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F),
					Optional.of(DamageTypeTags.BYPASSES_SHIELD),
					Optional.of(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
					Optional.of(SoundEvents.ITEM_TRIDENT_THROW)
				)
			), 15, Items.DIAMOND));

	public static void runTests() {
		// Add test items to the combat tab
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.addAfter(Items.SHIELD, FABRIC_BANNER_SHIELD);
			entries.addAfter(FABRIC_BANNER_SHIELD, FABRIC_SHIELD);
			entries.addAfter(FABRIC_SHIELD, FABRIC_COMPONENT_SHIELD);
		});

		// Test events
		ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
			System.out.println(defender + "'s " + shield.getName().getString() + " has been disabled!");
			return ActionResult.PASS;
		});

		ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {
			System.out.println(defender + " blocked " + amount + " from " + source + " with " + shield.getName().getString());
			return ActionResult.PASS;
		});

		// Test event: makes any shield with new enchantment reflect a 1/3rd of damage
		// back to attacker
//        ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {
//
//            RegistryKey<Enchantment> key = FabricShieldLibDataGenerator.EnchantmentGenerator.REFLECTION;
//            RegistryEntry<Enchantment> entry = defender.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getEntry(key.getValue()).get();
//            int reflectNumber = EnchantmentHelper.getLevel(entry, shield);
//
//            if(reflectNumber > 0) {
//                Entity attacker = source.getAttacker();
//
//                if(attacker.equals(null)) {
//                    return ActionResult.CONSUME;
//                }
//                if(defender.blockedByShield(source)){
//                    World world = attacker.getWorld();
//                    if(defender instanceof PlayerEntity) {  //Defender should always be a player, but check anyway
//                        attacker.sidedDamage(world.getDamageSources().playerAttack((PlayerEntity) defender), Math.round(amount * 0.33F));
//                    } else {
//                        attacker.sidedDamage(world.getDamageSources().mobAttack(defender), Math.round(amount * 0.33F));
//                    }
//                }
//            }
//
//            return ActionResult.PASS;
//        });

		// Test Event: if your shield gets disabled, give player speed
//        ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
//            defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
//            return ActionResult.PASS;
//        });
	}

	private static <T extends Item> T registerItem(String name, Function<Item.Settings, T> constructor) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricShieldLib.MOD_ID, name));
		Item.Settings settings = new Item.Settings();
		settings = settings.registryKey(key);
		T item = constructor.apply(settings);
		return Registry.register(Registries.ITEM, key, item);
	}
}
