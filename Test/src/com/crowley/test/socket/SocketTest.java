package com.crowley.test.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketTest {
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 6666;

	public static void main(String[]args) throws InterruptedException {
		new Client().start();
	}
	
} 




class Client extends Thread {

	@Override
	public void run() {
		try {
			Socket client = new Socket(SocketTest.HOST, SocketTest.PORT);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write("Hello JAVA ~\n");
			writer.flush();

			
			
			TimeUnit.SECONDS.sleep(8);
			writer.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("client quit ~" + Thread.currentThread());
		
	}
	
}
