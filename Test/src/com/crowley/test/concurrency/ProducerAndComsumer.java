package com.crowley.test.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProducerAndComsumer {
	private Queue<Food> foods = new LinkedList<Food>();
	private final int FOOD_CAPACITY = 10;
	

	public synchronized void produceFood(Food food) {
		try {
			if(foods.size() == FOOD_CAPACITY) {
				this.wait();
			} else {
				food.showProduced();
				foods.offer(food);
				this.notifyAll();//提醒Comsumer有新的Food可以吃了
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void eatFood() {
		try {
			if(foods.size() == 0) {
				this.wait();
			} else {
				Food food = foods.poll();
			food.eaten();
			this.notifyAll();//提醒Producer可以生产新的Food了
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ProducerAndComsumer controller = new ProducerAndComsumer();
		new Producer(controller).start();
		new Comsumer(controller).start();
		new Producer(controller).start();
		new Comsumer(controller).start();
		new Producer(controller).start();
		new Comsumer(controller).start();
	}
}



class Producer extends Thread {
	private ProducerAndComsumer controller;
	
	public Producer(ProducerAndComsumer controller) {
		this.controller = controller;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Food food = new Food();
				controller.produceFood(food);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(800));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}


class Comsumer extends Thread {
	private ProducerAndComsumer controller;
	
	public Comsumer(ProducerAndComsumer controller) {
		this.controller = controller;
	}
	@Override
	public void run() {
		while(true) {
			try {
				controller.eatFood();
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

class Food {
	private static int counter = 0;
	private final int id = ++counter;
	
	public void eaten() {
		System.out.println(this + " has been eaten...");
	}
	
	public void showProduced() {
		System.out.println(this + " has been produced...");
	}
	
	@Override
	public String toString() {
		return "Food_id:" + id;
	}
	
}