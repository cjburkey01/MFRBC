package com.cjburkey.mfrbc.block;

import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuarry extends Block implements ITileEntityProvider {
	
	public BlockQuarry() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityQuarry te = (TileEntityQuarry) world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);
		super.breakBlock(world, pos, blockstate);
	}
	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
		TileEntityQuarry te = (TileEntityQuarry) world.getTileEntity(pos);
		if(stack.hasDisplayName()) {
			te.setCustomName(stack.getDisplayName());
		}
	}

	public TileEntity createNewTileEntity(World w, int m) {
		return new TileEntityQuarry();
	}
	
}