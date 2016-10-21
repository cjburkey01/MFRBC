package com.cjburkey.mfrbc.tile;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.block.BlockPump;
import com.cjburkey.mfrbc.block.BlockPumpPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityPumpPipe extends TileEntity {
	
	private TileEntityPump pump = null;
	
	public void run() {
		if(pump == null) { findPump(); }
		if(pump == null) { Util.log("No pump found."); this.worldObj.destroyBlock(this.pos, false); return; }
		
		
	}
	
	public TileEntityPumpPipe pipeBelow() {
		BlockPos b = this.pos.down();
		IBlockState state = this.worldObj.getBlockState(b);
		if(state.getBlock() instanceof BlockPumpPipe) {
			Util.log("Found pipe below: " + this.pos);
			return (TileEntityPumpPipe) this.worldObj.getTileEntity(b);
		}
		return null;
	}
	
	public void findPump() {
		for(int y = this.pos.getY(); y < this.worldObj.getHeight(); y ++) {
			BlockPos p = new BlockPos(this.pos.getX(), y, this.pos.getZ());
			IBlockState state = this.worldObj.getBlockState(p);
			if(state.getBlock() instanceof BlockPump) {
				Util.log("Found pump at: " + p + " from " + this.pos);
				pump = (TileEntityPump) this.worldObj.getTileEntity(p);
			}
		}
	}
	
}