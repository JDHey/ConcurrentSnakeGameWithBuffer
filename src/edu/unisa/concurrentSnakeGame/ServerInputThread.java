package edu.unisa.concurrentSnakeGame;


public class ServerInputThread implements Runnable {
	static GameState myGame;
	static BufferIO myBuffer;
	static MonitorLoggedIn monitor;

	public ServerInputThread(GameState myGame, BufferIO myBuffer, MonitorLoggedIn monitor) {
		ServerInputThread.myGame = myGame;
		ServerInputThread.myBuffer = myBuffer;
		ServerInputThread.monitor = monitor;
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

			if(monitor.search(data.getId())){
				if (!myGame.setDirectionPlayer(data)) {
					myGame.addPlayer(data.getId());
				}
			}
		}

	}
}
