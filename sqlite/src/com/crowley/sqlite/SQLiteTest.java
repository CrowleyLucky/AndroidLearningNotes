package com.crowley.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class SQLiteTest extends AndroidTestCase{
	public static final String TAG = "Crowley";

	public void testCreateTable() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.close();
	}
	
	public void testInsert() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into persons(name, age) values (?, ?)";
		//String sql = "insert into persons values (null, ?, ?)";
		db.execSQL(sql, new Object[]{"Crowley Hsu", 23});
		db.execSQL(sql, new Object[]{"Kevin", 23});
		db.execSQL(sql, new Object[]{"SMC", 23});
		
		db.close();
	}
	
	public void testQuery() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from persons order by id desc";
		Cursor c = db.rawQuery(sql, new String[]{});
		while(c.moveToNext()) {
			Log.d(TAG, String.valueOf(c.getInt(c.getColumnIndex("id"))));
			Log.d(TAG, c.getString(c.getColumnIndex("name")));
			Log.d(TAG, String.valueOf(c.getInt(c.getColumnIndex("age"))));
			Log.d(TAG, "count:" + c.getCount());
			Log.d(TAG, "---------------------------");
		}
		db.close();
	}
	
	public void testmodify() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "update persons set name = ? where id = ?";
		db.execSQL(sql, new Object[]{"SMC-2", 4});
		db.close();
	}
	
	public void testDelete() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from persons where name = ?";
		db.execSQL(sql, new Object[]{"Kevin"});
		db.close();
	}
	
	public void testGetCount() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select count(*) total from persons";
		Cursor c = db.rawQuery(sql, null);
		c.moveToFirst();
		Log.d(TAG, "total names:" + c.getInt(c.getColumnIndex("total")));
		db.close();
	}
	
	public void testQueryPart() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from persons order by id desc limit 0,2";
		Cursor c = db.rawQuery(sql, new String[]{});
		while(c.moveToNext()) {
			Log.d(TAG, String.valueOf(c.getInt(c.getColumnIndex("id"))));
			Log.d(TAG, c.getString(c.getColumnIndex("name")));
			Log.d(TAG, String.valueOf(c.getInt(c.getColumnIndex("age"))));
			Log.d(TAG, "count:" + c.getCount());
			Log.d(TAG, "---------------------------");
		}
		db.close();
	}
	
	public void testTransaction() {
		SQLiteHelper helper = new SQLiteHelper(this.getContext(), "person-db.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "update persons set name = ? where id = ?";
		try {
			db.beginTransaction();//开始事务
			db.execSQL(sql, new Object[]{"SMC-000", 1});
			
			db.execSQL(sql, new Object[]{"SMC-111", 2});
			db.setTransactionSuccessful();//设置事务状态为成功，如果不调用，事务会回滚
		} finally {
			db.endTransaction();//提交事务
			db.close();
		}
	}
}
