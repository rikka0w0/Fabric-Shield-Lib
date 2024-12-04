package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FabricShieldLibDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(EnchantmentGenerator::new);
		pack.addProvider(ItemTagGenerator::new);
	}

	public static class ItemTagGenerator extends FabricTagProvider<Item> {
		public ItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
			super(output, RegistryKeys.ITEM, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup lookup) {
			this.getOrCreateTagBuilder(ConventionalItemTags.SHIELD_TOOLS)
				.add(FabricShieldLib.fabric_banner_shield)
				.add(FabricShieldLib.fabric_shield);
		}
	}

    public static class EnchantmentGenerator extends FabricDynamicRegistryProvider {
        public static final RegistryKey<Enchantment> REFLECTION = EnchantmentGenerator.of("reflect");

        public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
            System.out.println("REGISTERING ENCHANTS");
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
            register(entries, REFLECTION, Enchantment.builder(Enchantment.definition(registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ConventionalItemTags.SHIELD_TOOLS),
                                            // this is the "weight" or probability of our enchantment showing up in the table
                                            10,
                                            // the maximum level of the enchantment
                                            1,
                                            // base cost for level 1 of the enchantment, and min levels required for something higher
                                            Enchantment.constantCost( 10),
                                            // same fields as above but for max cost
                                            Enchantment.constantCost(15),
                                            // anvil cost
                                            5,
                                            // valid slots
                                            AttributeModifierSlot.HAND)));
        }

        private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
            entries.add(key, builder.build(key.getValue()), resourceConditions);
        }

        private static RegistryKey<Enchantment> of(String path) {
            Identifier id = Identifier.of(FabricShieldLib.MOD_ID, path);
            return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
        }

        @Override
        public String getName() {
            return "FabricShieldLibEnchantmentGenerator";
        }
    }
}