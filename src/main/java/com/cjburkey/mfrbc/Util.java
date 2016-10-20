package com.cjburkey.mfrbc;

import org.apache.logging.log4j.LogManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextComponentString;

public class Util {
	
	public static final void log(Object msg) { LogManager.getLogger("MFRBC").info(msg); }
	public static final void chat(EntityLivingBase ply, Object msg) { ply.addChatMessage(new TextComponentString(msg + "")); }
	
}