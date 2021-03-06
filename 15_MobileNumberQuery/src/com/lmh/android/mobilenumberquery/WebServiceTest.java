package com.lmh.android.mobilenumberquery;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.test.AndroidTestCase;

public class WebServiceTest extends AndroidTestCase{

		public void testUploadXml() throws Exception {
			String urlPath = "http://172.23.8.65:80/AndroidService/servlet/UploadXmlFile";
			InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream("person.xml");
			byte[] buffer = ReadService.readFromStream(xmlStream);
			
			HttpURLConnection connection = (HttpURLConnection) new URL(urlPath).openConnection();
			connection.setReadTimeout(5000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/xml");
			OutputStream out = connection.getOutputStream();
			out.write(buffer);
			out.flush();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("upload successfully!");
			} else {
				System.out.println("upload failed!");
			}
		}
	
}
