package edu.unisa.concurrentSnakeGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Login1  extends JDialog{

	protected JTextField textField;
	protected JTextArea textArea;
	static ConcurrentNavigableMap<String, String> accMap;
	private static JTextField tfUsername;
	private static JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	private boolean succeeded;
	static ThreadPoolExecutor executor;
	static GameState myGame;
	static BufferIO myBuffer;
	static MonitorLoggedIn monitor;

	public Login1(Frame parent, ConcurrentNavigableMap<String, String> Map,ThreadPoolExecutor executor, GameState myGame, BufferIO myBuffer, MonitorLoggedIn monitor) {
		super(parent, "Login", true);
		//
		accMap = Map;
		this.executor = executor;
		this.myGame = myGame;
		this.myBuffer = myBuffer;
		this.monitor = monitor;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		lbPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		btnLogin = new JButton("Login");

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (Auth()) {
						JOptionPane.showMessageDialog(Login1.this,
								"Hi " + getUsernames()[0] + "! You have successfully logged in.",
								"Login",
								JOptionPane.INFORMATION_MESSAGE);
						succeeded = true;
						dispose();
					} else {
						JOptionPane.showMessageDialog(Login1.this,
								"Invalid username or password",
								"Login",
								JOptionPane.ERROR_MESSAGE);
						// reset username and password
						tfUsername.setText("");
						pfPassword.setText("");
						succeeded = false;

					}
				} catch (HeadlessException | InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	public static String[] getUsernames() {
		String[] usernames = new String[11];
		usernames[0] = tfUsername.getText().trim();

		for (int i = 1; i < 11; i++){
			if(!Arrays.asList(usernames).contains(Integer.toString(i))){
				usernames[i] = Integer.toString(i);
			}else{
				usernames[i] = Integer.toString(i+1);
			}
		}
		return usernames;
	}

	public static String[] getPasswords() {
		String[] passwords = new String[11];
		passwords[0] = new String(pfPassword.getPassword());

		for (int i = 1; i < 11; i++){
			if(!Arrays.asList(passwords).contains(Integer.toString(i))){
				passwords[i] = Integer.toString(i);
			}else{
				passwords[i] = Integer.toString(i+1);
			}
		}

		return passwords;
	}

	public boolean isSucceeded() {
		return succeeded;
	}


	public static boolean Auth() throws InterruptedException, ExecutionException{
		String[] usernames = getUsernames();
		String[] passwords = getPasswords();
		RunnableAuth[] runnables = new RunnableAuth[11];
		Thread[] threads = new Thread[11];

		for(int i = 0; i < 1; i++){
			runnables[i] = new RunnableAuth(usernames[i], passwords[i], accMap, false, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}

		for(int i = 1; i < 11; i++){
			runnables[i] = new RunnableAuth(usernames[i], passwords[i], accMap, true, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}

		for(int i = 0; i < 11; i++){
			threads[i].start();
		}

		for(Thread th : threads){
			while(th.isAlive()){
				System.out.println("waiting");
			}
		}

		for(int i = 0; i < runnables.length; i++){
			if(!(boolean) (runnables[i]).get()){
				return false;
			}
		}
		return true;
	}


}
