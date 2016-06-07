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
import java.util.Collection;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Login100  extends JDialog{
	
	protected JTextField textField;
    protected JTextArea textArea;
    static ConcurrentNavigableMap<String, String> accMap;
    private JLabel lbUsernames;
    private JLabel lbPasswords;
    private JButton btnLogin;
    private JButton btnCancel;
    private JList jlUsernames;
    private JList jlPasswords;
    private boolean succeeded;
    private String[] usernameData;
    private String[] passwordData;
	static ThreadPoolExecutor executor;
	static GameState myGame;
	static BufferIO myBuffer;
	static MonitorLoggedIn monitor;

    
    public Login100(Frame parent, ConcurrentNavigableMap<String, String> Map,ThreadPoolExecutor executor, GameState myGame, BufferIO myBuffer, MonitorLoggedIn monitor) {
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
 
        lbUsernames = new JLabel("Username ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsernames, cs);
 
        lbPasswords = new JLabel("Password ");
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbPasswords, cs);
        
        Collection<String> a = accMap.values();
        passwordData = new String[accMap.size()];
        a.toArray(passwordData);
        
        Collection<String> b = accMap.keySet();
        usernameData = new String[accMap.size()];
        b.toArray(usernameData);
        
        
        jlUsernames = new JList(usernameData);
        jlPasswords = new JList(passwordData);
        
        JScrollPane pane1 = new JScrollPane(jlUsernames);
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(pane1, cs);
        
        JScrollPane pane2 = new JScrollPane(jlPasswords);
        cs.gridx = 2;
        cs.gridy = 1;
        panel.add(pane2, cs);
        
        

        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnLogin = new JButton("Login");
 
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                try {
					if (Auth()) {
					    JOptionPane.showMessageDialog(Login100.this,
					            "You have successfully logged in.",
					            "Login",
					            JOptionPane.INFORMATION_MESSAGE);
					    succeeded = true;
					    dispose();
					} else {
					    JOptionPane.showMessageDialog(Login100.this,
					            "Invalid username or password",
					            "Login",
					            JOptionPane.ERROR_MESSAGE);
					    // reset username and password

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
    
//    public String getUsername() {
//        return tfUsername.getText().trim();
//    }
// 
//    public String getPassword() {
//        return new String(pfPassword.getPassword());
//    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
     
	public String[] getUsernames() {
		return usernameData;
		
	}
	
    public  boolean Auth() throws InterruptedException, ExecutionException{

		RunnableAuth[] runnables = new RunnableAuth[accMap.size()];
		Thread[] threads = new Thread[accMap.size()];
		System.out.println(accMap.size());
		
		
		for(int i = 0; i < 5; i++){
			runnables[i] = new RunnableAuth(usernameData[i], passwordData[i], accMap, false, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}

		for(int i = 5; i < accMap.size(); i++){
			runnables[i] = new RunnableAuth(usernameData[i], passwordData[i], accMap, true, executor, myGame, myBuffer, monitor);
			threads[i] = new Thread(runnables[i]);
		}
		
	
		for(int i = 0; i < accMap.size(); i++){
			threads[i].start();
		}
		
		
	
		for(Thread th : threads){
			while(th.isAlive()){
				System.out.println("waiting");
			}
		}
		
		
		for(int i = 0; i < 4; i++){
			if(!(boolean) (runnables[i]).get()){
				return false;
			}
		}
		
		return true;

	}


	
}
