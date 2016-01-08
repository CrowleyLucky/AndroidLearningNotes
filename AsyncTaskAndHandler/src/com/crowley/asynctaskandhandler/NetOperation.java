package com.crowley.asynctaskandhandler;

import java.util.concurrent.TimeUnit;

public class NetOperation {

	public static void operate() {
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
