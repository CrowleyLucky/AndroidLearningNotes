package com.lmh.android.sqlite.test;

import android.test.AndroidTestCase;

import com.lmh.android.sqlite.domain.User;
import com.lmh.android.sqlite.service.DBHelper;
import com.lmh.android.sqlite.service.UserService;

public class UserServiceTest extends AndroidTestCase {
	
	public void testCreateDatabase() {
		DBHelper helper = new DBHelper(this.getContext());
		helper.getWritableDatabase();
		helper.close();
	}
	
	public void testAddUser() {
		UserService service = new UserService(this.getContext());
		for(int i = 0; i < 50; i++) {
			User u = new User(i, "Kevin", "123456", "13193196401");
			service.add(u);
		}
	}
	
	public void testDeleteUser() {
		UserService service = new UserService(this.getContext());
		service.delete(4);
	}
	
	public void testFindUser() {
		UserService service = new UserService(this.getContext());
		User user = service.find(2);
		System.out.println(user);
	}
	
	public void testFindScrollUsers() {
		UserService service = new UserService(this.getContext());
		System.out.println(service.getScrollData(1, 3));
	}
	
	public void testUpdate() {
		UserService service = new UserService(this.getContext());
		User u = service.find(2);
		System.out.println("before update:" + u);
		u.setUsername("hehe~");
		service.update(u);
		u = service.find(2);
		System.out.println("after update:" + u);
	}
	
	public void testGetCount() {
		UserService service = new UserService(this.getContext());
		System.out.println(service.getCount());
	}
	
	public void testTransaction() {
		UserService service = new UserService(this.getContext());
		service.transaction();
	}
	
	
	
	
	
}
