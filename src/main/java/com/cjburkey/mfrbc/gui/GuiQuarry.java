package com.cjburkey.mfrbc.gui;

import com.cjburkey.mfrbc.container.ContainerQuarry;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiQuarry extends GuiContainer {
	
	private IInventory playerInv;
	private TileEntityQuarry te;

	public GuiQuarry(IInventory inv, TileEntityQuarry te) {
		super(new ContainerQuarry(inv, te));
		
		this.playerInv = inv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	protected void drawGuiContainerBackgroundLayer(float ticks, int x, int y) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("mfrbc:textures/gui/guiQuarry.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, Integer.parseInt("404040", 16));
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, Integer.parseInt("404040", 16));
	}
	
}