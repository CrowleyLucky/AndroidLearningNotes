package com.lmh.android.junittest;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.ContentObserver;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user");
        this.getContentResolver().registerContentObserver(uri, true, new UserObserver(new Handler()));
    }


    private class UserObserver extends ContentObserver {

		public UserObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			System.out.println("alerting: data has been changed!");
		}
    	
    }
    
}
