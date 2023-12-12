package com.natamus.hoetweaks.events;

import com.natamus.collective.functions.CompareBlockFunctions;
import com.natamus.collective.services.Services;
import com.natamus.hoetweaks.config.ConfigHandler;
import com.natamus.hoetweaks.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class HoeEvent {
	public static boolean onHoeRightClickBlock(Level world, Player player, InteractionHand hand, BlockPos cpos, BlockHitResult hitVec) {
		if (world.isClientSide) {
			return true;
		}
		
		ItemStack stack = player.getItemInHand(hand);
		if (!Services.TOOLFUNCTIONS.isHoe(stack)) {
			return true;
		}

		if (ConfigHandler.onlyUntillWithOtherHandEmpty) {
			if (!player.getMainHandItem().isEmpty() && !player.getOffhandItem().isEmpty()) {
				return true;
			}
		}

		Block block = world.getBlockState(cpos).getBlock();
		if (block.equals(Blocks.AIR)) {
			cpos = cpos.below().immutable();
			block = world.getBlockState(cpos).getBlock();
		}
		
		int damage;
		if (block.equals(Blocks.FARMLAND)) {
			if (!player.isCrouching() && ConfigHandler.mustCrouchToHaveBiggerHoeRange) {
				world.setBlock(cpos, Blocks.DIRT.defaultBlockState(), 3);
				damage = 1;
			}
			else {
				int range = Util.getHoeRange(stack);
				damage = Util.processSoilGetDamage(world, cpos, range, Blocks.DIRT, true);
			}
			
			Vec3 pvec = player.position();
			if (pvec.y % 1 != 0) {
				player.setPos(pvec.x, Math.ceil(pvec.y), pvec.z);
			}
		}
		else if (CompareBlockFunctions.isDirtBlock(block)) {
			if (!player.isCrouching() && ConfigHandler.mustCrouchToHaveBiggerHoeRange) {
				return true;
			}
			
			int range = Util.getHoeRange(stack);
			damage = Util.processSoilGetDamage(world, cpos, range, Blocks.FARMLAND, false);
		}
		else {
			return true;
		}
		
		world.playSound(null, cpos.getX(), cpos.getY(), cpos.getZ(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 0.5F, 1.0F);
		
		player.swing(hand);
		
		if (!player.isCreative()) {
			stack.hurtAndBreak(damage, player, (player1 -> player1.broadcastBreakEvent(hand)));
		}
		
		return false;
	}
	
	public static float onHarvestBreakSpeed(Level world, Player player, float digSpeed, BlockState state) {
		ItemStack handstack = player.getMainHandItem();
		if (!Services.TOOLFUNCTIONS.isHoe(handstack)) {
			return digSpeed;
		}
		
		Block block = state.getBlock();
		if (block instanceof PumpkinBlock || block.equals(Blocks.MELON)) {
			return (float)ConfigHandler.cropBlockBreakSpeedModifier;
		}
		
		return digSpeed;
	}
}
