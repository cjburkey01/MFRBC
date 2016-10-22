package com.cjburkey.mfrbc.packet;

import com.cjburkey.mfrbc.block.BlockPump;
import com.cjburkey.mfrbc.tile.TileEntityPump;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPumpToServer implements IMessage {
	
	private int x, y, z;
	
	public PacketPumpToServer() {  }
	public PacketPumpToServer(BlockPos p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}
	public PacketPumpToServer(int x, int y, int z) {
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
	
	public static class Handler implements IMessageHandler<PacketPumpToServer, PacketPumpToClient> {

		public PacketPumpToClient onMessage(PacketPumpToServer msg, MessageContext ctx) {
			World w = ctx.getServerHandler().playerEntity.worldObj;
			BlockPos p = new BlockPos(msg.x, msg.y, msg.z);
			IBlockState s = w.getBlockState(p);
			if(s.getBlock() instanceof BlockPump) {
				TileEntityPump q = (TileEntityPump) w.getTileEntity(p);
				boolean nu = q.getFluid() == null || q.getFluid().getFluid() == null;
				String n = (nu || q.getFluid().getLocalizedName() == null) ? "null" : q.getFluid().getLocalizedName();
				return new PacketPumpToClient(q.getEnergyStored(), q.getCapacity(), (nu) ? 0 : q.getFluid().amount, (nu) ? 0 : q.getTankCap(), n);
			}
			return null;
		}
		
	}
	
}