package com.lmh.android.sqlite.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lmh.android.sqlite.domain.User;

public class OtherUserService {
	private DBHelper helper;
	
	public OtherUserService(Context context) {
		helper = new DBHelper(context);
	}
	
	public void add(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
	//	db.execSQL("insert into user(username, password, phone) values(?,?,?)", 
		//		new Object[]{user.getUsername(), user.getPassword(), user.getPhone()});
		ContentValues values = new ContentValues(3);
		values.put("username", user.getUsername());
		values.put("password", user.getPassword());
		values.put("phone", user.getPhone());
		db.insert("user", null, values);
	}
	
	public void delete(Integer id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		//db.execSQL("delete from user where id=?", 
			//	new Object[]{id});
		db.delete("user", "id=?", new String[]{String.valueOf(id)});
	}
	
	public void update(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues(3);
		values.put("username", user.getUsername());
		values.put("password", user.getPassword());
		values.put("phone", user.getPhone());
		db.update("user", values, "id=?", new String[]{user.getId().toString()});
		//db.execSQL("update user set username=?, password=?, phone=? where id=?", 
		//		new Object[]{user.getUsername(), user.getPassword(), user.getPhone(), user.getId()});
	}

	public User find(Integer id) {
		User user = new User();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("user", new String[]{"id", "username", "password", "phone"}, "id=?", new String[]{id.toString()}, null, null, null);
		//Cursor cursor = db.rawQuery("select * from user where id=?", new String[]{id.toString()});
		if(cursor.moveToFirst()) {
			user.setId(id);
			user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
		}
		cursor.close();
		return user;
	}
	
	public List<User> getScrollData(int offset, int maxResult) {
		List<User> users = new ArrayList<User>();
		User user = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("user", null, null, null, null, null, null, offset + "," + maxResult);
		//Cursor cursor = db.rawQuery("select * from user limit ?,?", new String[]{offset + "", maxResult + ""});
		while(cursor.moveToNext()) {
			user = new User();
			user.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
			user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			users.add(user);
		}
		cursor.close();
		return users;
	}
	
	public long getCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("user", new String[]{"count(*) as total"}, null, null, null, null, null);
		//Cursor cursor = db.rawQuery("select count(*) as total from user", null);
		cursor.moveToFirst();
		long count = cursor.getInt(cursor.getColumnIndex("total"));
		cursor.close();
		return count;
	}
	
}
