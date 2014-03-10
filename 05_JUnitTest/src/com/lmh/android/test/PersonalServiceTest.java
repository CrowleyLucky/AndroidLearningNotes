package com.lmh.android.test;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class PersonalServiceTest extends AndroidTestCase{
	public void testAdd() {
		PersonalService service = new PersonalService();
		int actual = service.add(5, 2);
		Assert.assertEquals(7, actual);
	}
	
	public void testSub() {
		PersonalService service = new PersonalService();
		int actual = service.sub(5, 2);
		Assert.assertEquals(3, actual);
	}
}
