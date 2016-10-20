package com.cjburkey.mfrbc.slot;

import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotExitOnly extends Slot {

	public SlotExitOnly(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}
	
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
}