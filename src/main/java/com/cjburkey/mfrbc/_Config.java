package com.cjburkey.mfrbc;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class _Config {
	
	public static int quarryMaxEnergy, quarryMaxReceive, quarrySpeed, quarryRfPerOp, quarryBlocksPerBreak, quarryMaxSize;
	public static boolean quarryRequireRf;
	
	public static int pumpMaxEnergy, pumpMaxReceive, pumpSpeed, pumpRfPerOp, pumpSize;
	public static boolean pumpRequireRf;
	
	public static final void commonPreinit(FMLPreInitializationEvent e) {
		Configuration c = new Configuration(new File(e.getSuggestedConfigurationFile().getParentFile(), "/mfrbc/config.cfg"));
		c.load();
		
		// -- QUARRY -- //
		quarryMaxEnergy = c.getInt("quarryMaxEnergy", "quarry", 5000, 1000, 1000000, "The maximum energy the quarry can hold.");
		quarryMaxReceive = c.getInt("quarryMaxReceive", "quarry", 1000000, 250, 1000000000, "The maximum energy the quarry can receive per tick.");
		quarrySpeed = c.getInt("quarrySpeed", "quarry", 2, 0, 20, "Number of game ticks(20 t/s) between operations.");
		quarryRfPerOp = c.getInt("quarryRfPerOp", "quarry", 100, 25, 1000, "Amount of RF taken per block.  This number is multiplied by block hardness.");
		quarryBlocksPerBreak = c.getInt("quarryBlocksPerBreak", "quarry", 1, 1, 10, "How many blocks the quarry breaks every run.");
		quarryMaxSize = c.getInt("quarryMaxSize", "quarry", 64, 10, 128, "The max length and width of the quarry.");
		quarryRequireRf = c.getBoolean("quarryRequireRf", "quarry", true, "Whether or not the quarry needs power to function.");
		
		// -- PUMP -- //
		pumpMaxEnergy = c.getInt("pumpMaxEnergy", "pump", 5000, 1000, 1000000, "The maximum energy the pump can hold.");
		pumpMaxReceive = c.getInt("pumpMaxReceive", "pump", 1000000, 250, 1000000000, "The maximum energy the pump can receive per tick.");
		pumpSpeed = c.getInt("pumpSpeed", "pump", 5, 2, 100, "Number of game ticks(20 t/s) between operations.");
		pumpRfPerOp = c.getInt("pumpRfPerOp", "pump", 150, 100, 10000, "Amount of RF taken per fluid source.");
		pumpSize = c.getInt("pumpSize", "pump", 64, 15, 128, "The diameter of the pump's influence.");
		pumpRequireRf = c.getBoolean("pumpRequireRf", "pump", true, "Whether or not the pump needs power to function.");
		
		c.save();
	}
	
}