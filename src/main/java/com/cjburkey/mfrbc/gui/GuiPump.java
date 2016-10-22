package com.cjburkey.mfrbc.gui;

import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import com.cjburkey.mfrbc.container.ContainerPump;
import com.cjburkey.mfrbc.gui.ToolTipManager.ToolTipRenderer;
import com.cjburkey.mfrbc.packet.PacketPumpToServer;
import com.cjburkey.mfrbc.packet.PacketQuarryToServer;
import com.cjburkey.mfrbc.packet._Packets;
import com.cjburkey.mfrbc.tile.TileEntityPump;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiPump extends GuiContainer implements ToolTipRenderer {
	
	protected ToolTipManager ttm = new ToolTipManager();
	protected NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
	
	private IInventory playerInv;
	private TileEntityPump te;
	
	public static int energy, maxEnergy, fluid, maxFluid;
	public static String fluidName;

	public GuiPump(EntityPlayer p, TileEntityPump te) {
		super(new ContainerPump(p.inventory, te));
		
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
		_Packets.getNetwork().sendToServer(new PacketPumpToServer(te.getPos()));
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("mfrbc:textures/gui/guiPump.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, Integer.parseInt("404040", 16));
		
		ttm.clear();
		drawEnergyBar(23, 16, 42, 150);
		drawFluidBar(133, 16, 152, 150);
	}
	
	private void drawEnergyBar(int startX, int startY, int endX, int endY) {
		GuiHelper.drawProgressBar(this, startX, startY, endX, endY, this.energy, this.maxEnergy);
		
		String e = nf.format(this.energy);
		String m = nf.format(this.maxEnergy);
		int w = endX - startX + 1, h = endY - startY;
		ttm.addToolTip(new GuiToolTip(new Rectangle(startX, startY, w, h), "Energy", e + "RF / " + m + "RF"));
	}
	
	private void drawFluidBar(int startX, int startY, int endX, int endY) {
		GuiHelper.drawProgressBar(this, startX, startY, endX, endY, this.fluid, this.maxFluid, "mfrbc:textures/gui/guiFluidBar.png");
		
		String i = nf.format(this.fluid);
		String o = nf.format(this.maxFluid);
		String n = (fluidName == null || fluidName.trim().equals("null") || fluidName.isEmpty()) ? I18n.translateToLocal("noFluid") : fluidName;
		int w = endX - startX + 1, h = endY - startY;
		ttm.addToolTip(new GuiToolTip(new Rectangle(startX, startY, w, h), n, i + "mB / " + o + "mB"));
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