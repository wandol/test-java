package test.java.test;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable { 
	
	private BlockingQueue<String> queue; 
	
	public Producer(BlockingQueue<String> queue) { 
		this.queue = queue; 
	}
	
	@Override 
	public void run() { 
		while(true) { 
			try { 
				Thread.sleep(1000); 
				Date d = new Date(); 
				String msg = "메시지"+d.toString(); 
				queue.add(msg); 
				System.out.println("메시지를 생성합니다. [" + queue.size() + "]"); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 
		} 
	} 
}

