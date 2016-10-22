package com.cjburkey.mfrbc.container;

import com.cjburkey.mfrbc.tile.TileEntityPump;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPump extends Container {
	
	private TileEntityPump te;
	
	public ContainerPump(IInventory inv, TileEntityPump te) { this.te = te; }
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) { return null; }
	public boolean canInteractWith(EntityPlayer pl) { return this.te.isUseableByPlayer(pl); }
	
}