package com.cjburkey.mfrbc.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import com.cjburkey.mfrbc.MFRBC;
import com.cjburkey.mfrbc._Config;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityQuarry extends TileEntity implements ITickable, IEnergyReceiver, IInventory {
	
	private ItemStack[] inventory;
	private String customName;
	private int clock;
	private Stack<BlockPos> blocks = new Stack<BlockPos>();
	private int startX, startZ;
	private int endX, endZ;
	private boolean finished = false;
	private boolean firstTick = true;
	
	private int size = 16;
	
	protected int energy;
	protected int capacity;
	protected int maxReceive;
	
	public TileEntityQuarry() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.clock = _Config.quarrySpeed;
		
		this.setEnergyStored(0);
		this.setCapacity(_Config.quarryMaxEnergy);
		this.maxReceive = _Config.quarryMaxReceive;
	}
	
	public void scan() {
		this.startX = this.getPos().getX() + 1;
		this.startZ = this.getPos().getZ() + 1;
		
		this.endX = this.getPos().getX() + (size + 1);
		this.endZ = this.getPos().getZ() + (size + 1);
		
		List<BlockPos> bs = new ArrayList<BlockPos>();
		for(int x = this.startX; x < this.endX; x ++) {
			for(int z = this.startZ; z < this.endZ; z ++) {
				for(int y = this.getPos().getY() + 1; y > 0; y --) {
					BlockPos p = new BlockPos(x, y, z);
					IBlockState state = this.worldObj.getBlockState(p);
					Block b = state.getBlock();
					if(!state.equals(null) && !b.equals(Blocks.AIR) && !(b instanceof BlockLiquid) && (getHardness(p) >= 0)) {
						bs.add(p);
					}
				}
			}
		}
		blocks.clear();
		Collections.reverse(bs);
		blocks.addAll(bs);
		bs.clear();
	}
	
	public float getHardness(BlockPos pos) {
		IBlockState state = this.worldObj.getBlockState(pos);
		Block b = state.getBlock();
		return b.getBlockHardness(state, this.worldObj, pos);
	}
	
	public void update() {
		if(!this.worldObj.isRemote) {
			if(firstTick) { firstTick = false; scan(); }

			if(!finished) {
				clock --;
				if(clock <= 0) {
					clock = _Config.quarrySpeed;
					if(done()) { scan(); if(done()) { finished = true; } }
					if(!done() && shouldRun(getNextBlockPos(false))) { run(); }
				}
			}
		}
	}
	
	public boolean done() {
		return blocks.empty();
	}
	
	public BlockPos getNextBlockPos(boolean pop) {
		return (pop) ? blocks.pop() : blocks.peek();
	}
	
	public boolean shouldRun(BlockPos nextBlock) {
		if(this.getEnergyStored() >= this.getRfPrice(nextBlock) && !isInventoryFull()) {
			return true;
		}
		return true; //TODO: SET TO FALSE!!!!1!!11111!!11111111!1111111!11!!
	}
	
	public int getRfPrice(BlockPos p) {
		return (int) (Math.ceil(_Config.quarryRfPerOp * getHardness(p)));
	}
	
	public boolean isInventoryFull() {
		for(ItemStack i : this.inventory) {
			if(i == null) {
				return false;
			}
		}
		return true;
	}
	
	public List<ItemStack> getDrops(BlockPos pos) {
		IBlockState state = this.worldObj.getBlockState(pos);
		Block block = state.getBlock();
		return block.getDrops(this.worldObj, pos, state, 0);
	}
	
	public void run() {
		BlockPos pos = getNextBlockPos(true);
		this.worldObj.destroyBlock(pos, false);
		MFRBC.log(pos);
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.containerQuarry";
	}
	
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}
	
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	public int getSizeInventory() {
		return 27;
	}
	
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}
	
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					//Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
			
		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
	public void openInventory(EntityPlayer player) {  }
	public void closeInventory(EntityPlayer player) {  }
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	
	public int getField(int id) {
		return 0;
	}

	public void setField(int id, int value) {
	}

	public int getFieldCount() {
		return 0;
	}
	
	public void clear() {
		for(int i = 0; i < this.getSizeInventory(); i ++) {
			this.setInventorySlotContents(i, null);
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);

		if (this.hasCustomName()) {
			nbt.setString("CustomName", this.getCustomName());
		}
		
		if (this.energy < 0) {
			this.energy = 0;
		}
		nbt.setInteger("Energy", this.energy);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}
		
		this.energy = nbt.getInteger("Energy");
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		}
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