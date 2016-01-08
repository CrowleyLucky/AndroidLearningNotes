package com.crowley.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class SocketServerTest {

	public static void main(String[]args) {
		new Server().start();
	}
	
}


class Server extends Thread {
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(SocketTest.PORT);
			Socket socket = server.accept();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(reader.readLine() + Thread.currentThread());
			
			
			TimeUnit.SECONDS.sleep(8);
			reader.close();
			socket.close();
			server.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("server quit ~" + Thread.currentThread());
	}
	
}