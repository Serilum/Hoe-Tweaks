package com.natamus.hoetweaks.forge.events;

import com.natamus.hoetweaks.events.HoeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeHoeEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeHoeEvent.class);
	}

	@SubscribeEvent
	public static boolean onHoeRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		if (!HoeEvent.onHoeRightClickBlock(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), null)) {
			return true;
		}
		return false;
	}
	
	@SubscribeEvent
	public static void onHarvestBreakSpeed(PlayerEvent.BreakSpeed e) {
		Player player = e.getEntity();
		float originalSpeed = e.getOriginalSpeed();
		float newSpeed = HoeEvent.onHarvestBreakSpeed(player.level(), player, originalSpeed, e.getState());

		if (originalSpeed != newSpeed) {
			e.setNewSpeed(newSpeed);
		}
	}
}
