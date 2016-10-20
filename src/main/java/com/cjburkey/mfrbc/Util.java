package com.cjburkey.mfrbc;

import org.apache.logging.log4j.LogManager;

public class Util {
	
	public static final void log(Object msg) { LogManager.getLogger("MFRBC").info(msg); }
	
}