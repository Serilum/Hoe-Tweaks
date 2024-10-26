package com.natamus.hoetweaks.neoforge.events;

import com.natamus.hoetweaks.events.HoeEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeHoeEvent {
	@SubscribeEvent
	public static void onHoeRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		if (!HoeEvent.onHoeRightClickBlock(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), null)) {
			e.setCanceled(true);
		}
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
