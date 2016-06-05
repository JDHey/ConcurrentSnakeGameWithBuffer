package edu.unisa.concurrentSnakeGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is for local players who wish to have controls
 * @author Jayden
 *
 */
public class PlayerLocal extends Player implements KeyListener  {
	// initialise field attributes
	private String username ="username";
	private String password ="*****";
	private int upKey = 0, downKey = 1, leftKey = 2, rightKey = 4;

	// Getters and Setters
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;	}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;	}

	public int getUpKey() {	return upKey;}
	public void setUpKey(int upKey) {this.upKey = upKey;}

	public int getDownKey() {return downKey;}
	public void setDownKey(int downKey) {this.downKey = downKey;}

	public int getLeftKey() {return leftKey;}
	public void setLeftKey(int leftKey) {this.leftKey = leftKey;}

	public int getRightKey() {return rightKey;}
	public void setRightKey(int rightKey) {this.rightKey = rightKey;}

	public PlayerLocal(String playerId, BufferIO myBuffer, GameState gameState, int upKey, int downKey, int leftKey, int rightKey) {
		super(playerId, myBuffer, gameState, true);
		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		
		jFrame.getJPanel().addKeyListener(this);
		jFrame.getJPanel().setPlayerId(playerId);
	}

	/**
	 * Used for getting keyboard input
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		
		/*
		 * Can't use a switch statement as case labels need compile time constants
		 * instead of variables
		 */
		
		if (key == upKey) {
			setLastKeyPressed(Move.UP);
		} else if (key == downKey) {
			setLastKeyPressed(Move.DOWN);
		} else if (key == leftKey) {
			setLastKeyPressed(Move.LEFT);
		} else if (key == rightKey) {
			setLastKeyPressed(Move.RIGHT);
		} else if (key == KeyEvent.VK_ESCAPE) {
			setLastKeyPressed(Move.NONE);
		}
		
		updateBuffer();
	}

	//Unused implementation from the KeyListener Interface
	@Override
	public void keyReleased(KeyEvent arg0) {
		setLastKeyPressed(Move.NONE);
		updateBuffer();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
