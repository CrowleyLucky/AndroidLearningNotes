package com.lmh.android.contentprovidertest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void insertData (View v) {
		ContentResolver resolver = this.getContentResolver();
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user");
		ContentValues values = new ContentValues();
		values.put("username", "crowley~");
		System.out.println(resolver.insert(uri, values));
	}
	
}
