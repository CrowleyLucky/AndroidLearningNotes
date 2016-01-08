package com.crowley.simulator;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import junit.framework.TestCase;

public class Main extends TestCase{

	public void getIPv4() throws UnknownHostException {
		String ip = null;
		BufferedReader br = null;
		try {
			Process process = Runtime.getRuntime().exec("netstat -rn");
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.contains(" 0.0.0.0 ") || line.contains(" default ")) {
					System.out.println(line.trim());
					ip = (line.trim().split("\\s+"))[3];
					System.out.println(ip);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testGetMouseLocation() throws Exception {
		Point p = new Point();
		System.out.println("MouseButtonNumber:" + MouseInfo.getNumberOfButtons());
		p = MouseInfo.getPointerInfo().getLocation();
		System.out.println("MouseLocation:x=" + p.x + ", y=" + p.y);
		for(int i = 0; i < 1000; i++) {
			Robot robot = new Robot();
			robot.mouseMove(i, i);
			Thread.sleep(8);
		}
	}
	
	public void testRobot() throws Exception {
		Robot robot = new Robot();
		Thread.sleep(2000);
		//robot.mouseMove(10, 10);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		/*robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);*/
		
	}
}
