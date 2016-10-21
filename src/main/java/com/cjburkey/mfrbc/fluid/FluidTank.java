package com.cjburkey.mfrbc.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class FluidTank implements IFluidTank {
	
	private FluidStack fluid;
	private int cap;
	
	public FluidTank(int cap) {
		this.setCapacity(cap);
	}

	public FluidStack getFluid() {
		return this.fluid;
	}
	
	public void setFluid(FluidStack n) {
		this.fluid = n;
	}

	public int getFluidAmount() {
		return this.getFluid().amount;
	}

	public int getCapacity() {
		return this.cap;
	}
	
	public int getRemainingCapacity() {
		return this.getCapacity() - this.getFluidAmount();
	}
	
	public void setCapacity(int cap) {
		this.cap = (cap >= 0) ? cap : 0;
	}
	
	public boolean isFull() {
		return this.getRemainingCapacity() == 0;
	}

	public FluidTankInfo getInfo() {
		return new FluidTankInfo(this);
	}

	public int fill(FluidStack r, boolean doFill) {
		if(r == null) {
			return 0;
		}

		if(this.getFluid() == null) {
			if (!doFill) {
				return Math.min(this.getCapacity(), r.amount);
			}
			this.setFluid(new FluidStack(r, Math.min(this.getCapacity(), r.amount)));
			return this.getFluidAmount();
		}
		
		if(!this.getFluid().isFluidEqual(r)) {
			return 0;
		}
		
		int fillAmount = Math.min(this.getCapacity() - this.getFluidAmount(), r.amount);
		if(doFill && fillAmount > 0) {
			this.getFluid().amount += fillAmount;
		}

		return fillAmount;
	}

	public FluidStack drain(int amt, boolean drain) {
		if(this.getFluid() == null) {
			return null;
		}

		if(this.getFluidAmount() < amt) {
			amt = this.getFluidAmount();
		}

		FluidStack drainedFluid = new FluidStack(this.getFluid(), amt);
		if(drain) {
			this.getFluid().amount -= amt;
			if(this.getFluidAmount() == 0) {
				this.setFluid(null);
			}
		}

		return drainedFluid;
	}
	
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		setCapacity(nbt.getInteger("Capacity"));
		FluidStack loadedFluid = (nbt.hasKey("Empty")) ? null : FluidStack.loadFluidStackFromNBT(nbt);
		setFluid(loadedFluid);
		return this;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Capacity", this.getCapacity());
		if(fluid != null) {
			fluid.writeToNBT(nbt);
		} else {
			nbt.setString("Empty", "");
		}
		return nbt;
	}
	
}