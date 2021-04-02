package com.nackjoon.nj.core.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Decrypt {
	public static String decode(String str) {
		try {
			SecureAES secureAES = new SecureAES();
			return secureAES.decrypt( str );
		} catch (InvalidKeyException e ) {
		
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
}
