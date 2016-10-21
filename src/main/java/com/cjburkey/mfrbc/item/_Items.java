package com.cjburkey.mfrbc.item;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mfrbc.Info;
import com.cjburkey.mfrbc.Util;
import com.cjburkey.mfrbc.tab._Tabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class _Items {
	
	private static final List<Item> items = new ArrayList<Item>();
	
	public static Item itemRefinedIron;
	public static Item itemRefinedIronNugget;
	public static Item itemWheelWood;
	public static Item itemCornerWood;
	public static Item itemQuarryHead;

	public static Item itemUpgradeSpeed;
	
	public static final void commonPreinit() {
		itemRefinedIron = registerItem("itemRefinedIron", new Item());
		itemRefinedIronNugget = registerItem("itemRefinedIronNugget", new Item());
		itemWheelWood = registerItem("itemWheelWood", new Item());
		itemCornerWood = registerItem("itemCornerWood", new Item());
		itemQuarryHead = registerItem("itemQuarryHead", new Item());
		
		itemUpgradeSpeed = registerItem("itemUpgradeSpeed", new ItemUpgrade("upgradeSpeed"));
	}

	public static final void clientInit() {
		for(Item i : items) {
			ResourceLocation l = i.getRegistryName();
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(l.getResourceDomain() + ":" + l.getResourcePath(), "inventory"));
			Util.log("Registered render: '" + l.getResourcePath() + "'");
		}
	}
	
	// -- Registry -- //
	
	public static final Item registerItem(String n, Item i) {
		ResourceLocation loc = new ResourceLocation(Info.id, n);
		
		i.setUnlocalizedName(n);
		i.setRegistryName(loc);
		i.setCreativeTab(_Tabs.tabItems);
		items.add(i);
		
		GameRegistry.register(i);
		Util.log("Registered item: '" + n + "'");
		return i;
	}
	
}