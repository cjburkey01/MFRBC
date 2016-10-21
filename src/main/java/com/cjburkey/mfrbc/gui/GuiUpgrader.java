package com.cjburkey.mfrbc.gui;

import java.text.NumberFormat;
import java.util.Locale;
import com.cjburkey.mfrbc.container.ContainerUpgrader;
import com.cjburkey.mfrbc.packet.PacketQuarryToServer;
import com.cjburkey.mfrbc.packet._Packets;
import com.cjburkey.mfrbc.tile.TileEntityUpgrader;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiUpgrader extends GuiContainer {
	
	protected ToolTipManager ttm = new ToolTipManager();
	
	private IInventory playerInv;
	private TileEntityUpgrader te;
	
	public static int energy, maxEnergy;
	public static boolean working;

	public GuiUpgrader(EntityPlayer p, TileEntityUpgrader te) {
		super(new ContainerUpgrader(p.inventory, te));
		
		this.playerInv = p.inventory;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	protected void drawGuiContainerBackgroundLayer(float ticks, int x, int y) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("mfrbc:textures/gui/guiUpgrader.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, Integer.parseInt("404040", 16));
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, Integer.parseInt("404040", 16));
	}
	
}