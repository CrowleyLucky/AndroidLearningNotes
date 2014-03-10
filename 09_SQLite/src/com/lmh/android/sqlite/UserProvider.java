package com.lmh.android.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.lmh.android.sqlite.service.DBHelper;

public class UserProvider extends ContentProvider {
	private DBHelper helper; 
	private static final int USERS = 1;
	private static final int USER = 2;
	private static final UriMatcher MATCHER;
	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI("com.lmh.providers.userprovider", "user", USERS);
		MATCHER.addURI("com.lmh.providers.userprovider", "user/#", USER);
	}
	
	@Override
	public boolean onCreate() {
		helper = new DBHelper(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = null;
		switch(MATCHER.match(uri)) {
		case USER:
			long id = ContentUris.parseId(uri);
			String where = " id=" + id;
			if(selection != null && !selection.equals(""))
				where += " and " + selection;
			cursor = db.query("user", projection, where, selectionArgs, null, null, "id desc");
			break;
		case USERS:
			cursor = db.query("user", projection, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("There is no uri named " + uri);
		}
		return cursor;
	}

	

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int match = MATCHER.match(uri);
		SQLiteDatabase db = helper.getReadableDatabase();
		switch(match) {
		case USERS:
			long id = db.insert("user", null, values);
			Uri insertedUri = ContentUris.withAppendedId(uri, id);
			//发出数据变化通知
			this.getContext().getContentResolver().notifyChange(uri, null);
			return insertedUri;
		default: 
			throw new IllegalArgumentException("There is no uri named " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int match = MATCHER.match(uri);
		SQLiteDatabase db = helper.getWritableDatabase();
		int affectedRows = 0;
		switch(match) {
		case USER:
			long id = ContentUris.parseId(uri);
			String where = "id=" + id;
			if(selection != null && !selection.equals(""))
				where += " and " + selection;
			affectedRows = db.delete("user", where, selectionArgs);
			break;
		case USERS:
			affectedRows = db.delete("user", null, null);
			break;
		default:
			throw new IllegalArgumentException("There is no uri named " + uri);
		}
		
		return affectedRows;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int match = MATCHER.match(uri);
		int affectedRows = 0;
		SQLiteDatabase db = helper.getWritableDatabase();
		switch(match) {
		case USER:
			long id = ContentUris.parseId(uri);
			String where = " id=" + id;
			if(selection != null && !selection.equals(""))
				where += " and " + selection;
			affectedRows = db.update("user", values, where, selectionArgs);
			break;
		case USERS:
			affectedRows = db.update("user", values, null, null);
			break;
		default:
			throw new IllegalArgumentException("There is no uri named " + uri);
		}
		
		return affectedRows;
	}
	
	@Override
	public String getType(Uri uri) {
		int match = MATCHER.match(uri);
		switch(match) {
		case USER:
			return "vnd.android.cursor.dir/users";
			
		case USERS:
			return "vnd.android.cursor.item/user";
		default:
			return null;
		}
	}

}
