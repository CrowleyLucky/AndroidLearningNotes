package com.crowley.test.concurrency;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;



public class ThreadTest extends TestCase {
	
	public void testYeild() {
		//参见ProducerAndComsumer用法
	}
	
	public void testJoin() {
		Thread t = new Car("Ferrari");
		t.start();
		System.out.println("before invoke join()...");
		try {
			t.join();//将t线程加入主线程，等待t线程执行完毕之后，主线程再继续往下执行。
			System.out.println("after invoke join()...");
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testBaseProperties() {
		Thread t = new Car("Ferrari");
		System.out.println("Thread ID: " + t.getId());
		System.out.println("Thread name: " + t.getName());
		System.out.println("Thread isDaemon: " + t.isDaemon());//Deamon线程是作为服务线程来使用的，如果所有非Deamon线程结束，则程序结束，Deamon线程也结束。
		System.out.println("Thread isAlive before invoke start(): " + t.isAlive());
		System.out.println("Thread priority: " + t.getPriority());//优先级从1到10，最高为10，默认为5
		//线程有五种状态：NEW、RUNNABLE、BLOCKED、TIMED_WAITING、TERMINATED
		System.out.println("Thread state before invoke start(): " + t.getState());
		t.start();
		System.out.println("Thread isAlive after invoke start(): " + t.isAlive());
		System.out.println("Thread state after invoke start(): " + t.getState());
		System.out.println("TestThread.currentThread: " + Thread.currentThread());
		try {
			//t.wait();
			int i = 0;
			while(i < 4) {
				i ++;
				System.out.println("Thread state after invoke start(): " + t.getState());
				TimeUnit.MILLISECONDS.sleep(1000);//线程睡眠100ms
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}


class Car extends Thread {
	
	public Car() {}
	
	public Car(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		System.out.println("Car.currentThread: " + Thread.currentThread());
		try {
			int i = 0;
			while(i <= 3) {
				TimeUnit.SECONDS.sleep(1);//线程睡眠1s
				System.out.println("car run ... " + ++i);
				for(int j = 0; j < 1000000000; j++, j++) j--;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}