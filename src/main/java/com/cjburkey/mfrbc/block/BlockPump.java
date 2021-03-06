package com.cjburkey.mfrbc.block;

import java.util.Random;
import javax.annotation.Nullable;
import com.cjburkey.mfrbc.MFRBC;
import com.cjburkey.mfrbc.gui.GuiHandler;
import com.cjburkey.mfrbc.tile.TileEntityPump;
import choonster.testmod3.util.CapabilityUtils;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPump extends BlockDirectional implements ITileEntityProvider {

	public BlockPump() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setTickRandomly(true);
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random r) {
		for(int i = 0; i < r.nextInt(20); i ++) {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5d, pos.getY() + 1d, pos.getZ() + 0.5d, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}
	
	private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
		if(!world.isRemote) {
			IBlockState iblockstate = world.getBlockState(pos.north());
			IBlockState iblockstate1 = world.getBlockState(pos.south());
			IBlockState iblockstate2 = world.getBlockState(pos.west());
			IBlockState iblockstate3 = world.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			if(enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if(enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if(enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if(enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}
			
			world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
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