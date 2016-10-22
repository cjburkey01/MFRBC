package com.cjburkey.mfrbc.slot;

import com.cjburkey.mfrbc.item.ItemUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot {

	public SlotUpgrade(IInventory inv, int i, int x, int y) {
		super(inv, i, x, y);
	}
	
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemUpgrade;
	}
	
}