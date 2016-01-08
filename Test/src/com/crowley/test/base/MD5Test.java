package com.crowley.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {

	
	public static void main(String[] args) {
		System.out.println("--->>>>-->>>> " + md5EncriptWith32("shumanchang").equals("3A65DF88F2CEDD178BA3491AC638D1B8"));
		System.out.println("--->>>>-->>>> " + md5EncriptWith32("wocaonimei!").equals("55D3082F79F87D909407E860B03170DD"));
		System.out.println("--->>>>-->>>> " + md5EncriptWith32("JUnitDoesNotWork!").equals("65222EB9A0FF5C379CD65F2AEABA6B2D"));
		System.out.println("--->>>>-->>>> " + md5EncriptWith32("ButWhy?").equals("111DB993311827C7F13A1FE396760C6D"));
		System.out.println("--->>>>-->>>> " + md5EncriptWith32("Rubbish").equals("2258974E1D370F01555FD4AE05DD8E4D"));
		
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromString("shumanchang").equals("3A65DF88F2CEDD178BA3491AC638D1B8"));
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromString("wocaonimei!").equals("55D3082F79F87D909407E860B03170DD"));
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromString("JUnitDoesNotWork!").equals("65222EB9A0FF5C379CD65F2AEABA6B2D"));
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromString("ButWhy?").equals("111DB993311827C7F13A1FE396760C6D"));
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromString("Rubbish").equals("2258974E1D370F01555FD4AE05DD8E4D"));
		
		
		System.out.println("--->>>>-->>>> " + md5EncriptWith16("JUnitDoesNotWork!").equals("A0FF5C379CD65F2A"));
		
		//Apache Tomcat : 16bb0c896a828d79d31eff397e4bca00 
		System.out.println("--->>>>-->>>> " + getMD5CheckSumFromFile(new File("G:\\software_backups\\apache-tomcat-7.0.57-windows-x64.zip")).equals("16bb0c896a828d79d31eff397e4bca00".toUpperCase()));
		//System.out.println("--->>>>-->>>> " + getMD5CheckSumFromFile(new File("G:\\software_backups\\OperatingSystem\\GHOSTXP.GHO")).equals("16bb0c896a828d79d31eff397e4bca00".toUpperCase()));
		
	}
	
	public static String getMD5CheckSumFromFile (File fileInput) {
		StringBuilder result = new StringBuilder();
		InputStream in = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			in = new FileInputStream(fileInput);
			byte[] buffer = new byte[1024 * 4];
			int readLen = in.read(buffer, 0, buffer.length);
			while(-1 != readLen) {
				md.update(buffer, 0, readLen);
				readLen = in.read(buffer, 0, buffer.length);
			}
	
			byte[] results = md.digest();
				
			for(byte b : results) {
				result.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1).toUpperCase());
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(fileInput.getName() + "->" + result.toString());
		
		return result.toString();
	}
	
	public static String getMD5CheckSumFromString(String input) {
		StringBuilder result = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] results = md.digest();
			
			for(byte b : results) {
				result.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1).toUpperCase());
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//System.out.println(input + "->" + result.toString());
		return result.toString();
	}
	
	@Deprecated
	public static String md5EncriptWith32(String input) {
		StringBuilder result = new StringBuilder();
		char[] hexMap = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			
			byte[] resultBytes = md.digest();
			
			int i = 240;
			int j = 15;
			for(byte b : resultBytes) {
				char high = hexMap[((i & b) >> 4)];
				char low = hexMap[(j & b)];
				
				result.append(high);
				result.append(low);
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(input + "->" + result.toString());
		return result.toString();
	}
	
	public static String md5EncriptWith16(String input) {
		return md5EncriptWith32(input).substring(8, 24);
	}
	
}
