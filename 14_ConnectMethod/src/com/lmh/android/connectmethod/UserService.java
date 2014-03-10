package com.lmh.android.connectmethod;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class UserService {
	private static String urlPath = "http://172.23.8.65:8080/AndroidService/servlet/MethodTest";
	
	public static boolean login(String usernameStr, String passwordStr) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", usernameStr);
		data.put("password", passwordStr);	
		//return sendWithGetMethod(data);
		//return sendWithPostMethod(data);
		return sendWithHttpClient(data);
	}

	//使用android继承的HttpClient包访问网络，性能较低。
	//当需要使用到session，和访问https资源时，会加快开发进度。
	private static boolean sendWithHttpClient(Map<String, String> datas) throws Exception {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
		for(Map.Entry<String, String> entity : datas.entrySet()) {
			NameValuePair pair = new BasicNameValuePair( entity.getKey(), entity.getValue());
			parameters.add(pair);
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
		HttpPost httpPost = new HttpPost(urlPath);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		
		return false;
	}

	private static boolean sendWithGetMethod(Map<String, String> datas) throws Exception {
		StringBuilder sb = new StringBuilder(urlPath);
		sb.append("?");
		for(Map.Entry<String, String> data : datas.entrySet()) {
			sb.append(data.getKey() + "=" + URLEncoder.encode(data.getValue(), "UTF-8"));
			sb.append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println("doGet URL:" + sb.toString());
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestMethod("GET");
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		return false;
	}
	
	private static boolean sendWithPostMethod(Map<String, String> datas) throws Exception {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> data : datas.entrySet()) {
			sb.append(data.getKey() + "=" + URLEncoder.encode(data.getValue(), "UTF-8"));
			sb.append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println("doPost URL:" + urlPath);
		System.out.println("entity:" + sb.toString());
		byte[] entity = sb.toString().getBytes();
		HttpURLConnection connection = (HttpURLConnection)new URL(urlPath).openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", "" + entity.length);
		OutputStream out = connection.getOutputStream();
		out.write(entity);
		out.flush();
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		return false;
	}
}



