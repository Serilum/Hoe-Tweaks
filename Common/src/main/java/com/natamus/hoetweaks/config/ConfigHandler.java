package com.natamus.hoetweaks.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.hoetweaks.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean onlyUntillWithOtherHandEmpty = true;
	@Entry(min = 0.0, max = 20.0) public static double cropBlockBreakSpeedModifier = 8.0;
	@Entry public static boolean mustCrouchToHaveBiggerHoeRange = true;
	@Entry(min = 0, max = 32) public static int woodenTierHoeRange = 0;
	@Entry(min = 0, max = 32) public static int stoneTierHoeRange = 1;
	@Entry(min = 0, max = 32) public static int goldTierHoeRange = 2;
	@Entry(min = 0, max = 32) public static int ironTierHoeRange = 2;
	@Entry(min = 0, max = 32) public static int diamondTierHoeRange = 3;
	@Entry(min = 0, max = 32) public static int netheriteTierHoeRange = 4;

	public static void initConfig() {
		configMetaData.put("onlyUntillWithOtherHandEmpty", Arrays.asList(
			"When enabled, only allows the un-till function to work when the other hand is empty. Allows placing seeds with hoe in other hand."
		));
		configMetaData.put("cropBlockBreakSpeedModifier", Arrays.asList(
			"How much quicker a cropblock (pumpkin/melon) is broken than by default."
		));
		configMetaData.put("mustCrouchToHaveBiggerHoeRange", Arrays.asList(
			"Whether the bigger hoe range should only be used if the player is crouching when right-clicking the center block."
		));
		configMetaData.put("woodenTierHoeRange", Arrays.asList(
			"The wooden hoe till range (default while crouching). 0 = 1x1"
		));
		configMetaData.put("stoneTierHoeRange", Arrays.asList(
			"The stone hoe till range (default while crouching). 1 = 3x3"
		));
		configMetaData.put("goldTierHoeRange", Arrays.asList(
			"The gold hoe till range (default while crouching). 2 = 5x5"
		));
		configMetaData.put("ironTierHoeRange", Arrays.asList(
			"The iron hoe till range (default while crouching). 2 = 5x5"
		));
		configMetaData.put("diamondTierHoeRange", Arrays.asList(
			"The diamond hoe till range (default while crouching). 3 = 7x7"
		));
		configMetaData.put("netheriteTierHoeRange", Arrays.asList(
			"The netherite hoe till range (default while crouching). 4 = 9x9"
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}