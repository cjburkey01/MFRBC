package com.cjburkey.mfrbc.rec;

import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.item._Items;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class _Recipes {
	
	public static final void commonInit() {
		// Items
		addFurnace(new ItemStack(_Items.itemRefinedIron, 1), new ItemStack(Items.IRON_INGOT));
		
		add(new ItemStack(_Items.itemRefinedIron, 1), new Object[] { "xxx", "xxx", "xxx", Character.valueOf('x'), _Items.itemRefinedIronNugget });
		addShapeless(new ItemStack(_Items.itemRefinedIron, 9), new ItemStack(_Blocks.blockRefinedIronBlock, 1));
		addShapeless(new ItemStack(_Items.itemRefinedIronNugget, 9), new ItemStack(_Items.itemRefinedIron, 1));
		
		// Blocks
		add(new ItemStack(_Blocks.blockRefinedIronBlock, 1), new Object[] { "xxx", "xxx", "xxx", Character.valueOf('x'), _Items.itemRefinedIron });
	}
	
	// -- Registry -- //
	
	private static final void add(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapedOreRecipe(out, rec));
	}
	
	private static final void addShapeless(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(out, rec));
	}
	
	private static final void addFurnace(ItemStack out, ItemStack in) {
		GameRegistry.addSmelting(in, out, 1.0f);
	}
	
	private static final void addFurnaceXP(ItemStack out, ItemStack in, float xp) {
		GameRegistry.addSmelting(in, out, xp);
	}
	
}