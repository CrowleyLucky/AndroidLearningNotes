package com.crowley.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.crowley.sqlite.MainActivity;

public class PersonProvider extends ContentProvider{
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS = 1;
	
	static {
		MATCHER.addURI("com.crowley.personprovider", "person", PERSONS);
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//Log.d(MainActivity.TAG, "uri:" + uri + "," + MATCHER.match(uri));
		switch (MATCHER.match(uri)) {
		case PERSONS:
			Log.d(MainActivity.TAG, "uri:" + uri);
			Log.d(MainActivity.TAG, "contentValues:" + values);
			this.getContext().getContentResolver().notifyChange(uri, null);
			break;

		default:
			throw new IllegalArgumentException("Unknown Uri:" + uri);
		}
		
			return ContentUris.withAppendedId(uri, 3);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
