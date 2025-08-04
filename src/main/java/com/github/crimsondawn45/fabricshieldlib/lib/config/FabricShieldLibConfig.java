package com.github.crimsondawn45.fabricshieldlib.lib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class FabricShieldLibConfig extends MidnightConfig {

	public static final String MAIN = "general";
	public static final String TOOLTIP = "tooltip";


	@Entry(category = MAIN)
	public static boolean enable_tooltips = true;

	@Comment(category = MAIN)
	public static Comment enchantability_convention;

	@Entry(category = MAIN)
	public static int vanilla_shield_enchantability = 14;

	@Entry(category = MAIN)
	public static boolean universal_disable = false;

	@Comment(category = MAIN)
	public static Comment universal_disable_description_1;

	@Comment(category = MAIN)
	public static Comment universal_disable_description_2;

	@Entry(category = TOOLTIP)
	public static boolean complex_tooltip = false;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean block_delay_seconds = false;

	@Comment(category = TOOLTIP) public static Comment spacer1;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean cooldown_ticks = true;

	@Comment(category = TOOLTIP) public static Comment spacer2;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean damage_reduction_type = false;

	@Comment(category = TOOLTIP) public static Comment spacer3;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean damage_reduction_base = false;

	@Comment(category = TOOLTIP) public static Comment spacer4;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean damage_reduction_percent = false;

	@Comment(category = TOOLTIP) public static Comment spacer5;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean damage_reduction_blocking_angle = false;

	@Comment(category = TOOLTIP) public static Comment spacer6;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean item_damage_threshold = false;

	@Comment(category = TOOLTIP) public static Comment spacer7;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean item_damage_base = false;

	@Comment(category = TOOLTIP) public static Comment spacer8;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean item_damage_percent = false;

	@Comment(category = TOOLTIP) public static Comment spacer9;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean block_sound = false;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean disabled_sound = false;

	@Condition(requiredOption = "fabricshieldlib:complex_tooltip", requiredValue = "true")
	@Entry(category = TOOLTIP)
	public static boolean bypassed_by = false;



}
