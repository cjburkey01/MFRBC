package com.cjburkey.mfrbc.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiHelper {
	
	public static final void drawProgressBar(Gui self, int startX, int startY, int endX, int endY, float val, float max, String texture) {
		Minecraft mc = Minecraft.getMinecraft();
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(new ResourceLocation(texture));
		float percent = val / max;
		int w = endX - startX + 1, h = endY - startY;
		
		int fh = (int) (percent * (float) h) + 1;
		int fy = startY - (int) (percent * (float) h) + h;
		self.drawTexturedModalRect(startX, fy, 0, h - fh + 1, w, (percent == 0) ? 0 : fh);
	}
	
	public static final void drawProgressBar(Gui self, int startX, int startY, int endX, int endY, float val, float max) {
		drawProgressBar(self, startX, startY, endX, endY, val, max, "mfrbc:textures/gui/guiBar.png");
	}
	
}