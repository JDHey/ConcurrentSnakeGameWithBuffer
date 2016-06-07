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
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Login4 extends JDialog{

	protected JTextField textField;
	protected JTextArea textArea;
	static ConcurrentNavigableMap<String, String> accMap;
	private JTextField tfUsername1;
	private JPasswordField pfPassword1;
	private JLabel lbUsername1;
	private JLabel lbPassword1;
	private JButton btnLogin;
	private JButton btnCancel;
	private JTextField tfUsername2;
	private JPasswordField pfPassword2;
	private JLabel lbUsername2;
	private JLabel lbPassword2;
	private JTextField tfUsername3;
	private JPasswordField pfPassword3;
	private JLabel lbUsername3;
	private JLabel lbPassword3;
	private JTextField tfUsername4;
	private JPasswordField pfPassword4;
	private JLabel lbUsername4;
	private JLabel lbPassword4;
	private JButton btnAutofill;
	private boolean succeeded;
	static ThreadPoolExecutor executor;
	static GameState myGame;
	static BufferIO myBuffer;
	static MonitorLoggedIn monitor;




	public Login4(Frame parent, ConcurrentNavigableMap<String, String> Map,ThreadPoolExecutor executor, GameState myGame, BufferIO myBuffer, MonitorLoggedIn monitor) {
		super(parent, "Login", true);

		accMap = Map;
		this.executor = executor;
		this.myGame = myGame;
		this.myBuffer = myBuffer;
		this.monitor = monitor;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername1 = new JLabel("Player 1 Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername1, cs);

		tfUsername1 = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername1, cs);

		lbPassword1 = new JLabel("Player 1 Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword1, cs);

		pfPassword1 = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword1, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		lbUsername2 = new JLabel("Player 2 Username: ");
		cs.gridx = 3;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername2, cs);

		tfUsername2 = new JTextField(20);
		cs.gridx = 4;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername2, cs);

		lbPassword2 = new JLabel("Player 2 Password: ");
		cs.gridx = 3;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword2, cs);

		pfPassword2 = new JPasswordField(20);
		cs.gridx = 4;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword2, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		lbUsername3 = new JLabel("Player 3 Username: ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		panel.add(lbUsername3, cs);

		tfUsername3 = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(tfUsername3, cs);

		lbPassword3 = new JLabel("Player 3 Password: ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(lbPassword3, cs);

		pfPassword3 = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(pfPassword3, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		lbUsername4 = new JLabel("Player 4 Username: ");
		cs.gridx = 3;
		cs.gridy = 3;
		cs.gridwidth = 1;
		panel.add(lbUsername4, cs);

		tfUsername4 = new JTextField(20);
		cs.gridx = 4;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(tfUsername4, cs);

		lbPassword4 = new JLabel("Player 4 Password: ");
		cs.gridx = 3;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(lbPassword4, cs);

		pfPassword4 = new JPasswordField(20);
		cs.gridx = 4;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(pfPassword4, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		btnLogin = new JButton("Login");
		btnAutofill = new JButton("Fill Login");

		btnAutofill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tfUsername1.setText("John");
				pfPassword1.setText("1234");

				tfUsername2.setText("bob");
				pfPassword2.setText("password");

				tfUsername3.setText("jeff");
				pfPassword3.setText("jeff");

				tfUsername4.setText("1");
				pfPassword4.setText("1");

			}
		});
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (Auth()) {
						JOptionPane.showMessageDialog(Login4.this,
								"You have successfully logged in.",
								"Login",
								JOptionPane.INFORMATION_MESSAGE);
						succeeded = true;
						dispose();
					} else {
						JOptionPane.showMessageDialog(Login4.this,
								"Invalid username or password",
								"Login",
								JOptionPane.ERROR_MESSAGE);
						// reset username and password
						tfUsername1.setText("");
						pfPassword1.setText("");
						tfUsername2.setText("");
						pfPassword2.setText("");
						tfUsername3.setText("");
						pfPassword3.setText("");
						tfUsername4.setText("");
						pfPassword4.setText("");
						succeeded = false;

					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
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
		bp.add(btnAutofill);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	public String[] getUsernames() {
		String[] usernames = new String[14];
		usernames[0] = tfUsername1.getText().trim();
		usernames[1] = tfUsername2.getText().trim();
		usernames[2] = tfUsername3.getText().trim();
		usernames[3] = tfUsername4.getText().trim();
		
		for (int i = 4; i < 14; i++){
			if(!Arrays.asList(usernames).contains(Integer.toString(i))){
				usernames[i] = Integer.toString(i);
			}
		}
		return usernames;
	}

	public String[] getPasswords() {
		String[] passwords = new String[14];
		passwords[0] = new String(pfPassword1.getPassword());
		passwords[1] = new String(pfPassword2.getPassword());
		passwords[2] = new String(pfPassword3.getPassword());
		passwords[3] = new String(pfPassword4.getPassword());
		
		for (int i = 4; i < 14; i++){
			if(!Arrays.asList(passwords).contains(Integer.toString(i))){
				passwords[i] = Integer.toString(i);
			}
		}

		return passwords;
	}

	public boolean isSucceeded() {
		return succeeded;
	}


	public  boolean Auth() throws InterruptedException, ExecutionException{

		String[] usernames = getUsernames();
		String[] passwords = getPasswords();
		RunnableAuth[] runnables = new RunnableAuth[14];
		Thread[] threads = new Thread[14];



		for(int i = 0; i < 4; i++){
			runnables[i] = new RunnableAuth(usernames[i], passwords[i], accMap, false, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}

		for(int i = 4; i < 14; i++){
			runnables[i] = new RunnableAuth(usernames[i], passwords[i], accMap, true, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}

		for(int i = 0; i < 14; i++){
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

		//	for(int i = 0; i< 4; i++){
		//		if(accMap.containsKey(usernames[i])){
		//			if(accMap.get(usernames[i]).equals(passwords[i])){
		//				return true;
		//			}
		//		}
		//	}

		return true;

	}


}
