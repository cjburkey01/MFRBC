package com.cjburkey.mfrbc.block;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.tile.TileEntityMarker;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMarker extends Block implements ITileEntityProvider {
	
	public BlockMarker() {
		super(Material.WOOD);
		
		this.setHardness(0.0f);
		this.setSoundType(SoundType.WOOD);
	}
	
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		return canPlaceBlockAt(world, pos);
	}
	
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileEntityMarker();
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer pl, EnumHand h, ItemStack stack, EnumFacing side, float x, float y, float z) {
		if(!world.isRemote) {
			TileEntityMarker te = (TileEntityMarker) world.getTileEntity(pos);
			te.search();
			BlockPos[] markers = te.getMarkers();
			if(markers[0] == null || markers[1] == null) {
				Util.chat(pl, "[Markers] Invalid Shape.");
			} else {
				Util.chat(pl, "[Markers] Marker 1: " + markers[0]);
				Util.chat(pl, "[Markers] Marker 2: " + markers[1]);
			}
		}
		return true;
	}
	
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return (super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos));
	}
	
	private boolean canBlockStay(World w, BlockPos p) {
		IBlockState down = w.getBlockState(p.down());
		IBlockState up = w.getBlockState(p.up());
		return (!w.isAirBlock(p.down()) || !w.isAirBlock(p.up())) && (down.getBlock().isFullCube(down) || up.getBlock().isFullCube(up));
	}
	
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		super.neighborChanged(state, worldIn, pos, blockIn);
		this.checkForDrop(worldIn, pos, state);
	}
	
	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if(!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		}
		return true;
	}
	
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) {
		return NULL_AABB;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return  new AxisAlignedBB(7f/16f, 0f/16f, 7f/16f, 9f/16f, 16f/16f, 9f/16f);
	}
	
	public boolean isFullBlock(IBlockState state) {
		return true;
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
}