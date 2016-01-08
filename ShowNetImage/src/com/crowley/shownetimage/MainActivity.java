package com.crowley.shownetimage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView iv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.image);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
		
		
		
		try {
			showImage();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showImage() throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection )new URL("http://192.168.1.104:8080/AndroidService/joke.gif").openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		Log.d("Crowley", "HTTP CODE:" + conn.getResponseCode());
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while(-1 != (len = in.read(buffer))) {
				out.write(buffer, 0, len);
			}
			byte[] bytes = out.toByteArray();
			Bitmap bm = BitmapFactory.decodeByteArray(out.toByteArray(), 0, bytes.length);
			Log.d("Crowley", "bm:" + bm);
			Log.d("Crowley", "iv:" + iv);
			iv.setImageBitmap(bm);
		}
		
		
		
	}
	
	
	
	
}
