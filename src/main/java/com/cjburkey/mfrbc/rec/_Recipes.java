package com.cjburkey.mfrbc.rec;

import com.cjburkey.mfrbc.block._Blocks;
import com.cjburkey.mfrbc.item._Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class _Recipes {
	
	public static final void commonInit() {
		// Items
		addFurnace(new ItemStack(_Items.itemRefinedIron, 1), new ItemStack(Items.IRON_INGOT));

		addFull(new ItemStack(_Items.itemRefinedIron, 1), new ItemStack(_Items.itemRefinedIronNugget, 1));
		addShapeless(new ItemStack(_Items.itemRefinedIron, 9), new ItemStack(_Blocks.blockRefinedIronBlock, 1));
		addShapeless(new ItemStack(_Items.itemRefinedIronNugget, 9), new ItemStack(_Items.itemRefinedIron, 1));
		add(new ItemStack(_Items.itemWheelWood, 1), new Object[] { " x ", "xyx", " x ", 'x', "plankWood", 'y', "stickWood" });
		
		// Blocks
		addFull(new ItemStack(_Blocks.blockRefinedIronBlock, 1), new ItemStack(_Items.itemRefinedIron, 1));
		add(new ItemStack(_Blocks.blockMarker, 1), new Object[] { "x", "y", 'x', "dyeBlue", 'y', new ItemStack(Blocks.TORCH, 1) });
		add(new ItemStack(_Blocks.blockQuarry, 1), new Object[] { "xyx", "zaz", "xbx",
				'x', _Items.itemCornerWood, 'y', _Items.itemWheelWood, 'z', Blocks.STONE, 'a', "blockSteel", 'b', _Items.itemQuarryHead });
	}
	
	// -- Registry -- //
	
	private static final void add(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapedOreRecipe(out, rec));
	}
	
	private static final void addShapeless(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(out, rec));
	}
	
	private static final void addFull(ItemStack out, ItemStack in) {
		add(out, new Object[] { "xxx", "xxx", "xxx", 'x', in.getItem() });
	}
	
	private static final void addFurnace(ItemStack out, ItemStack in) {
		GameRegistry.addSmelting(in, out, 1.0f);
	}
	
	private static final void addFurnaceXP(ItemStack out, ItemStack in, float xp) {
		GameRegistry.addSmelting(in, out, xp);
	}
	
}