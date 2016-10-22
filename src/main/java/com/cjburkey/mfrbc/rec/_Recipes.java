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

		addFull(new ItemStack(_Items.itemRefinedIron, 1), _Items.itemRefinedIronNugget);
		addShapeless(new ItemStack(_Items.itemRefinedIron, 9), _Blocks.blockRefinedIronBlock);
		addShapeless(new ItemStack(_Items.itemRefinedIronNugget, 9), "ingotSteel");
		add(new ItemStack(_Items.itemWheelWood, 1), new Object[] { " x ", "xyx", " x ", 'x', "plankWood", 'y', "stickWood" });
		add(new ItemStack(_Items.itemQuarryHead, 1), new Object[] { "xyx", "xzx", "xax", 'x', "gemDiamond", 'y', "ingotSteel", 'z', "plankWood", 'a', "dustRedstone" });
		add(new ItemStack(_Items.itemCornerWood, 1), new Object[] { "xx", " x", 'x', Blocks.WOODEN_BUTTON });
		add(new ItemStack(_Items.itemUpgradeBase, 1), new Object[] { "xxx", "yzy", "aba", 'x', "dustRedstone", 'y', "gemDiamond", 'z', "blockSteel", 'a', "plankWood", 'b', "ingotSteel" });
		add(new ItemStack(_Items.itemUpgradeSpeed, 1), new Object[] { "xxx", "yzy", "aba",
				'x', "dustRedstone", 'y', "gemDiamond", 'z', _Items.itemUpgradeBase, 'a', Items.SUGAR, 'b', "ingotSteel" });
		
		// Blocks
		addFull(new ItemStack(_Blocks.blockRefinedIronBlock, 1), new ItemStack(_Items.itemRefinedIron, 1));
		add(new ItemStack(_Blocks.blockMarker, 1), new Object[] { "x", "y", "x", 'x', "dyeBlue", 'y', new ItemStack(Blocks.TORCH, 1) });
		add(new ItemStack(_Blocks.blockPumpPipe, 4), new Object[] { "x", "x", "x", 'x', "ingotSteel" });
		add(new ItemStack(_Blocks.blockUpgrader, 1), new Object[] { " x ", "xyx", " x ", 'x', _Items.itemUpgradeBase, 'y', _Blocks.blockRefinedIronBlock });
		add(new ItemStack(_Blocks.blockQuarry, 1), new Object[] { "xyx", "zaz", "xbx",
				'x', _Items.itemCornerWood, 'y', _Items.itemWheelWood, 'z', "stone", 'a', "blockSteel", 'b', _Items.itemQuarryHead });
		add(new ItemStack(_Blocks.blockPump, 1), new Object[] { "xyx", "aba", "xzx",
				'x',_Items.itemCornerWood, 'y', _Items.itemWheelWood, 'z', _Blocks.blockPumpPipe, 'a', "cobblestone", 'b', "blockSteel" });
	}
	
	// -- Registry -- //
	
	private static final void add(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapedOreRecipe(out, rec));
	}
	
	private static final void addShapeless(ItemStack out, Object... rec) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(out, rec));
	}
	
	private static final void addFull(ItemStack out, Object in) {
		add(out, new Object[] { "xxx", "xxx", "xxx", 'x', in });
	}
	
	private static final void addFurnace(ItemStack out, ItemStack in) {
		GameRegistry.addSmelting(in, out, 1.0f);
	}
	
	private static final void addFurnaceXP(ItemStack out, ItemStack in, float xp) {
		GameRegistry.addSmelting(in, out, xp);
	}
	
}