package edu.unisa.concurrentSnakeGame;

/**
 * Abstract class so it can't be instantiated.
 * This class is extended in PlayerAI.class and PlayerLocal.class, use those instead.
 * @author Jayden
 *
 */
public abstract class Player {	
	public static enum Move {
		 UP, DOWN, LEFT, RIGHT, NONE;
		}
	
	public static BufferIO myBuffer;
	
	final int FPS = 25;
	
	PlayerWindow jFrame;
	private Move lastKeyPressed = null;
	
	private GameState gameState;
	private String playerId = "PlayName";
	private boolean isloggedin = false;
	int upKey, downKey, leftKey, rightKey;

	protected boolean sendUpdateToBuffer = false;

	/**
	 * Constructor for Player.
	 * Allows for setting custom key input
	 * @param playerId
	 * @param gameState
	 */
	public Player(String playerId, BufferIO myBuffer,GameState gameState, boolean renderWindow) {
		this.playerId = playerId;
		Player.myBuffer = myBuffer;
		this.gameState = gameState;
		this.isloggedin = true;
		resetLastKeyPressed();
		
		myBuffer.offer(new PlayerNetworkData(getPlayerId(), lastKeyPressed));// addition
		
		if (renderWindow) {
			//Renders window
			jFrame = new PlayerWindow(getPlayerId(),gameState);
		}
	}

	public Move getLastKeyPressed() {
		return lastKeyPressed;
	}

	/**
	 * Sets lastKeyPressed to the input if it is different.
	 * Then it sets sendUpdateToBuffer to true
	 * @param input
	 */
	public void setLastKeyPressed(Move input) {
		if (getLastKeyPressed() != input) {
			this.lastKeyPressed = input;
			sendUpdateToBuffer = true;
		}
	}

	public void resetLastKeyPressed() {	lastKeyPressed = Move.NONE;	}
	
	public String getPlayerId() {
		return playerId;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void updateGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public boolean isloggedin() {
		return isloggedin;	
	}
	public void setIsloggedin(boolean isloggedin) {	
		this.isloggedin = isloggedin;
	}

	public static BufferIO getMyBuffer() {
		return myBuffer;
	}

	public static void setMyBuffer(BufferIO myBuffer) {
		Player.myBuffer = myBuffer;
	}

	/**
	 * Sends an update to the buffer if sendUpdateToBuffer is true
	 */
	public void updateBuffer() {
		if (sendUpdateToBuffer) {
			if (isloggedin) {
				PlayerNetworkData data = new PlayerNetworkData(getPlayerId(), getLastKeyPressed());
				myBuffer.offer(data);
				sendUpdateToBuffer = false;
			}
		}
	}
}
