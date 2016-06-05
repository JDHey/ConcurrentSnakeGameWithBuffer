package edu.unisa.concurrentSnakeGame;

public class FoodThread implements Runnable{

	static GameState myGame;
	public int id = 0;
	public static final int MAX_FOOD =  5;
	
	public FoodThread(GameState gs) {
		FoodThread.myGame = gs;
	}
	
	@Override
	public void run() {
		init();
		gameLoop();
	}
	
	private void init() {
		String name = Thread.currentThread().getName();
		System.out.println("Initializing thread: "+name);
	}

	private FoodNode makeFood() {
		int x = (int)Math.round(Math.random()*Main.GAME_SIZE);
		int y = (int)Math.round(Math.random()*Main.GAME_SIZE);
		id = id + 1;
		FoodNode food = new FoodNode(id, x, y);
		myGame.getFoodNodeList().add(food);
		return food;
	}
	
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
				
				if (myGame.getFoodNodeList().size() < MAX_FOOD) {
					makeFood();
				}
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
