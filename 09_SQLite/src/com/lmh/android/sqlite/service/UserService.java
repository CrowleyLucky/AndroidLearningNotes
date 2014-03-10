package com.lmh.android.sqlite.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lmh.android.sqlite.domain.User;

public class UserService {
	private DBHelper helper;
	
	public UserService(Context context) {
		helper = new DBHelper(context);
	}
	
	public void add(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into user(username, password, phone) values(?,?,?)", 
				new Object[]{user.getUsername(), user.getPassword(), user.getPhone()});
	}
	
	public void delete(Integer id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from user where id=?", 
				new Object[]{id});
	}
	
	public void update(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("update user set username=?, password=?, phone=? where id=?", 
				new Object[]{user.getUsername(), user.getPassword(), user.getPhone(), user.getId()});
	}

	public User find(Integer id) {
		User user = new User();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from user where id=?", new String[]{id.toString()});
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
		Cursor cursor = db.rawQuery("select * from user limit ?,?", new String[]{offset + "", maxResult + ""});
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
	
	public Cursor getCursorScrollData(int offset, int maxResult) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select id as _id, username, password, phone from user limit ?,?", new String[]{offset + "", maxResult + ""});
		return cursor;
	}
	
	public long getCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) as total from user", null);
		cursor.moveToFirst();
		long count = cursor.getInt(cursor.getColumnIndex("total"));
		cursor.close();
		return count;
	}
	
	public void transaction() {
		User u1 = find(1);
		User u2 = find(2);
		SQLiteDatabase db = helper.getWritableDatabase();
		System.out.println("before:" + getScrollData(0, 10));
		db.beginTransaction();
		try {
			u1.setPassword(u1.getPassword() + "000");
			u2.setPassword(u2.getPassword() + "000");
			update(u1);
			update(u2);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		System.out.println("after:" + getScrollData(0, 10));
	}
	
}
