package com.cjburkey.mfrbc.block;

import com.cjburkey.mfrbc.MFRBC;
import com.cjburkey.mfrbc.gui.GuiHandler;
import com.cjburkey.mfrbc.tile.TileEntityUpgrader;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUpgrader extends Block implements ITileEntityProvider {
	
	public BlockUpgrader() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
		this.setSoundType(SoundType.METAL);
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityUpgrader te = (TileEntityUpgrader) world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);
		super.breakBlock(world, pos, blockstate);
	}
	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
		if(!world.isRemote) {
			TileEntityUpgrader te = (TileEntityUpgrader) world.getTileEntity(pos);
			if(stack.hasDisplayName()) {
				te.setCustomName(stack.getDisplayName());
			}
		}
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer pl, EnumHand h, ItemStack stack, EnumFacing side, float x, float y, float z) {
		if(!world.isRemote) {
			pl.openGui(MFRBC.instance, GuiHandler.guiUpgrader, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityUpgrader();
	}
	
}