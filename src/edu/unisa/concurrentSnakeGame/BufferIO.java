package edu.unisa.concurrentSnakeGame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Buffer queue used to simulate a network stream of data
 * @author Jayden
 *
 */
public class BufferIO {
	BlockingQueue<PlayerNetworkData> bufferQueue = new LinkedBlockingQueue<PlayerNetworkData>();
	
	public void offer(PlayerNetworkData data) {
		try {
			bufferQueue.put(data);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public PlayerNetworkData poll() {		
		return bufferQueue.poll();
	}

	public PlayerNetworkData take() {
		PlayerNetworkData data = null;
		try {
			data = bufferQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}

	public BlockingQueue<PlayerNetworkData> getBufferQueue() {
		return bufferQueue;
	}

	public void setBufferQueue(BlockingQueue<PlayerNetworkData> bufferQueue) {
		this.bufferQueue = bufferQueue;
	}
}
