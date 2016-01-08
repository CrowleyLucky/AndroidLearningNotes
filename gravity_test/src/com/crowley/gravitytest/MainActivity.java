package com.crowley.gravitytest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		try {
			HttpURLConnection conn = (HttpURLConnection)new URL("").openConnection();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
