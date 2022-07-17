package com.tristankechlo.toolleveling.config;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tristankechlo.toolleveling.ToolLeveling;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public final class ItemValues {

	public static Map<Item, Long> itemValues;
	private static Map<String, Long> rawItemValues;
	private static final Type type = new TypeToken<Map<String, Long>>() {}.getType();
	private static final Gson GSON = new Gson();

	private ItemValues() {}

	public static void setToDefaultValues() {
		rawItemValues = new HashMap<>();

		// Ores
		addItem(Items.COAL, 8);
		addItem(Items.COAL_ORE, 30);
		addItem(Items.DEEPSLATE_COAL_ORE, 30);
		addItem(Items.COAL_BLOCK, 73);

		addItem(Items.COPPER_ORE, 10);
		addItem(Items.DEEPSLATE_COPPER_ORE, 10);
		addItem(Items.RAW_COPPER, 11);
		addItem(Items.COPPER_INGOT, 14);
		addItem(Items.COPPER_BLOCK, 126);
		addItem(Items.RAW_COPPER_BLOCK, 99);

		addItem(Items.RAW_IRON, 13);
		addItem(Items.IRON_INGOT, 15);
		addItem(Items.IRON_ORE, 12);
		addItem(Items.DEEPSLATE_IRON_ORE, 12);
		addItem(Items.RAW_IRON_BLOCK, 117);
		addItem(Items.IRON_BLOCK, 135);

		addItem(Items.GOLD_INGOT, 40);
		addItem(Items.RAW_GOLD, 35);
		addItem(Items.GOLD_ORE, 30);
		addItem(Items.DEEPSLATE_GOLD_ORE, 30);
		addItem(Items.RAW_GOLD_BLOCK, 315);
		addItem(Items.GOLD_BLOCK, 360);

		addItem(Items.DIAMOND, 160);
		addItem(Items.DIAMOND_ORE, 160);
		addItem(Items.DEEPSLATE_DIAMOND_ORE, 160);
		addItem(Items.DIAMOND_BLOCK, 1450);
		addItem(Items.NETHERITE_INGOT, 200);
		addItem(Items.NETHERITE_SCRAP, 50);
		addItem(Items.ANCIENT_DEBRIS, 50);
		addItem(Items.NETHERITE_BLOCK, 1800);
		addItem(Items.LAPIS_LAZULI, 8);
		addItem(Items.LAPIS_ORE, 120);
		addItem(Items.DEEPSLATE_LAPIS_ORE, 120);
		addItem(Items.LAPIS_BLOCK, 70);
		addItem(Items.EMERALD, 100);
		addItem(Items.EMERALD_ORE, 800);
		addItem(Items.DEEPSLATE_EMERALD_ORE, 800);
		addItem(Items.EMERALD_BLOCK, 900);
		addItem(Items.QUARTZ, 10);
		addItem(Items.NETHER_QUARTZ_ORE, 40);
		addItem(Items.QUARTZ_BLOCK, 40);
		addItem(Items.REDSTONE, 4);
		addItem(Items.REDSTONE_ORE, 60);
		addItem(Items.DEEPSLATE_REDSTONE_ORE, 60);
		addItem(Items.REDSTONE_BLOCK, 36);
		addItem(Items.GLOWSTONE_DUST, 4);
		addItem(Items.GLOWSTONE, 15);

		// other
		addItem(Items.AMETHYST_BLOCK, 11);
		addItem(Items.AMETHYST_SHARD, 17);

		// Food
		addItem(Items.GOLDEN_APPLE, 400);
		addItem(Items.GOLDEN_CARROT, 100);
		addItem(Items.GLISTERING_MELON_SLICE, 100);
		addItem(Items.ENCHANTED_GOLDEN_APPLE, 2500);

		// Drops
		addItem(Items.SLIME_BALL, 25);
		addItem(Items.SLIME_BLOCK, 225);
		addItem(Items.ENDER_PEARL, 20);
		addItem(Items.BLAZE_ROD, 30);
		addItem(Items.ENDER_EYE, 50);
		addItem(Items.BLAZE_POWDER, 15);
		addItem(Items.MAGMA_CREAM, 50);
		addItem(Items.GHAST_TEAR, 200);
		addItem(Items.NETHER_STAR, 2500);
		addItem(Items.SHULKER_SHELL, 200);
		addItem(Items.END_CRYSTAL, 300);
		addItem(Items.EXPERIENCE_BOTTLE, 100);
		addItem(Items.DRAGON_EGG, 2000);
		addItem(Items.DRAGON_HEAD, 2000);

		// Decorative
		addItem(Items.ENDER_CHEST, 140);
		addItem(Items.BEACON, 2500);
		createItemValues();
	}

	public static JsonObject serialize(JsonObject json) {
		Map<String, Long> tempValues = itemValues.entrySet().stream().collect(
				Collectors.toMap((e) -> ForgeRegistries.ITEMS.getKey(e.getKey()).toString(), (e) -> e.getValue()));
		return GSON.toJsonTree(tempValues, type).getAsJsonObject();
	}

	public static void deserialize(JsonObject json) {
		rawItemValues = GSON.fromJson(json, type);
		createItemValues();
	}

	private static void createItemValues() {
		itemValues = new HashMap<>();
		for (Map.Entry<String, Long> element : rawItemValues.entrySet()) {
			ResourceLocation loc = new ResourceLocation(element.getKey());
			long worth = element.getValue();
			if (worth < 0) {
				continue;
			}
			if (ForgeRegistries.ITEMS.containsKey(loc)) {
				Item item = ForgeRegistries.ITEMS.getValue(loc);
				itemValues.put(item, worth);
			} else {
				ToolLeveling.LOGGER.warn("Ignoring invalid item with id: " + element.getKey());
			}
		}
	}

	private static void addItem(Item item, long worth) {
		ResourceLocation r = ForgeRegistries.ITEMS.getKey(item);
		rawItemValues.put(r.toString(), worth);
	}

}
