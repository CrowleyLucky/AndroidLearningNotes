package com.crowley.test.concurrency;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class SynchronizedBlockTest extends TestCase{
	
	//����ͬ���ϣ������߳̾������ǲ�ͬ����ʵ�����������ụ��
	public void test1() throws InterruptedException {
		Sync2 sync1 = new Sync2();
		Sync2 sync2 = new Sync2();
		Controller3 c1 = new Controller3(sync1, 1);
		Controller3 c2 = new Controller3(sync2, 1);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
	//��ͬ���ϣ������߳̾�������ͬһ������'obj'�������ụ��
	public void test2() throws InterruptedException {
		Sync2 sync = new Sync2();
		Controller3 c1 = new Controller3(sync, 1);
		Controller3 c2 = new Controller3(sync, 2);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
	//����ͬ���ϣ������߳̾������ǲ�ͬ����ʵ���������ֱ��ǣ�'obj'��'sync'�����ụ��
	public void test3() throws InterruptedException {
		Sync2 sync = new Sync2();
		Controller3 c1 = new Controller3(sync, 2);
		Controller3 c2 = new Controller3(sync, 3);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
	//��ͬ���ϣ������߳̾���������ͬ����ʵ��������Ϊ��'sync'���ụ��
	public void test4() throws InterruptedException {
		Sync2 sync = new Sync2();
		Controller3 c1 = new Controller3(sync, 3);
		Controller3 c2 = new Controller3(sync, 4);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
	//����ͬ���ϣ������߳̾������ǲ�ͬ����ʵ��������Ϊ��'obj'��'obj2'�����ụ��
	public void test5() throws InterruptedException {
		Sync2 sync = new Sync2();
		Controller3 c1 = new Controller3(sync, 1);
		Controller3 c2 = new Controller3(sync, 5);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
	//��ͬ���ϣ���Ȼ�����̲߳������ǲ�ͬʵ�����󣬵������Ķ���ͬһ����̬��������Ϊ��'obj2'���ụ��
	public void test6() throws InterruptedException {
		Sync2 sync1 = new Sync2();
		Sync2 sync2 = new Sync2();
		Controller3 c1 = new Controller3(sync1, 5);
		Controller3 c2 = new Controller3(sync2, 5);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(4);
	}
	
}


class Controller3 extends Thread {
	private Sync2 sync;
	private int methods;
	
	public Controller3(Sync2 sync, int methods) {
		this.sync = sync;
		this.methods = methods;
	}
	
	@Override
	public void run() {
		switch (methods) {
		case 1:
			sync.testA();
			break;
		case 2:
			sync.testB();
			break;
		case 3:
			sync.testC();
			break;
		case 4:
			sync.testD();
			break;
		case 5:
			sync.testE();
			break;

		default:
			break;
		}
	}
}


class Sync2{
	private Object obj = new Object();
	private static Object obj2 = new Object();
	
	public void testA() {
		synchronized (obj) {
			System.out.println("Sync.testA() with 'obj' lock start...");
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Sync.testA() with 'obj' lock end...");
		}
	}
	
	public void testB() {
		synchronized (obj) {
			System.out.println("Sync.testB() with 'obj' lock start...");
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Sync.testB() with 'obj' lock end...");
		}
	}
	
	public void testC() {
		synchronized (this) {
			System.out.println("Sync.testC() with 'this' lock start...");
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Sync.testC() with 'this' lock end...");
		}
	}
	
	public synchronized void testD() {
		System.out.println("synchronized method Sync.testD() lock start...");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("synchronized method Sync.testD() lock end...");
	}
	
	public void testE() {
		synchronized (obj2) {
			System.out.println("Sync.testE() with 'obj2' lock start...");
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Sync.testE() with 'obj2' lock end...");
		}
	}
	
	
}