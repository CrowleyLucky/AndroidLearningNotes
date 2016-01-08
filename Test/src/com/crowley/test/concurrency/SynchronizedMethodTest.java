package com.crowley.test.concurrency;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class SynchronizedMethodTest extends TestCase {
	//public void test....   ��ͬ�̷ֱ߳������ͨ�����;�̬�����������⣬���ܴﵽͬ��Ч��

	//��ͬ�̵߳��ò�ͬ����ʵ���ϵľ�̬synchronized�������ụ�⣬�ܴﵽͬ��Ч��
	public void testSynchronized4() {
		Counter counter1 = new Counter(false);
		Counter counter2 = new Counter(false);
		Thread t1 = new CounterController(counter1);
		Thread t2 = new CounterController(counter2);
		t1.start();
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(11);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//��ͬ�̵߳��ò�ͬ����ʵ���ϵ���ͨsynchronized���������ụ�⣬���ܴﵽͬ��Ч��
	public void testSynchronized3() {
		Counter counter1 = new Counter(true);
		Counter counter2 = new Counter(true);
		Thread t1 = new CounterController(counter1);
		Thread t2 = new CounterController(counter2);
		t1.start();
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(11);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//��ͬ�̵߳���ͬһ������ʵ���ϵľ�̬synchronized�������ụ�⣬�ܴﵽͬ��Ч��
	public void testSynchronized2() {
		Counter counter = new Counter(false);
		Thread t1 = new CounterController(counter);
		Thread t2 = new CounterController(counter);
		t1.start();
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(11);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//��ͬ�̵߳���ͬһ������ʵ���ϵ���ͨsynchronized�������ụ�⣬�ܴﵽͬ��Ч��
	public void testSynchronized1() {
		Counter counter = new Counter(true);
		Thread t1 = new CounterController(counter);
		Thread t2 = new CounterController(counter);
		t1.start();
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(11);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


class Counter {
	private int i = 0;
	private static int ii = 0;
	private boolean normalAdd;
	
	public Counter(boolean isNormalAdd) {
		normalAdd = isNormalAdd;
	}
	public synchronized void add() {
		if(normalAdd) {
			normalAdd();
		} else {
			staticAdd();
		}
	}
	//�����synchronized�ؼ��֣�ͬ�����Ƕ���ʵ���ϵ�������ͬʵ���ϵ���ͨ��������
	public synchronized void normalAdd() {
		i++;
		System.out.println("i=" + i + ",Counter.normalAdd() invoked by " + Thread.currentThread());
	}
	//�����synchronized�ؼ��֣�ͬ�����Ƕ����ϵ��������о�̬��������
	public static synchronized void staticAdd() {
		ii++;
		System.out.println("i=" + ii + ",Counter.staticAdd() invoked by " + Thread.currentThread());
	}
	
	public int getCount() {
		if(normalAdd) {
			return i;
		} else {
			return ii;
		}
	}
}

class CounterController extends Thread {
	private Counter counter;
	
	public CounterController(Counter counter) {
		this.counter = counter;
	}
	
	@Override
	public void run() {
		while(counter.getCount() < 10) {
			try {
				counter.add();
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}