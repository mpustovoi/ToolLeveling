package com.tristankechlo.toolleveling.client;

import com.tristankechlo.toolleveling.utils.Utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class TooltipEvents {

	@SubscribeEvent
	public void onTooltip(final ItemTooltipEvent event) {
		if (event.getFlags().isAdvanced() && Screen.hasShiftDown()) {
			final Item item = event.getItemStack().getItem();
			final long worth = Utils.getItemWorth(item);
			event.getToolTip().add(new TextComponent(""));
			event.getToolTip().add(
					new TranslatableComponent("tooltip.toolleveling.itemworth", worth).withStyle(ChatFormatting.AQUA));
		}
	}

}
