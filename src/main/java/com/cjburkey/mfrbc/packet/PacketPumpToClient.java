package com.cjburkey.mfrbc.packet;

import com.cjburkey.mfrbc.gui.GuiPump;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPumpToClient implements IMessage {
	
	private int energy, max, fluid, maxFluid;
	private String name;
	
	public PacketPumpToClient() {  }
	public PacketPumpToClient(int e, int m, int i, int o, String n) {
		this.energy = e;
		this.max = m;
		this.fluid = i;
		this.maxFluid = o;
		this.name = n;
	}
	
	public void fromBytes(ByteBuf buf) {
		String[] s = ByteBufUtils.readUTF8String(buf).split(";");
		
		this.energy = Integer.parseInt(s[0]);
		this.max = Integer.parseInt(s[1]);
		this.fluid = Integer.parseInt(s[2]);
		this.maxFluid = Integer.parseInt(s[3]);
		this.name = s[4];
	}
	public void toBytes(ByteBuf buf) {
		 ByteBufUtils.writeUTF8String(buf, this.energy + ";" + this.max + ";" + this.fluid + ";" + this.maxFluid + ";" + this.name);
	}
	
	public static class Handler implements IMessageHandler<PacketPumpToClient, IMessage> {

		public IMessage onMessage(PacketPumpToClient msg, MessageContext ctx) {
			if(msg != null) {
				GuiPump.energy = msg.energy;
				GuiPump.maxEnergy = msg.max;
				GuiPump.fluid = msg.fluid;
				GuiPump.maxFluid = msg.maxFluid;
				GuiPump.fluidName = msg.name.trim();
			}
			return null;
		}
		
	}
	
}