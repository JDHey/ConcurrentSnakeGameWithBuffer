package edu.unisa.concurrentSnakeGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawSurface extends JPanel {
	private GameState myGameState; //Used on client render
	private boolean isServerRender;
	private boolean drawSlowText;
	private String playerId;
		
	/**
	 * Main constructor for DrawSurface. Draws the gameState out.
	 * Has a boolean variable for drawing server UI.
	 * @param myGameState
	 * @param isServerRender
	 */
	public DrawSurface(GameState myGameState, boolean isServerRender) {
		this.myGameState = myGameState;
		this.isServerRender = isServerRender;
		drawSlowText = false;
		playerId = null;
		
		init();
	}
	
	public DrawSurface(GameState myGameState) {
		this(myGameState, false);
	}
	
	private void init() {
		//Set background
		setBackground(Color.WHITE);
		
		//Set focusable to allow keyboard input
		setFocusable(true);
		requestFocusInWindow();		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Draws GameState
		myGameState.draw(g, playerId);

		if (isServerRender) {
			//Render UI last so it appears on top
			g.setColor(Color.BLACK);
			if (drawSlowText) {
				g.drawString("RANDOM SERVER DELAYS ON (Press S to toggle)", 0, Main.GAME_SIZE-30);
			} else {
				g.drawString("RANDOM SERVER DELAYS OFF (Press S to toggle)", 0, Main.GAME_SIZE-30);
			}
		}
	}
	
	public void setSlowText(boolean b) {
		drawSlowText = b;
	}

	/**
	 * This is so our jPanel is the correct size when pack() is called on Jframe
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PlayerWindow.WINDOW_SIZE, PlayerWindow.WINDOW_SIZE);
	}
	
	
	/**
	 * Used for identifying which Snake to highlight to indicate player ownership
	 * @param playerId
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
}
