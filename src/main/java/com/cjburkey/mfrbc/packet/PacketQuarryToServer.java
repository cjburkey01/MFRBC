package com.cjburkey.mfrbc.packet;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.block.BlockQuarry;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketQuarryToServer implements IMessage {
	
	private int x, y, z;
	
	public PacketQuarryToServer() {  }
	public PacketQuarryToServer(BlockPos p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}
	public PacketQuarryToServer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void fromBytes(ByteBuf buf) {
		String[] s = ByteBufUtils.readUTF8String(buf).split(";");
		this.x = Integer.parseInt(s[0]);
		this.y = Integer.parseInt(s[1]);
		this.z = Integer.parseInt(s[2]);
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.x + ";" + this.y + ";" + this.z);
	}
	
	public static class Handler implements IMessageHandler<PacketQuarryToServer, PacketQuarryToClient> {

		public PacketQuarryToClient onMessage(PacketQuarryToServer msg, MessageContext ctx) {
			//Util.log("Message received on the server.");
			World w = ctx.getServerHandler().playerEntity.worldObj;
			BlockPos p = new BlockPos(msg.x, msg.y, msg.z);
			IBlockState s = w.getBlockState(p);
			if(s.getBlock() instanceof BlockQuarry) {
				TileEntityQuarry q = (TileEntityQuarry) w.getTileEntity(p);
				//Util.log("Found quarry: " + q);
				return new PacketQuarryToClient(q.getEnergyStored(), q.getCapacity(), q.isWorking());
			}
			return null;
		}
		
	}
	
}