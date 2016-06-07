package edu.unisa.concurrentSnakeGame;


/**
 * The only thing this class does is update all players 60 times a second.
 * During this update, it moves players and checks collisions (including food).
 * @author Jayden
 *
 */
public class ServerGameStateUpdaterThread implements Runnable {
	static GameState myGame;
	static BufferIO myBuffer;	

	public ServerGameStateUpdaterThread(GameState myGame, BufferIO myBuffer) {
		ServerGameStateUpdaterThread.myGame = myGame;
		ServerGameStateUpdaterThread.myBuffer = myBuffer;
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
	 * Updates all gameState items
	 */
	private void gameLoop() {
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0;
		long fps = 0;
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		while(!Thread.currentThread().isInterrupted()) {
			// works out how long its been since the last update, this
			// will be used to calculate how far the snake should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);

			if (!myGame.getPaused()) {
				// update the fps counters
				lastFpsTime += updateLength;
				fps++;

				// update our FPS counter if a second has passed since
				// we last recorded
				if (lastFpsTime >= 1000000000)
				{
					System.out.println("("+Thread.currentThread().getName()+"_FPS: "+fps+")");
					lastFpsTime = 0;
					fps = 0;
				}

				myGame.delay();
				myGame.updateAll(delta);
			}

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give 
			// us our final value to wait for.
			// This is in milliseconds, whereas our lastLoopTime etc. vars are in nanoseconds.
			try {
				long sleepTime = (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
				if (sleepTime < 0) { sleepTime = 0; };
				Thread.sleep( sleepTime );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}