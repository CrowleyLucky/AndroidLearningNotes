package com.lmh.android.test;

import android.test.AndroidTestCase;
import android.util.Log;

public class LogTest extends AndroidTestCase{
	private static final String tag = "LogTest";
	
	public void testErrorLevel() {
		Log.i(tag, "log.info");
		Log.e(tag, "log.error");
		Log.d(tag, "log.debug");
		System.out.println("System.out");
	}
}
