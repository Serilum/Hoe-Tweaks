package com.natamus.hoetweaks.util;

import com.natamus.collective.functions.BlockFunctions;
import com.natamus.collective.functions.CompareBlockFunctions;
import com.natamus.hoetweaks.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;

import java.util.Iterator;

public class Util {
	public static int getHoeRange(Level level, ItemStack hoeStack) {
		ResourceLocation resourceLocation = level.registryAccess().lookupOrThrow(Registries.ITEM).getKey(hoeStack.getItem());
		if (resourceLocation == null) {
			return ConfigHandler.woodenTierHoeRange;
		}

		String itemPath = resourceLocation.getPath();

		int range = ConfigHandler.woodenTierHoeRange;
		if (itemPath.startsWith("stone")) {
			range = ConfigHandler.stoneTierHoeRange;
		}
		else if (itemPath.startsWith("gold")) {
			range = ConfigHandler.goldTierHoeRange;
		}
		else if (itemPath.startsWith("iron") || itemPath.startsWith("steel")) {
			range = ConfigHandler.ironTierHoeRange;
		}
		else if (itemPath.startsWith("diamond")) {
			range = ConfigHandler.diamondTierHoeRange;
		}
		else if (itemPath.startsWith("netherite")) {
			range = ConfigHandler.netheriteTierHoeRange;
		}
		
		return range;
	}
	
	public static int processSoilGetDamage(Level world, BlockPos pos, int range, Block blocktoset, boolean checkforfarmland) {
		int damage = 0;
		
		Iterator<BlockPos> blockstotill = BlockPos.betweenClosedStream(pos.getX()-range, pos.getY(), pos.getZ()-range, pos.getX()+range, pos.getY(), pos.getZ()+range).iterator();
		while(blockstotill.hasNext()) {
			BlockPos apos = blockstotill.next();
			Block ablock = world.getBlockState(apos).getBlock();
			if (!checkforfarmland && !CompareBlockFunctions.isDirtBlock(ablock)) {
				continue;
			}
			else if (checkforfarmland && !ablock.equals(Blocks.FARMLAND)) {
				continue;
			}
			
			BlockPos posabove = apos.above();
			Block blockabove = world.getBlockState(posabove).getBlock();
			if (blockabove instanceof BushBlock) {
				BlockFunctions.dropBlock(world, posabove);
			}
			else if (!blockabove.equals(Blocks.AIR)) {
				continue;
			}
			
			world.setBlock(apos, blocktoset.defaultBlockState(), 3);
			damage += 1;
		}
		
		return damage;
	}
}
