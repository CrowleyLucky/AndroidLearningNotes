package com.crowley.test.concurrency;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class SynchronizedMethodTest2 extends TestCase{

	//���ܴﵽͬ��Ч������static���������Ǽӵ�Sync����ʵ���ϵģ���ͬ��Sync�����в�ͬ����������ʱ�������ǲ�ͬ�����������߳�֮�䲻�ụ��
	public void test1() throws InterruptedException {
		Controller c1 = new Controller(new Sync());
		Controller c2 = new Controller(new Sync());
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//�ܴﵽͬ��Ч������static���������Ǽӵ�Sync����ʵ���ϵģ�����Controller��������ͬһ��Sync���󣬲���ʱ��������ͬһ�����������߳�֮��ụ��
	public void test2() throws InterruptedException {
		Sync sync = new Sync();
		Controller c1 = new Controller(sync);
		Controller c2 = new Controller(sync);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//���ܴﵽͬ��Ч������static���������Ǽӵ�Sync����ʵ���ϵģ���static���������Ǽӵ�Sync.class�����ϵģ�
	//��Ȼ����Controller��������ͬһ��Sync���󣬵��ǲ���ʱ�����Ĳ���ͬһ�����������߳�֮�䲻�ụ��
	public void test3() throws InterruptedException {
		Sync sync = new Sync();
		Controller c1 = new Controller(sync);
		Controller2 c2 = new Controller2(sync);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//���ܴﵽͬ��Ч����static���������Ǽӵ�Sync.class�����ϵģ���˲���ʱ��������ͬһ�����������߳�֮��ụ��
	public void test4() throws InterruptedException {
		Sync sync = new Sync();
		Controller2 c1 = new Controller2(sync);
		Controller2 c2 = new Controller2(sync);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
}

class Controller extends Thread {
	private Sync sync;
	
	public Controller(Sync sync) {
		this.sync = sync;
	}
	
	@Override
	public void run() {
		sync.testA();
	}
	
}

class Controller2 extends Thread {
	private Sync sync;
	
	public Controller2(Sync sync) {
		this.sync = sync;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		sync.testB();
	}
	
}

class Sync {
	
	public synchronized void testA() {
		System.out.println("Sync.testA() start...");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Sync.testA() end...");
	}
	
	public synchronized static void testB() {
		System.out.println("Sync.testB() start...");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Sync.testB() end...");
	}
}