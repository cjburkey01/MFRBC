package com.cjburkey.mfrbc.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc._Config;
import com.cjburkey.mfrbc.block.BlockMarker;
import com.cjburkey.mfrbc.block.BlockQuarry;
import com.cjburkey.mfrbc.block.BlockUpgrader;
import com.cjburkey.mfrbc.item._Items;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityQuarry extends TileEntity implements ITickable, IEnergyReceiver, IInventory {
	
	private ItemStack[] inventory;
	private String customName;
	private int clock;
	private Stack<BlockPos> blocks = new Stack<BlockPos>();
	private boolean firstTick = true;
	private int startX, startZ;
	private int endX, endZ;
	private boolean finished = false;
	private int blocksPerRun;
	private String unloc;
	private int speed;
	
	protected int energy;
	protected int capacity;
	protected int maxReceive;
	
	public TileEntityQuarry() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.speed = _Config.quarrySpeed;
		this.clock = this.speed;
		this.blocksPerRun = _Config.quarryBlocksPerBreak;
		
		this.setEnergyStored(0);
		this.setCapacity(_Config.quarryMaxEnergy);
		this.maxReceive = _Config.quarryMaxReceive;
		this.unloc = "tile.blockQuarry.name";
	}
	
	// -- QUARRY -- //
	
	public boolean isWorking() {
		if(!finished && !done() && shouldRun(getNextBlockPos(false))) {
			return true;
		}
		return false;
	}
	
	public BlockPos getInFront() {
		IBlockState state = this.worldObj.getBlockState(pos);
		EnumFacing facing = state.getValue(BlockQuarry.FACING);
		if(facing.equals(EnumFacing.NORTH)) {
			return pos.south();
		} else if(facing.equals(EnumFacing.SOUTH)) {
			return pos.north();
		} else if(facing.equals(EnumFacing.EAST)) {
			return pos.west();
		} else if(facing.equals(EnumFacing.WEST)) {
			return pos.east();
		}
		return pos.north();
	}
	
	private boolean searched = false;
	public boolean findBounds() {
		BlockPos f = getInFront();
		IBlockState s = this.worldObj.getBlockState(f);
		if(s.getBlock() instanceof BlockMarker) {
			TileEntityMarker m = (TileEntityMarker) this.worldObj.getTileEntity(f);
			BlockPos[] starts = m.getMarkers();
			if(starts[0] == null || starts[1] == null) {
				if(!searched) {
					searched = true;
					m.search();
					return findBounds();
				} else {
					Util.log("Missing quarry markers");
				}
			} else {
				this.startX = starts[0].getX();
				this.startZ = starts[0].getZ();
				this.endX = starts[1].getX();
				this.endZ = starts[1].getZ();
				
				mine(f);
				mine(starts[0]);
				mine(starts[1]);
				
				this.markDirty();
				Util.log("Created quarry bounds: (" + this.startX + ", " + this.startZ + ") to (" + this.endX + ", " + this.endZ + ")");
				searched = false;
				return true;
			}
		}
		return false;
	}
	
	public void scan() {
		long s = System.nanoTime();
		Util.log("Quarry Scan Start: " + s);
		List<BlockPos> bs = new ArrayList<BlockPos>();
		
		if(endX > startX && endZ > startZ) {
			for(int y = this.getPos().getY() + 1; y > 0; y --) {
				for(int x = this.startX; x < this.endX; x ++) {
					for(int z = this.startZ; z < this.endZ; z ++) {
						if(x != this.startX && z != this.startZ) {
							logBlock(bs, x, y, z);
						}
					}
				}
			}
		} else if(endX > startX && endZ < startZ) {
			for(int y = this.getPos().getY() + 1; y > 0; y --) {
				for(int x = this.startX; x < this.endX; x ++) {
					for(int z = this.startZ; z > this.endZ; z --) {
						if(x != this.startX && z != this.startZ && x != this.endX && z != this.endZ) {
							logBlock(bs, x, y, z);
						}
					}
				}
			}
		} else if(endX < startX && endZ < startZ) {
			for(int y = this.getPos().getY() + 1; y > 0; y --) {
				for(int x = this.startX; x > this.endX; x --) {
					for(int z = this.startZ; z > this.endZ; z --) {
						if(x != this.startX && z != this.startZ && x != this.endX && z != this.endZ) {
							logBlock(bs, x, y, z);
						}
					}
				}
			}
		} else if(endX < startX && endZ > startZ) {
			for(int y = this.getPos().getY() + 1; y > 0; y --) {
				for(int x = this.startX; x > this.endX; x --) {
					for(int z = this.startZ; z < this.endZ; z ++) {
						if(x != this.startX && z != this.startZ && x != this.endX && z != this.endZ) {
							logBlock(bs, x, y, z);
						}
					}
				}
			}
		}
		
		blocks.clear();
		Collections.reverse(bs);
		blocks.addAll(bs);
		bs.clear();
		
		long d = System.nanoTime();
		Util.log("Quarry Scan Stop: " + d);
		Util.log("Time Taken: " + (((float) (d - s)) / 1000f) + " microseconds");
	}
	
	private void logBlock(List<BlockPos> bs, int x, int y, int z) {
		BlockPos p = new BlockPos(x, y, z);
		IBlockState state = this.worldObj.getBlockState(p);
		Block b = state.getBlock();
		if(state.equals(null) || b.equals(Blocks.AIR) || b instanceof BlockLiquid || getHardness(p) < 0) { return; }
		bs.add(p);
	}
	
	public float getHardness(BlockPos pos) {
		IBlockState state = this.worldObj.getBlockState(pos);
		Block b = state.getBlock();
		return b.getBlockHardness(state, this.worldObj, pos);
	}
	
	public void update() {
		if(!this.worldObj.isRemote) {
			if(done() && firstTick) { firstTick = false; scan(); }
			
			if(!finished) {
				this.blocksPerRun = 1 + (int) Math.floor((float) Util.getUpgradeCount(this.worldObj, this.pos, _Items.itemUpgradeSpeed) / 1.5f);
				clock --;
				if(clock <= 0) {
					this.clock = this.speed;
					for(int i = 0; i < this.blocksPerRun; i ++) {
						if(done()) { scan(); if(done()) { finished = true; } }
						if(!done() && shouldRun(getNextBlockPos(false))) { run(); }
					}
				}
			}
		}
	}
	
	public boolean done() {
		return blocks.empty();
	}
	
	public BlockPos getNextBlockPos(boolean pop) {
		return (pop) ? blocks.pop() : blocks.peek();
	}
	
	public boolean shouldRun(BlockPos nextBlock) {
		if(this.getEnergyStored() >= this.getRfPrice(nextBlock) && !isInventoryFull() && this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) {
			return true;
		} else if(!isInventoryFull()) {
			return !_Config.quarryRequireRf;
		}
		return false;
	}
	
	public int getRfPrice(BlockPos p) {
		return (int) (Math.ceil(_Config.quarryRfPerOp * getHardness(p)));
	}
	
	public boolean isInventoryFull() {
		for(int i = 0; i < this.inventory.length; i ++) {
			ItemStack is = this.getStackInSlot(i);
			if(is == null) {
				return false;
			}
		}
		return true;
	}
	
	public void run() {
		BlockPos pos = getNextBlockPos(true);
		this.energy -= getRfPrice(pos);
		mine(pos);
	}
	
	public void mine(BlockPos p) {
		for(ItemStack i : getDrops(p)) {
			addStackToInv(i);
		}
		this.worldObj.destroyBlock(p, false);
	}
	
	// -- INVENTORY -- //
	
	public List<ItemStack> getDrops(BlockPos pos) {
		IBlockState state = this.worldObj.getBlockState(pos);
		Block block = state.getBlock();
		return block.getDrops(this.worldObj, pos, state, 0);
	}
	
	public boolean addStackToInv(ItemStack stack) {
		for(int i = 0; i < this.inventory.length; i ++) {
			ItemStack s = this.getStackInSlot(i);
			if(s == null) {
				inventory[i] = stack.copy();
				return true;
			} else {
				if(s.getItem().equals(stack.getItem())) {
					if(s.stackSize + stack.stackSize <= 64) {
						inventory[i].stackSize += stack.stackSize;
						return true;
					} else {
						int diff = stack.stackSize - (64 - this.getStackInSlot(i).stackSize);
						inventory[i].stackSize = 64;
						if(i + 1 < this.inventory.length) {
							if(inventory[i + 1] != null) {
								continue;
							} else {
								inventory[i + 1] = new ItemStack(stack.getItem(), diff);
							}
							return true;
						}
					}
				} else {
					if(i + 1 < this.inventory.length) {
						if(inventory[i + 1] != null) {
							continue;
						} else {
							inventory[i + 1] = stack.copy();
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public String getName() {
		return this.hasCustomName() ? this.customName : this.unloc;
	}
	
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}
	
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	public int getSizeInventory() {
		return 9;
	}
	
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory()) return null;
		
		return this.inventory[index];
	}
	
	public ItemStack decrStackSize(int index, int count) {
		if(this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					//Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index < 0 || index >= this.getSizeInventory()) return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
			
		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
	public void openInventory(EntityPlayer player) {  }
	public void closeInventory(EntityPlayer player) {  }
	
	public boolean isItemValidForSlot(int index, ItemStack stack) { return false; }
	
	public int getField(int id) {
		return 0;
	}

	public void setField(int id, int value) {  }

	public int getFieldCount() {
		return 0;
	}
	
	public void clear() {
		for(int i = 0; i < this.getSizeInventory(); i ++) {
			this.setInventorySlotContents(i, null);
		}
	}
	
	// -- ENERGY -- //

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		if (energy > capacity) {
			energy = capacity;
		}
	}

	public void setEnergyStored(int energy) {
		this.energy = energy;
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public void modifyEnergyStored(int energy) {
		this.energy += energy;
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	public int getEnergyStored() {
		return this.energy;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	public int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate) {
		int energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			this.energy += energyReceived;
		}
		return energyReceived;
	}

	public int getEnergyStored(EnumFacing from) {
		return this.energy;
	}

	public int getMaxEnergyStored(EnumFacing from) {
		return this.capacity;
	}
	
	// -- NBT -- //
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);

		if (this.hasCustomName()) {
			nbt.setString("CustomName", this.getCustomName());
		}
		
		if (this.energy < 0) {
			this.energy = 0;
		}
		nbt.setInteger("Energy", this.energy);
		
		nbt.setInteger("startX", this.startX);
		nbt.setInteger("startZ", this.startZ);
		nbt.setInteger("endX", this.endX);
		nbt.setInteger("endZ", this.endZ);
		
		return super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}
		
		this.energy = nbt.getInteger("Energy");
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		}
		
		this.startX = nbt.getInteger("startX");
		this.startZ = nbt.getInteger("startZ");
		this.endX = nbt.getInteger("endX");
		this.endZ = nbt.getInteger("endZ");
		
		super.readFromNBT(nbt);
	}
	
}