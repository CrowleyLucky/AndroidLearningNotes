package com.lmh.android.mobilenumberquery;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MobileService {

	/**
	 * query telephone numbers' belonging to
	 * @param tel the target telephone number
	 * @return the belonging-to of the target telephone number 
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public static String getMobileBelongingTo(String tel) throws MalformedURLException, Exception {
		String urlPath = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
		InputStream soapXml = MobileService.class.getClassLoader().getResourceAsStream("soap12.xml");
		String soap = new String(ReadService.readFromStream(soapXml), "UTF-8");
		soap = soap.replaceAll("\\$mobile", tel);
		byte[] soapBytes = soap.getBytes();
		HttpURLConnection conn = (HttpURLConnection) new URL(urlPath).openConnection();
		conn.setRequestMethod("POST");
		conn.setReadTimeout(3000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(soapBytes.length));
		OutputStream out = conn.getOutputStream();
		out.write(soapBytes);
		out.flush();
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return parseXML(conn.getInputStream());
		}
		return null;
	}
/*
 <?xml version="1.0" encoding="utf-8"?>
<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
  <soap12:Body>
    <getMobileCodeInfoResponse xmlns="http://WebXml.com.cn/">
      <getMobileCodeInfoResult>string</getMobileCodeInfoResult>
    </getMobileCodeInfoResponse>
  </soap12:Body>
</soap12:Envelope>
 
 */
	private static String parseXML(InputStream inputStream) throws Exception {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if("getMobileCodeInfoResult".equals(parser.getName())) {
					return parser.nextText();
				}
				break;
			}
			event = parser.next();
		}
		
		
		return null;
	}
	
}
