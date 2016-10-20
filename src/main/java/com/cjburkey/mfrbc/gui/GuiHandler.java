package com.cjburkey.mfrbc.gui;

import com.cjburkey.mfrbc.container.ContainerQuarry;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int guiQuarry = 0;
	
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == guiQuarry) {
			return new ContainerQuarry(player.inventory, (TileEntityQuarry) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == guiQuarry) {
			return new GuiQuarry(player.inventory, (TileEntityQuarry) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
}