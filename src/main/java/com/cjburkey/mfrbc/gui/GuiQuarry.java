package com.cjburkey.mfrbc.gui;

import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import com.cjburkey.mfrbc.container.ContainerQuarry;
import com.cjburkey.mfrbc.gui.ToolTipManager.ToolTipRenderer;
import com.cjburkey.mfrbc.packet.PacketQuarryToServer;
import com.cjburkey.mfrbc.packet._Packets;
import com.cjburkey.mfrbc.tile.TileEntityQuarry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiQuarry extends GuiContainer implements ToolTipRenderer {
	
	protected ToolTipManager ttm = new ToolTipManager();
	protected NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
	
	private IInventory playerInv;
	private TileEntityQuarry te;
	
	public static int energy, maxEnergy;
	public static boolean working;

	public GuiQuarry(EntityPlayer p, TileEntityQuarry te) {
		super(new ContainerQuarry(p.inventory, te));
		
		this.playerInv = p.inventory;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		ttm.drawTooltips(this, mouseX, mouseY);
	}
	
	protected void drawGuiContainerBackgroundLayer(float ticks, int x, int y) {
		_Packets.network.sendToServer(new PacketQuarryToServer(te.getPos()));
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("mfrbc:textures/gui/guiQuarry.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, Integer.parseInt("404040", 16));
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, Integer.parseInt("404040", 16));
		drawEnergyBar();
	}
	
	private void drawEnergyBar() {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("mfrbc:textures/gui/guiBar.png"));
		float percent = ((float) energy / (float) maxEnergy);
		int x = 23, y = 16, w = 20, h = 52;
		
		this.drawTexturedModalRect(x, y - (int) (percent * (float) h) + h, 0, 0, w, (int) (percent * (float) h + 1));
		
		addToolTips(x, y, w, h);
	}
	
	private void addToolTips(int x, int y, int w, int h) {
		String e = nf.format(this.energy);
		String m = nf.format(this.maxEnergy);
		ttm.clear();
		ttm.addToolTip(new GuiToolTip(new Rectangle(x, y, w, h), "Energy", e + "RF / " + m + "RF"));
	}

	public int getGuiLeft() {
		return this.guiLeft;
	}

	public int getGuiTop() {
		return this.guiTop;
	}

	public int getXSize() {
		return this.xSize;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}

	public void drawTextS(List<String> par1List, int par2, int par3, FontRenderer font) {
		this.drawHoveringText(par1List, par2, par3, font);
	}
	
}