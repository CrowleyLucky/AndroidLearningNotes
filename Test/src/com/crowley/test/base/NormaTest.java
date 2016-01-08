package com.crowley.test.base;

import java.io.File;

import junit.framework.TestCase;


public class NormaTest extends TestCase{

	public void testByte2Char() {
		File f = new File("bin", "com\\crowley");
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getName());
		System.out.println(f.exists());
		System.out.println(new File(f.getAbsolutePath()).getParent());
		System.out.println("abc".substring(1));
		
		File ff = new File("c:\\Windows");
		System.out.println(ff.getAbsolutePath().length());
		
	}
	
	
	
	
	
}
