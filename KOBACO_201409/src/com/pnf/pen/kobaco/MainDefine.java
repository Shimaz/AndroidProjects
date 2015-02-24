package com.pnf.pen.kobaco;

import java.util.Date;

import android.content.Context;

import com.pnf.bt.lib.PNFPenController;

public class MainDefine {
	public static PNFPenController penController = null;
	
	public static int iDisGetWidth = 0;
	public static int iDisGetHeight = 0;
	
	public static long GetCurrentSec()
	{
		Date date = new Date();
		long retVal = date.getTime();
		retVal = (retVal/1000);
		return retVal;
	}
	
	public static String getLangString(Context con ,int id){
		if(con == null) return "";
		return con.getResources().getString(id);
	}
}
