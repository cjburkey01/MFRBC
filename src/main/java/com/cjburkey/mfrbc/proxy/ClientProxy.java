package com.cjburkey.mfrbc.proxy;

import com.cjburkey.mfrbc.MFRBC;
import com.cjburkey.mfrbc.gui.GuiHandler;
import com.cjburkey.mfrbc.item._Items;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	
	public void preinit(FMLPreInitializationEvent e) { super.preinit(e); }

	public void init(FMLInitializationEvent e) {
		super.init(e);
		
		_Items.clientInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(MFRBC.instance, new GuiHandler());
	}

	public void postinit(FMLPostInitializationEvent e) { super.postinit(e); }
	
}