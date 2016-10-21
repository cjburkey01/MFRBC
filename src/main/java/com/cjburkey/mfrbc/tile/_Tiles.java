package com.cjburkey.mfrbc.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class _Tiles {
	
	public static final void commonPreinit() {
		GameRegistry.registerTileEntity(TileEntityQuarry.class, "tileEntityQuarry");
		GameRegistry.registerTileEntity(TileEntityMarker.class, "tileEntityMarker");
		GameRegistry.registerTileEntity(TileEntityUpgrader.class, "tileEntityUpgrader");
		GameRegistry.registerTileEntity(TileEntityPump.class, "tileEntityPump");
		GameRegistry.registerTileEntity(TileEntityPumpPipe.class, "tileEntityPumpPipe");
	}
	
}