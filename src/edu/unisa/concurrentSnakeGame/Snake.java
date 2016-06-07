package edu.unisa.concurrentSnakeGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.unisa.concurrentSnakeGame.Player.Move;

public class Snake {
	public static enum State {
		 ALIVE, DYING, DEAD;
		}
	public static final int DEFAULT_SPEED = 1;
	public static final int DYING_FADE_SPEED = 3;
	public static final int STARTING_LENGTH = 40;
	public static final int TURNING_SPEED = 5;
	public static final double TURBO_SPEED = DEFAULT_SPEED*1.5;
	public static final int MINIMUM_SIZE = 10;
	public static final int FOOD_LENGTH_INCREASE = 10;

	private String id;
	private int direction;
	private Color colour;
	private State currentState;
	private Player.Move lastKeyPressed;
	private double speed;
	
	private boolean isBoosting;

	private int score;
	
	List<SnakeNode> nodeList =  Collections.synchronizedList(new ArrayList<SnakeNode>());
	
	
	public Snake(String id, int x, int y) {
		this.id = id;
		direction = 0;
		currentState = State.ALIVE;
		score = 0;
		lastKeyPressed = Player.Move.NONE;
		speed = DEFAULT_SPEED;
		isBoosting = false;
		
		//To make sure the colour is dark and not bright white
		//I multiply by only 200 and not 255
		int red = (int) Math.round(Math.random()*200);
		int green = (int) Math.round(Math.random()*200);
		int blue = (int) Math.round(Math.random()*200);
		colour = new Color(red,green,blue, 255);
		
		SnakeNode firstNode = new SnakeNode(x,y);
		nodeList.add(firstNode);
		
		increaseLength(STARTING_LENGTH/DEFAULT_SPEED, true);
	}

	public String getId() {
		return id;
	}
	
	/**
	 * Increases the length of the snake by the amount passed in.
	 * If snappy is true, it will increase the length instantly.
	 * If snappy is false, it will slowly increase in length.
	 * @param amount
	 * @param snappy
	 */
	public void increaseLength(int amount, boolean snappy) {
		double xDir = 0;
		double yDir = 0;
		
		for(int i = 0; i<amount; i++) {
			if (nodeList.size() <= 2) {
				nodeList.add(new SnakeNode(getTail().getX()-1,getTail().getY()));
			} else {
				if (snappy) {
					SnakeNode secondLastNode = nodeList.get(1);
					
					xDir = getTail().getX() - secondLastNode.getX();
					yDir = getTail().getY() - secondLastNode.getY();
				}
				
				//Add node to tail
				SnakeNode newNode = new SnakeNode(getTail().getX()+xDir,getTail().getY()+yDir);
				nodeList.add(0, newNode);
			}
		}
	}
	
	public void decreaseLength(int amount) {
		for (int i=0; i<amount; i++) {
			if (nodeList.size()>MINIMUM_SIZE) {
				nodeList.remove(0);
			}
		}
	}
	
	/**
	 * Returns the head of the snake
	 * Needs to be synchronized. 
	 * Causes errors on some systems if not synchronized.
	 * @return
	 */
	public SnakeNode getHead() {
		return nodeList.get(nodeList.size()-1);
	}
	
	/**
	 * Returns the tail of the snake
	 * @return
	 */
	public SnakeNode getTail() {
		return nodeList.get(0);
	}
	
	/**
	 * Executes an update tick.
	 * @param deltaTime
	 */
	public void update(double deltaTime) {
		if (currentState == State.ALIVE) {
			changeDirection();
			move(speed * deltaTime);
		} else if (currentState == State.DYING){
			int a = colour.getAlpha();
			if (a > 1) {
				int r = colour.getRed();
				int g = colour.getGreen();
				int b = colour.getBlue();
				a -= DYING_FADE_SPEED;
				colour = new Color(r,g,b,a);
			} else {
				currentState = State.DEAD;
			}
		} else {
			//Dead
		}
	}
	
	/**
	 * Moves in the direction the snake is pointing.
	 * Using trigonometry to calculate x and y movements.
	 * @param amount
	 */
	void move(double amount) {
		double xAmount = Math.cos(getDirection()*Math.PI/180)*amount;
		double yAmount = Math.sin(getDirection()*Math.PI/180)*amount;
		
		double nodeX = ((getHead().getX() + xAmount)+Main.GAME_SIZE)%Main.GAME_SIZE;
		double nodeY = ((getHead().getY() + yAmount)+Main.GAME_SIZE)%Main.GAME_SIZE;
		
		nodeList.add(new SnakeNode(nodeX, nodeY));
		nodeList.remove(0);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void changeDirection() {
		switch (lastKeyPressed) {
			case UP:
				if (nodeList.size()>MINIMUM_SIZE) {
					isBoosting = true;
				}
				break;
			case DOWN:
				//Does nothing at the moment
				break;
			case LEFT:
				setDirection(getDirection()-TURNING_SPEED);
				break;
			case RIGHT:
				setDirection(getDirection()+TURNING_SPEED);
				break;
			case NONE:
				speed = DEFAULT_SPEED;
				isBoosting = false;
			default:
				//Shouldn't be default
				break;
		}
		
		if (isBoosting) {
			speed = TURBO_SPEED;
			decreaseLength(1);
		}
	}
	
	/**
	 * Checks if the snake head passed in collides 
	 * with any snake node on this body
	 * @param head
	 * @return
	 */
	public boolean collidesWith(SnakeNode head) {
		synchronized(nodeList){
			for(SnakeNode node : nodeList) {
				if (node.collidesWith(head)) { return true; }
			}
		}
		
		return false;		
	}

	/**
	 * Draws the snake.
	 * 
	 * If drawFancy is true, it indicates the player rendering must own this snake. 
	 * As such, it renders this snake fancier to make it more unique and identifiable.
	 * @param g
	 * @param drawFancy
	 */
	public void draw(Graphics g, boolean drawFancy) {
		//System.out.println("Drawing dot now at:" + getX() + "x + " + getY() + "y");
		//System.out.println(getId() + " is now moving with direction: " + getDirection());
		//System.out.println(getId() + " is now being drawn");
		int headX = (int)Math.round(getHead().getX());
		int headY = (int)Math.round(getHead().getY());
		int headSize = (int) Math.round(getHead().getSize()*1);
		
		g.setColor(colour);
		
		//Draws the head and name
		if (drawFancy) {
			//Draws the body
			synchronized(nodeList){
				//Draw outline
				g.setColor(new Color(0,0,0,colour.getAlpha()));
				for(SnakeNode node : nodeList) {
					node.draw(g, true);
				}
				
				//Draw middle
				g.setColor(colour);
				for(SnakeNode node : nodeList) {
					node.draw(g, false);
				}
			}
			
			//Draws the name and head
			int textWidth = g.getFontMetrics().stringWidth("YOU");
			g.setColor(new Color(0,0,0,colour.getAlpha()));		
			g.drawString("YOU", headX-textWidth/2, headY-headSize);
			g.setColor(colour);
			g.fillOval(headX-headSize/2, headY-headSize/2, headSize, headSize);
			
		} else {
			//Draws the body
			synchronized(nodeList){
				for(SnakeNode node : nodeList) {
					node.draw(g, false);
				}
			}
			
			//Draws the name and head
			int textWidth = g.getFontMetrics().stringWidth(getId());
			g.setColor(colour);
			g.drawString(getId(), headX-textWidth/2, headY-headSize);
			g.fillOval(headX-headSize/2, headY-headSize/2, headSize, headSize);			
		}
	}
	
	/**
	 * Changes the current state and starts the dying animation
	 */
	public void kill() {
		currentState = State.DYING;
	}

	public State getCurrentState() {
		return currentState;
	}
	public int getScore() {
		return score;
	}

	public void increaseScore(int increasedAmount) {
		this.score += increasedAmount;
	}

	public void setLastKeyPressed(Move keyPressed) {
		lastKeyPressed = keyPressed;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public Move getLastKeyPressed() {
		return lastKeyPressed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<SnakeNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<SnakeNode> nodeList) {
		this.nodeList = nodeList;
	}
}
