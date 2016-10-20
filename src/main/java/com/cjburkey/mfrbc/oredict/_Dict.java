package com.cjburkey.mfrbc.oredict;

import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.item._Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class _Dict {
	
	public static final void commonPreinit() {
		// Items
		add(_Items.itemRefinedIron, "ingotSteel");
		
		// Blocks
		add(_Blocks.blockRefinedIronBlock, "blockSteel");
	}
	
	// -- Registry -- //
	
	private static final void add(Object ore, String name) {
		if(ore instanceof Item) {
			OreDictionary.registerOre(name, (Item) ore);
		} else if(ore instanceof Block) {
			OreDictionary.registerOre(name, (Block) ore);
		} else {
			Util.log("Unknown ore: '" + ore + "' trying to register as: '" + name + "'");
		}
	}
	
}