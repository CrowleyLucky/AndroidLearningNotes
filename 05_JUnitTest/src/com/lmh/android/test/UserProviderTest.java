package com.lmh.android.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

public class UserProviderTest extends AndroidTestCase{

	public void testInsert() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user");
		ContentValues values = new ContentValues();
		values.put("username", "Crowley");
		values.put("password", "155233333333");
		values.put("phone", "13193196401");
		uri = resolver.insert(uri, values);
		System.out.println("insertedUri:" + uri);
	}
	
	public void testDelete() {
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user/50");
		ContentResolver resolver = this.getContext().getContentResolver();
		int affectedRows = resolver.delete(uri, null, null);
		System.out.println("affectedRows:" + affectedRows);
	}
	
	public void testUpdate() {
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user/25");
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("password", "kevin___");
		int affectedRows = resolver.update(uri, values, null, null);
		System.out.println("affectedRows:" + affectedRows);
	}
	
	public void testQuery() {
		//Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user/25");
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user/");
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"id", "password", "username"}, null, null, "id desc");
		while(cursor.moveToNext()) {
			System.out.print("id:" + cursor.getInt(cursor.getColumnIndex("id")));
			System.out.print(",username:" + cursor.getString(cursor.getColumnIndex("username")));
			System.out.println(",password:" + cursor.getString(cursor.getColumnIndex("password")));
		}
	}
	
	public void testNull() {
		String s = null;
		System.out.println(s + "tt");
	}
	
	public void testUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		//ÄãÃÃ°¡¡£¡£¡£²»ÄÜ¼Ó¡°/¡±°¡¡£¡£¡£²Ý²Ý²Ý¡£¡£¡£¡£
		//matcher.addURI("com.lmh.providers.userprovider", "/user", 1);
		matcher.addURI("com.lmh.providers.userprovider", "user", 1);
		Uri uri = Uri.parse("content://com.lmh.providers.userprovider/user");
		int match = matcher.match(uri);
		System.out.println("match:" + match);
	}
	
}
