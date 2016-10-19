package com.cjburkey.mfrbc.proxy;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public void preinit(FMLPreInitializationEvent e) {
		super.preinit(e);
	}

	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	public void postinit(FMLPostInitializationEvent e) {
		super.postinit(e);
	}
	
}