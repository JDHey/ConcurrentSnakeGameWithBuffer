package edu.unisa.concurrentSnakeGame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Main implements KeyListener {
	public static final int GAME_SIZE = 1504;
	public static final int NUMBER_OF_PLAYER_MANAGING_THREADS =4;
	public static int AMOUNT_OF_LOCAL_PLAYERS = 4;
	public static int AMOUNT_OF_AI_PLAYERS = 100;
	static int gameType;
	private static String[] playerNames;

	static GameState myGame = new GameState();
	static BufferIO myBuffer = new BufferIO();

	final int FPS = 25;

	JFrame jFrame;
	DrawSurface jPanel;

	public Main() {
		/**
		 * 1 Player Gametype
		 */
		if(gameType == 1){ 
			AMOUNT_OF_LOCAL_PLAYERS = 1;
			AMOUNT_OF_AI_PLAYERS = 5;
		}else if(gameType == 2){
			AMOUNT_OF_LOCAL_PLAYERS = 4;
			AMOUNT_OF_AI_PLAYERS = 100;
		}else if(gameType == 3){
			AMOUNT_OF_LOCAL_PLAYERS = 4;
			AMOUNT_OF_AI_PLAYERS = 100;
		}
		

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(AMOUNT_OF_LOCAL_PLAYERS+AMOUNT_OF_AI_PLAYERS);
		
		addLocalPlayers("Player",AMOUNT_OF_LOCAL_PLAYERS, executor);
		addAIPlayers("AI",AMOUNT_OF_AI_PLAYERS, executor);
		executor.shutdown();
		initComponents();
		renderLoop();
	}
	
	private void addLocalPlayers(String name, int amount, ThreadPoolExecutor executor) {
		for (int i = 0; i<AMOUNT_OF_LOCAL_PLAYERS; i++) {			
			new PlayerLocal("Player"+(i+1), myBuffer, myGame, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		}
	}
	
	/**
	 * Adds lots of players. Temporary solution.
	 */
	private void addAIPlayers(String name, int amount, ThreadPoolExecutor executor) {
		for (int i = 0; i<amount; i++) {			
			PlayerAI player = new PlayerAI(name+(i+1), myBuffer, myGame);
			//Thread playerThread = new Thread(player);
			//playerThread.setName("AIThread"+i);
			//playerThread.start();
			//myMonitor.addLoggedOutPlayer(player);
			
			executor.execute(player);
		}
	}

	/**
	 * Starts the threads and names them
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		* Opens the Database and then opens the Account Map
		*/
		
		DB db = DBMaker.newFileDB(new File("testdb1"))

				.closeOnJvmShutdown()

				.encryptionEnable("password")

				.make();
		ConcurrentNavigableMap<String, String> accMap = db.getTreeMap("AccountMap");

		/**
		* Creates the swing frame and creates the buttons
		*/
		final JFrame frame = new JFrame("JDialog Demo");
		final JButton btnLogin = new JButton("Click to login 1 Player");
		final JButton btnLogin4 = new JButton("Click to login 4 Players");
		final JButton btnLogin100 = new JButton("Click to login 100 Players");

		/**
		* 1 Player
		*/
		btnLogin.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						playerNames = new String[1];
						Login1 loginDlg = new Login1(frame, accMap);
						loginDlg.setVisible(true);

						if(loginDlg.isSucceeded()){
							playerNames[0] = loginDlg.getUsername();
							gameType = 1;
							startGame();
							
						}
					}
				});
		
		/**
		* 4 Player
		*/
		btnLogin4.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						Login4 loginDlg = new Login4(frame, accMap);
						loginDlg.setVisible(true);
						
						if(loginDlg.isSucceeded()){
							playerNames = loginDlg.getUsernames();
							gameType = 2;
							startGame();
							
						}
					}
				});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(btnLogin);
		frame.getContentPane().add(btnLogin4);
		frame.getContentPane().add(btnLogin100);
		frame.setVisible(true);

		

	}
	private static void startGame(){
		
		for (int i=0; i<NUMBER_OF_PLAYER_MANAGING_THREADS; i++) {
			Thread t = new Thread(new ServerInputThread(myGame, myBuffer));
			t.setName("Thread"+(i+1));
			t.start();
		}
		
		Thread t2 = new Thread(new ServerGameStateUpdaterThread(myGame, myBuffer));
		t2.setName("GameStateUpdater");
		t2.start();

		Thread food = new Thread(new FoodThread(myGame));
		food.setName("Food Thread");
		food.start();
			
		/**
		 * Create GUI and components on Event-Dispatch-Thread
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unused")
				Main main = new Main();
			}
		});
	}

	/**
	 * Initialize GUI and components (including ActionListeners etc)
	 */
	private void initComponents() {
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setTitle("SERVER VIEW");
		jFrame.setSize(Main.GAME_SIZE,Main.GAME_SIZE);
		
		jPanel = new DrawSurface(myGame, true); 
		jPanel.add(new JLabel("Server Render."));
		jPanel.add(new JLabel("Space to pause. S to toggle random server delays"));
		jPanel.addKeyListener(this);

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
				if (!myGame.getPaused()) {
					jPanel.repaint();
				}
			}
		}).start();
	}

	/**
	 * Server controls
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		switch (key) {
			case KeyEvent.VK_S:
				myGame.toggleSlow();
				jPanel.setSlowText(myGame.getSlow());
				break;
			case KeyEvent.VK_SPACE:
				myGame.togglePaused();
				break;
			default:
				// Unsupported key
				break;
		}
	}

	//Unused implementation from the KeyListener Interface
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}