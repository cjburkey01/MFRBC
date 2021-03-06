package com.cjburkey.mfrbc.block;

import java.util.HashMap;
import com.cjburkey.mfrbc.Info;
import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.item._Items;
import com.cjburkey.mfrbc.tab._Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class _Blocks {
	
	private static final HashMap<Block, Item> blocks = new HashMap<Block, Item>();
	
	public static Block blockRefinedIronBlock;
	public static Block blockQuarry;
	public static Block blockMarker;
	public static Block blockUpgrader;
	public static Block blockPump;
	public static Block blockPumpPipe;
	
	public static final void commonPreinit() {
		blockRefinedIronBlock = registerBlock("blockRefinedIronBlock", new Block(Material.IRON).setHardness(1.0f), false);
		blockQuarry = registerBlock("blockQuarry", new BlockQuarry(), false);
		blockMarker = registerBlock("blockMarker", new BlockMarker(), false);
		blockUpgrader = registerBlock("blockUpgrader", new BlockUpgrader(), false);
		blockPump = registerBlock("blockPump", new BlockPump(), false);
		blockPumpPipe = registerBlock("blockPumpPipe", new BlockPumpPipe(), false);
	}
	
	// -- Registry -- //
	
	private static final Block registerBlock(String n, Block b, boolean hide) {
		ResourceLocation loc = new ResourceLocation(Info.id, n);
		
		b.setUnlocalizedName(n);
		b.setRegistryName(loc);
		if(!hide) b.setCreativeTab(_Tabs.tabBlocks);
		
		Item item = _Items.registerItem(n, new ItemBlock(b));
		blocks.put(b, item);
		
		GameRegistry.register(b);
		Util.log("Registered block: '" + n + "'");
		return b;
	}
	
}