package com.cjburkey.mfrbc.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMarker extends Block {
	
	public BlockMarker() {
		super(Material.WOOD);
		
		this.setHardness(0.0f);
		this.setSoundType(SoundType.WOOD);
	}
	
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return null;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return  new AxisAlignedBB(7f/16f, 0.0D, 7f/16f, 9f/16f, 1.0D, 9f/16f);
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}