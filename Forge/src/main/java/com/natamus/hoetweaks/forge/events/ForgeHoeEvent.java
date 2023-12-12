package com.natamus.hoetweaks.forge.events;

import com.natamus.hoetweaks.events.HoeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeHoeEvent {
	@SubscribeEvent
	public void onHoeRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		if (!HoeEvent.onHoeRightClickBlock(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), null)) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onHarvestBreakSpeed(PlayerEvent.BreakSpeed e) {
		Player player = e.getEntity();
		float originalSpeed = e.getOriginalSpeed();
		float newSpeed = HoeEvent.onHarvestBreakSpeed(player.level, player, originalSpeed, e.getState());

		if (originalSpeed != newSpeed) {
			e.setNewSpeed(newSpeed);
		}
	}
}
