package com.cjburkey.mfrbc.tab;

import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.item._Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class _Tabs {
	
	public static CreativeTabs tabItems;
	public static CreativeTabs tabBlocks;
	
	public static final void commonPreinit() {
		tabItems = new CreativeTabs("mfrbcTabItems") { public Item getTabIconItem() { return _Items.itemRefinedIron; } };
		tabBlocks = new CreativeTabs("mfrbcTabBlocks") { public Item getTabIconItem() { return Item.getItemFromBlock(_Blocks.blockRefinedIronBlock); } };
	}
	
}