package com.crowley.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

public class ContentProviderTest extends AndroidTestCase {

	public void testInsert() {
		Uri uri = Uri.parse("content://com.crowley.personprovider/person");
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "smc-lucky");
		values.put("number", "18566291285");
		values.put("salary", "10000");
		resolver.insert(uri, values);
	}
	
}
