package com.nackjoon.nj.core.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encrypt {
	
	
	
	//암호화
	public static String encode(String str) {
		try {
			SecureAES secureAES = new SecureAES();
			return secureAES.encrypt( str );
		} catch (InvalidKeyException e) {
			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			
			e.printStackTrace();
		} catch (BadPaddingException e) {
			
			e.printStackTrace();
		}

		return "";
	}
	
	//SHA-256 단방향 암호화
	public static String encodeSHA256(String str) {
		byte[] passwordBytes = str.getBytes();
		MessageDigest sha256 = null;
		byte[] resultBytes = null;
		StringBuilder resultBuffer = new StringBuilder();

		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			sha256.update(passwordBytes);
			resultBytes = sha256.digest();

			for (int i = 0; i < resultBytes.length; i++) {
				resultBuffer.append(Integer.toString((resultBytes[i] & 0xf0) >> 4, 16));
				resultBuffer.append(Integer.toString(resultBytes[i] & 0x0f, 16));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return resultBuffer.toString();
	}
	
	//MD5 단방향 암호화
	public static String encodeMD5(String str) {
		StringBuffer md5 = new StringBuffer();

		try {
			byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());

			for (int i = 0; i < digest.length; i++) {
				md5.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
				md5.append(Integer.toString(digest[i] & 0x0f, 16));
			}
		} catch (NoSuchAlgorithmException ne) {
			ne.printStackTrace();
		}

		return md5.toString();
	}	
}
