package com.lmh.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

public class StorageTest extends AndroidTestCase {
	private String fileName = "storage.txt";
	public static final String TAG = "Crowley";
	
	
	public void testInsert() {
		Uri uri = Uri.parse("content://com.crowley.personprovider/person");
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "smc-lucky");
		values.put("number", "18566291285");
		values.put("salary", "10000");
		resolver.insert(uri, values);
	}
	
	
	//从Rom中 /data/data/'package'/shared_prefs/ 目录下读取用户偏好
	public void testReadFromSharedPreference() {
		SharedPreferences prefs = this.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
		Log.d(TAG, "name:" + prefs.getString("name", "unknown-name"));
		Log.d(TAG, "isMarried:" + prefs.getBoolean("isMarried", false));
		Log.d(TAG, "age:" + prefs.getInt("age", -1));
		Log.d(TAG, "ages:" + prefs.getInt("ages", -1));
	}
	
	//将用户偏好存到Rom中 /data/data/'package'/shared_prefs/ 目录下
	public void testSaveToSharedPreference() {
		SharedPreferences prefs = this.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putString("name", "Crowley");
		edit.putInt("age", 23);
		edit.putBoolean("isMarried", false);
		edit.putFloat("height", 168.5f);
		
		edit.commit();
	}
	
	//将文件存到SDCard中，要在清单文件里面加入读写SD卡的权限
	public void testSaveToSDCard() {
		
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			System.out.println("SD卡不存在或者不可访问！");
			return;
		}
		
		
		
		//File f = new File("/mnt/sdcard", "sd.txt");//这样写兼容性不好~~
		File f = new File(Environment.getExternalStorageDirectory(), "sd2.txt");
		try {
			FileOutputStream out = new FileOutputStream(f);
			out.write("这是一个写入到SDCard中的文件！！！".getBytes());
			out.flush();
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//直接将文件存到Rom的 /data/data/'package'/files/ 目录下
	public void testSaveToRom() {
		try {
			FileOutputStream out = this.getContext().openFileOutput(fileName, Context.MODE_WORLD_READABLE);
			out.write("This is a text file content saved in Rom ! \n这是一个来自Rom里面的text文件内容@@@！".getBytes());
			
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//从Rom的 /data/data/'package'/files/ 目录下读取文件
	public void testReadFromRom() {
		try {
			FileInputStream in = this.getContext().openFileInput(fileName);
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream contents = new ByteArrayOutputStream();
			int length = 0;
			while(-1 != (length = in.read(buffer))) {
				contents.write(buffer, 0, length);				
			}
			System.out.println(new String(contents.toByteArray(), "UTF-8"));
			contents.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
}
