package com.lmh.android.sqlite.test;

import android.test.AndroidTestCase;

import com.lmh.android.sqlite.domain.User;
import com.lmh.android.sqlite.service.DBHelper;
import com.lmh.android.sqlite.service.OtherUserService;

public class OtherUserServiceTest extends AndroidTestCase {
	
	public void testCreateDatabase() {
		DBHelper helper = new DBHelper(this.getContext());
		helper.getWritableDatabase();
		helper.close();
	}
	
	public void testAddUser() {
		User u = new User(1, "Kevin", "123456", "13193196401");
		User u2 = new User(2, "SMC", "123456", "");
		User u3 = new User(3, "LMG", "123456", "");
		User u4 = new User(4, "LMH", "123456", "");
		User u5 = new User(5, "Shu", "123456", "");
		OtherUserService service = new OtherUserService(this.getContext());
		service.add(u);
		service.add(u2);
		service.add(u3);
		service.add(u4);
		service.add(u5);
	}
	
	public void testDeleteUser() {
		OtherUserService service = new OtherUserService(this.getContext());
		service.delete(4);
	}
	
	public void testFindUser() {
		OtherUserService service = new OtherUserService(this.getContext());
		User user = service.find(2);
		System.out.println(user);
	}
	
	public void testFindScrollUsers() {
		OtherUserService service = new OtherUserService(this.getContext());
		System.out.println(service.getScrollData(1, 3));
	}
	
	public void testUpdate() {
		OtherUserService service = new OtherUserService(this.getContext());
		User u = service.find(2);
		System.out.println("before update:" + u);
		u.setUsername("hehe~");
		service.update(u);
		u = service.find(2);
		System.out.println("after update:" + u);
	}
	
	public void testGetCount() {
		OtherUserService service = new OtherUserService(this.getContext());
		System.out.println(service.getCount());
	}
	
	
	
}
