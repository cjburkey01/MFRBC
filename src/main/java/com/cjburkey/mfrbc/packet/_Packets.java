package com.cjburkey.mfrbc.packet;

import com.cjburkey.mfrbc.Util;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class _Packets {
	
	private static SimpleNetworkWrapper network;
	
	public static final void commonPreinit() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("MFRBC");
		
		network.registerMessage(PacketQuarryToServer.Handler.class, PacketQuarryToServer.class, 0, Side.SERVER);
		network.registerMessage(PacketQuarryToClient.Handler.class, PacketQuarryToClient.class, 1, Side.CLIENT);
		
		network.registerMessage(PacketPumpToServer.Handler.class, PacketPumpToServer.class, 2, Side.SERVER);
		network.registerMessage(PacketPumpToClient.Handler.class, PacketPumpToClient.class, 3, Side.CLIENT);
		
		Util.log("Registered packets.");
	}
	
	public static SimpleNetworkWrapper getNetwork() { return network; }
	
}