package com.cjburkey.mfrbc.proxy;

import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.item._Items;
import com.cjburkey.mfrbc.oredict._Dict;
import com.cjburkey.mfrbc.packet._Packets;
import com.cjburkey.mfrbc.rec._Recipes;
import com.cjburkey.mfrbc.tab._Tabs;
import com.cjburkey.mfrbc.tile._Tiles;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent e) {
		_Packets.commonPreinit();
		_Config.commonPreinit(e);
		_Tabs.commonPreinit();
		_Items.commonPreinit();
		_Blocks.commonPreinit();
		_Tiles.commonPreinit();
		_Dict.commonPreinit();
	}

	public void init(FMLInitializationEvent e) {
		_Recipes.commonInit();
	}

	public void postinit(FMLPostInitializationEvent e) {  }
	
}