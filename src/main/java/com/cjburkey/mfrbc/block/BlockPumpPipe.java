package com.cjburkey.mfrbc.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.cjburkey.mfrbc.tile.TileEntityPumpPipe;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPumpPipe extends Block implements ITileEntityProvider {
	
	public BlockPumpPipe() {
		super(Material.GROUND);
		
		this.setBlockUnbreakable();
		this.setSoundType(SoundType.METAL);
	}
	
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return  new AxisAlignedBB(5f/16f, 0f/16f, 5f/16f, 11f/16f, 16f/16f, 11f/16f);
	}
	
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPumpPipe();
	}
	
}