package com.cjburkey.mfrbc;

import org.apache.logging.log4j.LogManager;
import com.cjburkey.mfrbc.block.BlockUpgrader;
import com.cjburkey.mfrbc.tile.TileEntityUpgrader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Util {
	
	public static final void log(Object msg) { LogManager.getLogger("MFRBC").info(msg); }
	public static final void chat(EntityLivingBase ply, Object msg) { ply.addChatMessage(new TextComponentString(msg + "")); }
	public static final int between(int start, int end, float percent) { return (int) ((float) start + ((float) end * percent)); }
	
	public static int getUpgradeCount(World wo, BlockPos p, Item upgradeItem) {
		int amt = 0;
		
		BlockPos up = p.up();
		BlockPos down = p.down();
		BlockPos west = p.west();
		BlockPos east = p.east();
		BlockPos north = p.north();
		BlockPos south = p.south();
		
		ItemStack u = getUpgrade(wo, up);
		ItemStack d = getUpgrade(wo, down);
		ItemStack w = getUpgrade(wo, west);
		ItemStack e = getUpgrade(wo, east);
		ItemStack n = getUpgrade(wo, north);
		ItemStack s = getUpgrade(wo, south);
		
		if(u != null && u.getItem().equals(upgradeItem)) { amt ++; }
		if(d != null && d.getItem().equals(upgradeItem)) { amt ++; }
		if(w != null && w.getItem().equals(upgradeItem)) { amt ++; }
		if(e != null && e.getItem().equals(upgradeItem)) { amt ++; }
		if(n != null && n.getItem().equals(upgradeItem)) { amt ++; }
		if(s != null && s.getItem().equals(upgradeItem)) { amt ++; }
		
		return amt;
	}
	
	public static ItemStack getUpgrade(World w, BlockPos p) {
		IBlockState s = w.getBlockState(p);
		if(s.getBlock() instanceof BlockUpgrader) {
			TileEntity e = w.getTileEntity(p);
			if(e != null) {
				TileEntityUpgrader te = (TileEntityUpgrader) e;
				return te.getStackInSlot(0);
			}
		}
		return null;
	}
	
}