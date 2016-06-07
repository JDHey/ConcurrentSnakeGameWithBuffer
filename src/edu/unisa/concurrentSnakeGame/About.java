package edu.unisa.concurrentSnakeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.unisa.concurrentSnakeGame.Main;

public class About extends JDialog implements ActionListener{

	//create an object of the following types
	Main menu;
	JButton ok;
	JPanel contentPane = new JPanel();
	BorderLayout contentLayout = new BorderLayout();
	JPanel buttonPane = new JPanel();
	JPanel infoPane = new JPanel();
	GridLayout infoLayout = new GridLayout(0, 1);

	public About(Main obj){

		//link the object to the parameter
		menu = obj;

		//set the title of the window
		this.setTitle("About Snake Game");

		//to stop background interaction while this window is running
		this.setModal(true);

		//set the size
		this.setSize(300, 200);

		//not allow resize 
		this.setResizable(false);
		
		//add the JPanel function to the window
		this.add(contentPane());

		//make frame visible
		this.setVisible(true);

	}

	public JPanel contentPane() {
		//set the layout of the content pane
		contentPane.setLayout(contentLayout);

		//set the background color to pink
		contentPane.setBackground(Color.WHITE);

		//add the button pane into the content pane and set it to south
		contentPane.add(buttonPane(), BorderLayout.SOUTH);

		//add the info pane into the content pane and set it to east
		contentPane.add(infoPane(), BorderLayout.WEST);

		//return the contentPane
		return contentPane;
	}
	/**
	 * method to set the 'OK' button
	 * @return buttonPane a JPanel object
	 */
	public JPanel buttonPane(){

		//set the background color to pink
		buttonPane.setBackground(Color.WHITE);

		//create the 'OK' button
		ok = new JButton("OK");

		//set the action listener
		ok.setActionCommand("ok");
		ok.addActionListener(this);

		//adding the button into the button pane
		buttonPane.add(ok);

		//return the buttonPane
		return buttonPane;
	}

	/**
	 * method that hold information of the program
	 * @return infoPane a JPanel object
	 */
	public JPanel infoPane(){

		//set the layout of the info pane to GridLayout with (0,1)
		infoPane.setLayout(infoLayout);

		//set background color to pink
		infoPane.setBackground(Color.WHITE);

		//create JLabels
		JLabel jl1 = new JLabel(" SNAKE GAME ");
		JLabel jl2 = new JLabel(" Total Real Player: 4 ");
		JLabel jl3 = new JLabel(" AI Player: 100 ");
		JLabel jl6 = new JLabel(" Key Pressed: UP, DOWN, LEFT, RIGHT ");
		JLabel jl4 = new JLabel(" Version 1.0 ");
		JLabel jl5 = new JLabel(" Copyright: (c) 2016 ");

		//add the JLabels into the info pane
		infoPane.add(jl1);
		infoPane.add(jl2);
		infoPane.add(jl3);
		infoPane.add(jl6);
		infoPane.add(jl4);
		infoPane.add(jl5);

		//return the infoPane
		return infoPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//checking the action command
		if(e.getActionCommand().equals("ok")){
			
			//close the about window
			this.dispose();
		}
	}

}