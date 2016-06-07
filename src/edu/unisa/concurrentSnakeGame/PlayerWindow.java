package edu.unisa.concurrentSnakeGame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public class PlayerWindow {
	public static final int FPS = 25;
	public static final int WINDOW_SIZE = 700;
	
	JFrame jFrame;
	DrawSurface jPanel;
    
    Main menu;
	
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
        
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setMnemonic(KeyEvent.VK_E);
        exitMenu.setToolTipText("Exit Snake Game");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        JMenu tools = new JMenu("Sound");
        tools.setMnemonic(KeyEvent.VK_T);
        JMenuItem sound = new JMenuItem("Play");
        sound.setMnemonic(KeyEvent.VK_P);
        sound.setToolTipText("Play Sound");
        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sound s = new sound();
            }
        });
        
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);
        aboutMenu.setToolTipText("About Snake Game");
        aboutMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                About a = new About(menu);
            }
        });
        
        file.add(exitMenu);
        menubar.add(file);
        
        tools.add(sound);
        menubar.add(tools);
        
        help.add(aboutMenu);
        menubar.add(help);  
        
        jFrame.setJMenuBar(menubar);
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