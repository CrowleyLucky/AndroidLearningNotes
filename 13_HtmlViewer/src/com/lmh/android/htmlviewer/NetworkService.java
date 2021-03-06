package com.lmh.android.htmlviewer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkService {

	public static byte[] readFromPath(String path) throws Exception{
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestMethod("GET");
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream in = connection.getInputStream();
			return readFromStream(in);
		}
		return null;
	}
	
	public static byte[] readFromStream(InputStream in) throws IOException {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
}
