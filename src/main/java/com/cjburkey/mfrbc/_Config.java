package com.cjburkey.mfrbc;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class _Config {
	
	public static int quarryMaxEnergy, quarryMaxReceive, quarrySpeed, quarryRfPerOp;
	public static boolean quarryRequireRf;
	
	public static final void commonPreinit(FMLPreInitializationEvent e) {
		Configuration c = new Configuration(new File(e.getSuggestedConfigurationFile().getParentFile(), "/mfrbc/config.cfg"));
		c.load();
		
		quarryMaxEnergy = c.getInt("quarryMaxEnergy", "quarry", 500000, 9999, 1000000, "The maximum energy the quarry can hold.");
		quarryMaxReceive = c.getInt("quarryMaxReceive", "quarry", 1000000, 9999, 10000000, "The maximum energy the quarry can receive per tick.");
		quarrySpeed = c.getInt("quarrySpeed", "quarry", 2, 0, 20, "Number of game ticks(20 t/s) between operations.");
		quarryRfPerOp = c.getInt("quarryRfPerOp", "quarry", 20, 5, 1000, "Amount of RF taken per block.  This number is multiplied by block hardness.");
		quarryRequireRf = c.getBoolean("quarryRequireRf", "quarry", true, "Whether or not the quarry needs power to function.");
		
		c.save();
	}
	
}