package com.cjburkey.mfrbc.tile;

import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.fluid.FluidTank;
import com.cjburkey.mfrbc.fluid.FluidUtilz;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
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
	
	public TileEntityPump() {
		this.tank = new FluidTank(FluidUtilz.bucketToMb(100));
		this.startClock = _Config.pumpSpeed;
		this.clock = this.startClock;
	}
	
	public void update() {
		if(!this.worldObj.isRemote) {
			clock --;
			if(clock <= 0) {
				clock = startClock;
				
				if(canRun()) {
					
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

	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	public int getEnergyStored() {
		return this.energy;
	}

	public int getCapacity() {
		return this.capacity;
	}
	
	// -- TE -- //
	
	public void readFromNBT(NBTTagCompound nbt) { super.readFromNBT(nbt); }
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) { return super.writeToNBT(nbt); }
	
}