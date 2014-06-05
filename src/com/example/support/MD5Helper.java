package com.example.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

	public static String getMD5String(String passwordStr){
		
		byte byteData[] = null;
	    MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		    md.update(passwordStr.getBytes());
		    byteData = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < byteData.length; i++)
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    String md5Pass = sb.toString();
	    
	    return md5Pass;
	}
	
}
