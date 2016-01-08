package com.crowley.test.concurrency;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class Test extends TestCase{

	public static void main(String[]args) {
		Thread t = new Thread(new ADeamon());
		//t.setDaemon(true);
		t.start();
		//t.interrupt();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


class ADeamon implements Runnable {

	@Override
	public void run() {
		System.out.println("run()...");
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("passing here...");
			System.out.println("passing here...");
			System.out.println("passing here...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally...");
		}
		System.out.println("after finally...");
	}

}