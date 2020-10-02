package test.java.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queue {

	public static void main(String[] args) {
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(50); 
		//BlockingQueue queue = new LinkedBlockingQueue(); 
		
		Thread p = new Thread(new Producer(queue)); 
		Thread c = new Thread(new Consumer(queue)); 
		
		p.start(); 
		c.start();

	}
}
