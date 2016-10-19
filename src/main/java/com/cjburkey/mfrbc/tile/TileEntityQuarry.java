package com.cjburkey.mfrbc.tile;

import com.cjburkey.mfrbc._Config;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityQuarry extends TileEntity implements ITickable, IEnergyReceiver {
	
	protected int energy;
	protected int capacity;
	protected int maxReceive;
	
	public TileEntityQuarry() {
		this.setEnergyStored(0);
		this.setCapacity(_Config.quarryMaxEnergy);
		this.maxReceive = _Config.quarryMaxReceive;
	}
	
	public void update() {
		
	}

	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("Energy");
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (this.energy < 0) {
			this.energy = 0;
		}
		nbt.setInteger("Energy", this.energy);
		return nbt;
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
	
}