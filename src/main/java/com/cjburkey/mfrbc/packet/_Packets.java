package com.cjburkey.mfrbc.packet;

import javafx.application.Platform;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class _Packets {
	
	public static SimpleNetworkWrapper network;
	
	public static final void commonPreinit() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("MFRBC");

		//network.registerMessage(MessageClientServerQuarry.Handler.class, MessageClientServerQuarry.class, 0, Side.SERVER);
		//network.registerMessage(MessageServerClientQuarry.Handler.class, MessageServerClientQuarry.class, 1, Side.CLIENT);

		network.registerMessage(PacketQuarryToServer.Handler.class, PacketQuarryToServer.class, 0, Side.SERVER);
		network.registerMessage(PacketQuarryToClient.Handler.class, PacketQuarryToClient.class, 1, Side.CLIENT);
		
		System.out.println("Registered Stuff");
	}
	
}