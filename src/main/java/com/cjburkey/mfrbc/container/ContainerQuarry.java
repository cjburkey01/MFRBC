package com.cjburkey.mfrbc.container;

import com.cjburkey.mfrbc.slot.SlotExitOnly;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuarry extends Container {
	
	private TileEntityQuarry te;
	
	public ContainerQuarry(IInventory inv, TileEntityQuarry te) {
		this.te = te;
		
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 3; ++x) {
				this.addSlotToContainer(new SlotExitOnly(te, x + y * 3, 62 + x * 18, 17 + y * 18));
			}
		}
		
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for(int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, 8 + x * 18, 142));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);
		if(slot != null && slot.getHasStack() && fromSlot < 9) {
			ItemStack current = slot.getStack();
			previous = current.copy();
			
			if(!this.mergeItemStack(current, 9, 45, true)) {
				return null;
			}
			
			if(current.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			
			if(current.stackSize == previous.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}
	
	public boolean canInteractWith(EntityPlayer pl) {
		return this.te.isUseableByPlayer(pl);
	}
	
}