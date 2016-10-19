package com.cjburkey.mfrbc;

import org.apache.logging.log4j.LogManager;
import com.cjburkey.mfrbc.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Info.name, version = Info.version, modid = Info.id)
public class MFRBC {
	
	@Instance
	public static MFRBC instance;
	
	@SidedProxy(clientSide = Info.cproxy, serverSide = Info.sproxy)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preinit(FMLPreInitializationEvent e) { proxy.preinit(e); }

	@EventHandler
	public static void init(FMLInitializationEvent e) { proxy.init(e); }

	@EventHandler
	public static void postinit(FMLPostInitializationEvent e) { proxy.postinit(e); }
	
	public static final void log(Object msg) { LogManager.getLogger("MFRBC").info(msg); }
	
}