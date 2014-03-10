package com.lmh.android.sqlite.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, "user.db", null, 4);
	}

	//数据库第一次创建时调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("create database：");
		db.execSQL("create table user (id integer primary key autoincrement, username varchar(20), password varchar(40))");
	}

	//数据库版本发生变化时调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("oldVersion:" + oldVersion + ", newVersion:" + newVersion);
		db.execSQL("alter table user add phone varchar(12)");
	}
	
}
