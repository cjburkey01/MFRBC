package com.cjburkey.mfrbc.tile;

import java.util.Stack;
import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.block.BlockPump;
import com.cjburkey.mfrbc.fluid.FluidUtilz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityPumpPipe extends TileEntity implements ITickable {
	
	private TileEntityPump pump = null;
	private Stack<BlockPos> fluids = new Stack<BlockPos>();
	private boolean finished = false;
	
	public boolean playerPlaced = false;
	
	public void update() {
		if(!this.worldObj.isRemote) {
			if(this.worldObj.isAirBlock(this.pos.up()) && !this.playerPlaced) {
				this.worldObj.destroyBlock(this.pos, false);
			}
		}
	}
	
	public void run() {
		if(!this.playerPlaced) {
			if(pump == null) { findPump(); }
			if(pump == null) { Util.log("No pump found."); this.worldObj.destroyBlock(this.pos, false); return; }
			
			TileEntityPumpPipe below;
			if(finished && ((below = TileEntityPump.pipeBelow(this.worldObj, this.pos)) != null)) {
				below.run();
				return;
			}
			
			if(!finished) {
				go();
			}
			
			if(fluids.isEmpty() && !finished) {
				scan();
				if(fluids.isEmpty()) {
					TileEntityPumpPipe p = TileEntityPump.pumpPipeBelow(this.worldObj, this.pos);
					finished = true;
				}
			}
		}
	}
	
	public void findPump() {
		for(int y = this.pos.getY(); y < this.worldObj.getHeight(); y ++) {
			BlockPos p = new BlockPos(this.pos.getX(), y, this.pos.getZ());
			IBlockState state = this.worldObj.getBlockState(p);
			if(state.getBlock() instanceof BlockPump) {
				pump = (TileEntityPump) this.worldObj.getTileEntity(p);
			}
		}
	}
	
	public boolean canTake(BlockPos p) {
		IBlockState state = this.worldObj.getBlockState(p);
		if(state != null && state.getBlock() != null && FluidUtilz.isFluid(this.worldObj, p)) {
			return true;
		}
		return false;
	}
	
	public void go() {
		if(!this.fluids.isEmpty()) {
			BlockPos next = this.fluids.pop();
			if(canTake(next)) {
				Fluid f = FluidUtilz.getFluid(this.worldObj, next);
				FluidStack fs = new FluidStack(f, FluidUtilz.getBucket());
				if(this.pump.fill(EnumFacing.NORTH, fs, true) > 0) {
					this.pump.modifyEnergyStored(-_Config.pumpRfPerOp);
					//this.worldObj.setBlockState(next, Blocks.DIRT.getDefaultState());
					this.worldObj.setBlockToAir(next);
				}
			}
		}
	}
	
	public void scan() {
		fluids.clear();
		int half = (int) ((double) _Config.pumpSize / 2d);
		for(int x = this.getPos().getX() - half; x < this.getPos().getX() + half; x ++) {
			for(int z = this.getPos().getZ() - half; z < this.getPos().getZ() + half; z ++) {
				BlockPos p = new BlockPos(x, this.pos.getY(), z);
				if(!p.equals(this.pos) && canTake(p)) {
					this.fluids.add(p);
				}
			}
		}
	}
	
}