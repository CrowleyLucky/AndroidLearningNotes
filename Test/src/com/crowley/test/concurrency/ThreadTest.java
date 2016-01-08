package com.crowley.test.concurrency;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;



public class ThreadTest extends TestCase {
	
	public void testYeild() {
		//�μ�ProducerAndComsumer�÷�
	}
	
	public void testJoin() {
		Thread t = new Car("Ferrari");
		t.start();
		System.out.println("before invoke join()...");
		try {
			t.join();//��t�̼߳������̣߳��ȴ�t�߳�ִ�����֮�����߳��ټ�������ִ�С�
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
		System.out.println("Thread isDaemon: " + t.isDaemon());//Deamon�߳�����Ϊ�����߳���ʹ�õģ�������з�Deamon�߳̽���������������Deamon�߳�Ҳ������
		System.out.println("Thread isAlive before invoke start(): " + t.isAlive());
		System.out.println("Thread priority: " + t.getPriority());//���ȼ���1��10�����Ϊ10��Ĭ��Ϊ5
		//�߳�������״̬��NEW��RUNNABLE��BLOCKED��TIMED_WAITING��TERMINATED
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
				TimeUnit.MILLISECONDS.sleep(1000);//�߳�˯��100ms
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
				TimeUnit.SECONDS.sleep(1);//�߳�˯��1s
				System.out.println("car run ... " + ++i);
				for(int j = 0; j < 1000000000; j++, j++) j--;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}