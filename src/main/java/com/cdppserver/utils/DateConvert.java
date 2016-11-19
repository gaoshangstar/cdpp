package com.cdppserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cdppserver.constant.Constants;

public class DateConvert {
	
	public static String convert2Date(long date){
		
		SimpleDateFormat myFmt=new SimpleDateFormat(Constants.DATETIME_FORMAT);
		return myFmt.format(new Date(date));
		
	}

}
