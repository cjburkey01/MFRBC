package com.cjburkey.mfrbc.tile;

import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.block.BlockMarker;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityMarker extends TileEntity {
	
	public BlockPos marker1, marker2;
	
	public BlockPos[] getMarkers() {
		return new BlockPos[] { marker1, marker2 };
	}
	
	public void search() {
		marker1 = null;
		marker2 = null;
		
		for(int x = this.pos.getX() - _Config.quarryMaxSize; x < this.pos.getX(); x ++) {
			BlockPos p = new BlockPos(x, this.pos.getY(), this.pos.getZ());
			IBlockState s = this.worldObj.getBlockState(p);
			if(s.getBlock() instanceof BlockMarker) {
				marker1 = p;
			}
		}
		
		if(marker1 == null) {
			for(int x = this.pos.getX() + _Config.quarryMaxSize; x > this.pos.getX() + 1; x --) {
				BlockPos p = new BlockPos(x, this.pos.getY(), this.pos.getZ());
				IBlockState s = this.worldObj.getBlockState(p);
				if(s.getBlock() instanceof BlockMarker) {
					marker1 = p;
				}
			}
		}
		
		for(int z = this.pos.getZ() - _Config.quarryMaxSize; z < this.pos.getZ(); z ++) {
			BlockPos p = new BlockPos(this.pos.getX(), this.pos.getY(), z);
			IBlockState s = this.worldObj.getBlockState(p);
			if(s.getBlock() instanceof BlockMarker) {
				marker2 = p;
			}
		}
		
		if(marker2 == null) {
			for(int z = this.pos.getZ() + _Config.quarryMaxSize; z > this.pos.getZ() + 1; z --) {
				BlockPos p = new BlockPos(this.pos.getX(), this.pos.getY(), z);
				IBlockState s = this.worldObj.getBlockState(p);
				if(s.getBlock() instanceof BlockMarker) {
					marker2 = p;
				}
			}
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(nbt == null) { nbt = new NBTTagCompound(); }
		
		if(marker1 != null) {
			nbt.setDouble("marker1_x", marker1.getX());
			nbt.setDouble("marker1_y", marker1.getY());
			nbt.setDouble("marker1_z", marker1.getZ());
		}
		if(marker2 != null) {
			nbt.setDouble("marker2_x", marker2.getX());
			nbt.setDouble("marker2_y", marker2.getY());
			nbt.setDouble("marker2_z", marker2.getZ());
		}
		
		return super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt != null) {
			if(nbt.hasKey("marker1_x") && nbt.hasKey("marker1_y") && nbt.hasKey("marker1_z")) {
				marker1 = new BlockPos(nbt.getDouble("marker1_x"), nbt.getDouble("marker1_y"), nbt.getDouble("marker1_z"));
			}
			if(nbt.hasKey("marker2_x") && nbt.hasKey("marker2_y") && nbt.hasKey("marker2_z")) {
				marker2 = new BlockPos(nbt.getDouble("marker2_x"), nbt.getDouble("marker2_y"), nbt.getDouble("marker2_z"));
			}
		}
		
		super.readFromNBT(nbt);
	}
	
}