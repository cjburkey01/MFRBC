package com.cjburkey.mfrbc.container;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.slot.SlotExitOnly;
import com.cjburkey.mfrbc.slot.SlotUpgrade;
import com.cjburkey.mfrbc.tile.TileEntityUpgrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUpgrader extends Container {
	
	private TileEntityUpgrader te;
	private int size = 1;
	
	public ContainerUpgrader(IInventory inv, TileEntityUpgrader te) {
		this.te = te;
		
		this.addSlotToContainer(new SlotUpgrade(te, 0, 80, 35));
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				int i = x + y * 9 + 9;
				this.addSlotToContainer(new Slot(inv, i, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			int i = x;
			this.addSlotToContainer(new Slot(inv, i, 8 + x * 18, 142));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.size) {
				if (!this.mergeItemStack(current, this.size, 36, true))
					return null;
			} else {
				if (!this.mergeItemStack(current, 0, this.size, false))
					return null;
			}
			
			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}
	
	public boolean canInteractWith(EntityPlayer pl) {
		return this.te.isUseableByPlayer(pl);
	}
	
}