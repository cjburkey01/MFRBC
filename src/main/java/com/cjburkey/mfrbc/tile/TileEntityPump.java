package com.cjburkey.mfrbc.tile;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.block.BlockPumpPipe;
import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.fluid.FluidTank;
import com.cjburkey.mfrbc.fluid.FluidUtilz;
import com.cjburkey.mfrbc.item._Items;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

@SuppressWarnings("deprecation")
public class TileEntityPump extends TileEntity implements ITickable, IEnergyReceiver, IFluidHandler {
	
	private FluidTank tank;
	
	private int capacity;
	private int energy;
	private int maxReceive;
	private int startClock;
	private int clock;
	private int blocksPerRun = 1;
	
	public TileEntityPump() {
		this.tank = new FluidTank(FluidUtilz.bucketToMb(_Config.pumpBuckets));
		this.startClock = _Config.pumpSpeed;
		this.clock = this.startClock;
		this.energy = 0;
		this.capacity = _Config.pumpMaxEnergy;
		this.maxReceive = _Config.pumpMaxReceive;
	}
	
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.blockPump.name");
	}
	
	public void update() {
		if(!this.worldObj.isRemote) {
			clock --;
			if(clock <= 0) {
				clock = startClock;
				if(canRun()) {
					this.blocksPerRun = 1 + Util.getUpgradeCount(this.worldObj, this.pos, _Items.itemUpgradeSpeed);
					for(int i = 0; i < this.blocksPerRun; i ++) {
						TileEntityPumpPipe p = pumpPipeBelow(this.worldObj, this.pos);
						if(p != null) p.run();
					}
				}
			}
		}
	}
	
	public boolean canRun() {
		if(this.getEnergyStored() >= _Config.pumpRfPerOp && hasFluidRoom(1) && this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean hasFluidRoom(int buckets) {
		return (this.tank.getFluidAmount() + FluidUtilz.bucketToMb(buckets) <= this.tank.getCapacity());
	}
	
	// -- FLUID -- //
	
	public FluidStack getFluid() {
		return this.tank.getFluid();
	}
	
	public int getTankCap() {
		return this.tank.getCapacity();
	}
	
	public int fill(EnumFacing from, FluidStack r, boolean doFill) {
		return this.tank.fill(r, doFill);
	}

	public FluidStack drain(EnumFacing from, FluidStack r, boolean doDrain) {
		if(r.isFluidEqual(this.tank.getFluid())) {
			return this.tank.drain(r.amount, doDrain);
		}
		return null;
	}

	public FluidStack drain(EnumFacing from, int amt, boolean doDrain) {
		return this.tank.drain(amt, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return false;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return this.tank.getFluid().getFluid().equals(fluid);
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}
	
	// -- ENERGY -- //

	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	public int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate) {
		int energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			this.energy += energyReceived;
		}
		return energyReceived;
	}

	public int getEnergyStored(EnumFacing from) {
		return this.energy;
	}

	public int getMaxEnergyStored(EnumFacing from) {
		return this.capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		if (energy > capacity) {
			energy = capacity;
		}
	}

	public void setEnergyStored(int energy) {
		this.energy = energy;
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public void modifyEnergyStored(int energy) {
		this.energy += energy;
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public int getEnergyStored() {
		return this.energy;
	}

	public int getCapacity() {
		return this.capacity;
	}
	
	// -- TE -- //
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.tank = this.tank.readFromNBT(nbt);
		super.readFromNBT(nbt);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbtt) {
		NBTTagCompound nbt = this.tank.writeToNBT(nbtt);
		return super.writeToNBT(nbt);
	}
	
	public static TileEntityPumpPipe pumpPipeBelow(World w, BlockPos po) {
		TileEntityPumpPipe p = pipeBelow(w, po);
		if(p == null && (FluidUtilz.isFluid(w, po.down()) || w.isAirBlock(po.down()))) w.setBlockState(po.down(), _Blocks.blockPumpPipe.getDefaultState());
		p = pipeBelow(w, po);
		return p;
	}
	
	public static final TileEntityPumpPipe pipeBelow(World world, BlockPos p) {
		BlockPos b = p.down();
		IBlockState state = world.getBlockState(b);
		if(state.getBlock() instanceof BlockPumpPipe) {
			return (TileEntityPumpPipe) world.getTileEntity(b);
		}
		return null;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
}