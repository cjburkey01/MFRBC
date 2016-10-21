package com.cjburkey.mfrbc.packet;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.gui.GuiQuarry;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryToClient implements IMessage {
	
	private int energy, max;
	private boolean working;
	
	public PacketQuarryToClient() {  }
	public PacketQuarryToClient(int e, int m, boolean w) {
		this.energy = e;
		this.max = m;
		this.working = w;
	}
	
	public void fromBytes(ByteBuf buf) {
		String[] s = ByteBufUtils.readUTF8String(buf).split(";");
		
		this.energy = Integer.parseInt(s[0]);
		this.max = Integer.parseInt(s[1]);
		this.working = Boolean.parseBoolean(s[2]);
	}
	public void toBytes(ByteBuf buf) {
		 ByteBufUtils.writeUTF8String(buf, this.energy + ";" + this.max + ";" + this.working);
	}
	
	public static class Handler implements IMessageHandler<PacketQuarryToClient, IMessage> {

		public IMessage onMessage(PacketQuarryToClient msg, MessageContext ctx) {
			if(msg != null) {
				//Util.log("Message received on the client, " + msg.energy + " / " + msg.max + ".  Working: " + msg.working);
				GuiQuarry.energy = msg.energy;
				GuiQuarry.maxEnergy = msg.max;
				GuiQuarry.working = msg.working;
			}
			return null;
		}
		
	}
	
}