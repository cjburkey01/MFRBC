package com.cjburkey.mfrbc.block;

import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockQuarry extends Block implements ITileEntityProvider {
	
	public BlockQuarry() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
	}

	public TileEntity createNewTileEntity(World w, int m) {
		return new TileEntityQuarry();
	}
	
}