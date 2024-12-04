package com.github.crimsondawn45.fabricshieldlib.initializers;

import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.dynamic.Codecs;

public class FabricShieldDataComponents {
	public static final ComponentType<Integer> COOLDOWN_TICKS = register("shield_cooldown_ticks",
			builder -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));
	public static final ComponentType<Unit> SHOW_COOLDOWN_TICKS = register("shield_show_cooldown_ticks",
			builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static final ComponentType<Unit> SUPPORT_BANNER = register("shield_support_banner",
			builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));

	public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				Identifier.of(FabricShieldLib.MOD_ID, id),
				((ComponentType.Builder<T>) builderOperator.apply(ComponentType.builder())).build()
			);
	}
}
