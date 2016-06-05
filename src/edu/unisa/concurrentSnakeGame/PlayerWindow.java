package edu.unisa.concurrentSnakeGame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PlayerWindow {
	public static final int FPS = 25;
	public static final int WINDOW_SIZE = 700;
	
	JFrame jFrame;
	DrawSurface jPanel;
	
	public PlayerWindow(String playerId, GameState gameState) {
		jPanel = new DrawSurface(gameState);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initComponents(playerId, gameState);
				renderLoop();
			}
		});
	}
	
	/**
	 * Initialize GUI and components (including ActionListeners etc)
	 */
	protected void initComponents(String playerId, GameState gameState) {
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setTitle(playerId+" VIEW");
		jFrame.setSize(WINDOW_SIZE,WINDOW_SIZE);
		
		jPanel.setLayout(new FlowLayout());
		jPanel.add(new JLabel("I am " + playerId));
		jPanel.add(new JLabel("Press the arrow keys to move"));
		jPanel.setFocusable(true);

		jFrame.add(jPanel);
		jFrame.setVisible(true);
	}

	/**
	 * Starts the rendering loop. This is done using a timer.
	 */
	private void renderLoop() {
		int timerDelay = 1000 / FPS;
		new Timer(timerDelay, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jPanel.repaint();
			}
		}).start();
	}
	
	public DrawSurface getJPanel() {
		return jPanel;
	}
}
