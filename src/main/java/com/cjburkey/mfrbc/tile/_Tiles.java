package com.cjburkey.mfrbc.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class _Tiles {
	
	public static final void commonInit() {
		GameRegistry.registerTileEntity(TileEntityQuarry.class, "tileEntityQuary");
	}
	
}