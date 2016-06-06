package edu.unisa.concurrentSnakeGame;


public class ServerInputThread implements Runnable {
	static GameState myGame;
	static BufferIO myBuffer;

	public ServerInputThread(GameState myGame, BufferIO myBuffer) {
		ServerInputThread.myGame = myGame;
		ServerInputThread.myBuffer = myBuffer;
	}

	@Override
	public void run() {
		init();
		gameLoop();
	}

	/**
	 * Adds a snake and player to the thread
	 */
	private void init() {
		String name = Thread.currentThread().getName();
		System.out.println("Initializing thread: "+name);
	}

	/**
	 * Grabs data from the buffer. If buffer is empty, the buffer will make the thread wait.
	 */
	private void gameLoop() {
		while(!Thread.currentThread().isInterrupted()) {
			myGame.delay();
			
			PlayerNetworkData data = myBuffer.take();
			//Sets the direction of the player
			if (!myGame.setDirectionPlayer(data)) {
				//If it doesn't work, add a player
				myGame.addPlayer(data.getId());
			}
		}

	}
}
