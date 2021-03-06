package com.cjburkey.mfrbc.gui;

import com.cjburkey.mfrbc.container.ContainerPump;
import com.cjburkey.mfrbc.container.ContainerQuarry;
import com.cjburkey.mfrbc.container.ContainerUpgrader;
import com.cjburkey.mfrbc.tile.TileEntityPump;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import com.cjburkey.mfrbc.tile.TileEntityUpgrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int guiQuarry = 0, guiUpgrader = 1, guiPump = 2;
	
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == guiQuarry) {
			return new ContainerQuarry(player.inventory, (TileEntityQuarry) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(id == guiUpgrader) {
			return new ContainerUpgrader(player.inventory, (TileEntityUpgrader) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(id == guiPump) {
			return new ContainerPump(player.inventory, (TileEntityPump) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == guiQuarry) {
			return new GuiQuarry(player, (TileEntityQuarry) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(id == guiUpgrader) {
			return new GuiUpgrader(player, (TileEntityUpgrader) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(id == guiPump) {
			return new GuiPump(player, (TileEntityPump) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
}