package com.cjburkey.mfrbc.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;

public class FluidUtilz {
	
	public static final Fluid getFluid(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		
		FluidStack s = null;
		
		if(b instanceof IFluidBlock) {
			s = new FluidBlockWrapper((IFluidBlock) b, world, pos).getTankProperties()[0].getContents();
		} else if(b instanceof BlockLiquid) {
			s = new BlockLiquidWrapper((BlockLiquid) b, world, pos).getTankProperties()[0].getContents();
		}
		
		Fluid out = (s != null) ? s.getFluid() : null;
		return out;
	}
	
	public static final boolean isFluid(World world, BlockPos pos) {
		return getFluid(world, pos) != null;
	}
	
	public static final int getBucket() { return FluidContainerRegistry.BUCKET_VOLUME; }
	
	public static final int bucketToMb(int buckets) {
		return getBucket() * buckets;
	}
	
}