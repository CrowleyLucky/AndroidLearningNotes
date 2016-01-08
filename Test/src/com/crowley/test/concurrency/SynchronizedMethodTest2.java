package com.crowley.test.concurrency;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class SynchronizedMethodTest2 extends TestCase{

	//不能达到同步效果，非static方法的锁是加到Sync对象实例上的，不同的Sync对象有不同的锁，并发时竞争的是不同的锁，所以线程之间不会互斥
	public void test1() throws InterruptedException {
		Controller c1 = new Controller(new Sync());
		Controller c2 = new Controller(new Sync());
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//能达到同步效果，非static方法的锁是加到Sync对象实例上的，两个Controller操作的是同一个Sync对象，并发时竞争的是同一把锁，所以线程之间会互斥
	public void test2() throws InterruptedException {
		Sync sync = new Sync();
		Controller c1 = new Controller(sync);
		Controller c2 = new Controller(sync);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//不能达到同步效果，非static方法的锁是加到Sync对象实例上的，而static方法的锁是加到Sync.class对象上的，
	//虽然两个Controller操作的是同一个Sync对象，但是并发时竞争的不是同一把锁，所以线程之间不会互斥
	public void test3() throws InterruptedException {
		Sync sync = new Sync();
		Controller c1 = new Controller(sync);
		Controller2 c2 = new Controller2(sync);
		c1.start();
		c2.start();
		
		TimeUnit.SECONDS.sleep(6);
	}
	
	//不能达到同步效果，static方法的锁是加到Sync.class对象上的，因此并发时竞争的是同一把锁，所以线程之间会互斥
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