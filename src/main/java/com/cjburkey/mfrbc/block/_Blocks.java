package com.cjburkey.mfrbc.block;

import java.util.HashMap;
import com.cjburkey.mfrbc.Info;
import com.cjburkey.mfrbc.MFRBC;
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
	
	public static final void commonPreinit() {
		blockRefinedIronBlock = registerBlock("blockRefinedIronBlock", new Block(Material.IRON).setHardness(1.0f));
		blockQuarry = registerBlock("blockQuarry", new BlockQuarry());
	}
	
	// -- Registry -- //
	
	private static final Block registerBlock(String n, Block b) {
		ResourceLocation loc = new ResourceLocation(Info.id, n);
		
		b.setUnlocalizedName(n);
		b.setRegistryName(loc);
		b.setCreativeTab(_Tabs.tabBlocks);
		
		Item item = _Items.registerItem(n, new ItemBlock(b));
		blocks.put(b, item);
		
		GameRegistry.register(b);
		MFRBC.log("Registered block: '" + n + "'");
		return b;
	}
	
}