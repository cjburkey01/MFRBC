package com.cjburkey.mfrbc.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityUpgrader extends TileEntity implements IInventory {
	
	private String customName;
	private String unloc;
	private ItemStack[] inventory;
	
	public TileEntityUpgrader() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.unloc = "tile.blockUpgrader.name";
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getName() {
		return this.hasCustomName() ? this.customName : unloc;
	}

	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}
	
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	public int getSizeInventory() {
		return 1;
	}

	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory()) return null;
		
		return this.inventory[index];
	}

	public ItemStack decrStackSize(int index, int count) {
		if(this.getStackInSlot(index) != null) {
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
		if(index < 0 || index >= this.getSizeInventory()) return;

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
	
	public void clear() {
		for(int i = 0; i < this.getSizeInventory(); i ++) {
			this.setInventorySlotContents(i, null);
		}
	}
	
	public boolean isItemValidForSlot(int index, ItemStack stack) { return false; }
	public int getField(int id) { return 0; }
	public void setField(int id, int value) {  }
	public int getFieldCount() { return 0; }
	public void openInventory(EntityPlayer player) {  }
	public void closeInventory(EntityPlayer player) {  }
	
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
		
		return super.writeToNBT(nbt);
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
		
		super.readFromNBT(nbt);
	}
	
}