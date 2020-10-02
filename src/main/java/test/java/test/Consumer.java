package test.java.test;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable { 
	
	private BlockingQueue<String> queue; 
	
	public Consumer(BlockingQueue<String> queue) { 
		this.queue = queue; 
	} 
	@Override 
	public void run() { 
		while(true) { 
			try { 
				Thread.sleep(2000); 
				String msg = queue.take(); 
				System.out.println("메시지를 꺼냅니다. : " + msg + " [" + queue.size() + "]"); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 
		} 
	} 
}

