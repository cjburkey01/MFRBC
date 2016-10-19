package com.cjburkey.mfrbc;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class _Config {
	
	public static int quarryMaxEnergy, quarryMaxReceive;
	
	public static final void commonPreinit(FMLPreInitializationEvent e) {
		Configuration c = new Configuration(new File(e.getSuggestedConfigurationFile().getParentFile(), "/mfrbc/config.cfg"));
		c.load();
		
		quarryMaxEnergy = c.getInt("quarryMaxEnergy", "quarry", 500000, 9999, 1000000, "The maximum energy the quarry can hold.");
		quarryMaxReceive = c.getInt("quarryMaxReceive", "quarry", 1000000, 9999, 10000000, "The maximum energy the quarry can receive per tick.");
		
		c.save();
	}
	
}