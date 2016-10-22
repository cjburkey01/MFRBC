package com.cjburkey.mfrbc.block;

import javax.annotation.Nullable;
import com.cjburkey.mfrbc.MFRBC;
import com.cjburkey.mfrbc.gui.GuiHandler;
import com.cjburkey.mfrbc.tile.TileEntityPump;
import choonster.testmod3.util.CapabilityUtils;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockPump extends Block implements ITileEntityProvider {

	public BlockPump() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
		this.setSoundType(SoundType.METAL);
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPump();
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer pl, EnumHand h, ItemStack stack, EnumFacing side, float x, float y, float z) {
		if(!world.isRemote) {
			pl.openGui(MFRBC.instance, GuiHandler.guiPump, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Nullable
	private IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos) {
		return CapabilityUtils.getCapability((TileEntityPump) world.getTileEntity(pos), CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
	
}