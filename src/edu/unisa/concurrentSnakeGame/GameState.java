package edu.unisa.concurrentSnakeGame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class GameState {
	public static final int DELAY_AMOUNT = 50; //In milliseconds
	
	private List<FoodNode> foodNodeList = Collections.synchronizedList(new ArrayList<FoodNode>());
	private ConcurrentHashMap<String,Snake> snakeMap = new ConcurrentHashMap<String,Snake>();// addition
	
	private boolean isPaused = false; // addition
	private boolean isSlow = false; // addition
	
	/**
	 * Draws all the snakes. 
	 * If the snakeId matches, it means the Player rendering must own that snake.
	 */
	public void draw(Graphics g, String id) {
		if (checkSnakeExists(id)) {
			Snake snake = snakeMap.get(id);
			
			Graphics2D g2 = (Graphics2D) g;
			
			//Zooms in to the snake
			//g2.translate(snake.getHead().getX(), snake.getHead().getY());
			//g2.scale(1,1);
			g2.translate(-snake.getHead().getX()+(PlayerWindow.WINDOW_SIZE/2), -snake.getHead().getY()+(PlayerWindow.WINDOW_SIZE/2));
			drawGame(g, id);
			if (snake.getCurrentState() == Snake.State.DEAD) {
				drawDeathScreen(g,snake);
			}
		} else {
			drawGame(g, id);
		}
		
	}
	
	public boolean checkSnakeExists(String id) {
		if (id != null && snakeMap.get(id) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void drawDeathScreen(Graphics g, Snake snake) {
		String text;
		int textWidth;
		
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		
		//Cover screen with grey
		g.setColor(new Color(0,0,0,125));
		g.fillRect(0-Main.GAME_SIZE, 0-Main.GAME_SIZE, Main.GAME_SIZE*3, Main.GAME_SIZE*3);
		
		//Draw death text
		int snakeX = (int)Math.round(snake.getHead().getX());
		int snakeY = (int)Math.round(snake.getHead().getY());
		g.setColor(Color.WHITE);
		text = "Your score was: "+snake.getScore();
		textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, snakeX - (textWidth/2), snakeY);
	
		text = "Press any key to restart!";
		textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, snakeX - (textWidth/2), snakeY + 50);
	
	}

	private void drawGame(Graphics g, String id) {		
		drawBackground(g);
		
		synchronized (foodNodeList) {
			for (FoodNode f : foodNodeList) {
				f.draw(g);
			}
		}
		
		for (Snake s : snakeMap.values()) {
			if (id != null && s.getId().equals(id)) {
				s.draw(g, true);
			} else {
				s.draw(g, false);
			}
		}
	}
	
	public List<FoodNode> getFoodNodeList() {
		return foodNodeList;
	}

	/**
	 * Draws a background grid
	 * @param g
	 */
	private void drawBackground(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		int left = 0-Main.GAME_SIZE;
		int top = 0-Main.GAME_SIZE;
		int right = Main.GAME_SIZE*2;
		int bottom = Main.GAME_SIZE*2;
		for(int i=0-Main.GAME_SIZE; i<Main.GAME_SIZE*2;i+=Node.DEFAULT_SIZE) {
			g.drawLine(left, i, right, i);
			g.drawLine(i, top, i, bottom);
		}

		
		//This part draws the edges blue
		g.setColor(new Color(125,249,255));
		left = 0;
		top = 0;
		right = Main.GAME_SIZE;
		bottom = Main.GAME_SIZE;
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Node.DEFAULT_SIZE, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		//Top line
		g2.drawLine(left, top, right, top);
		//Bottom line
		g2.drawLine(left, bottom, right, bottom);
		//Left line
		g2.drawLine(left, top, left, bottom);
		//Right line
		g2.drawLine(right, top, right, bottom);
	}
	
	/**
	 * Checks if the snake passed in collides 
	 * with any other living snake
	 * @param s
	 * @return
	 */
	public boolean checkCollision(Snake s) {
		for (Snake checkSnake : snakeMap.values()) {
			//Checks if the snake is not itself and is still alive
			if (!checkSnake.equals(s) && checkSnake.getCurrentState() == Snake.State.ALIVE) {
				if (checkSnake.collidesWith(s.getHead())) {
					System.out.println(s.getId() + " collided with "+checkSnake.getId());
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean setDirectionPlayer(PlayerNetworkData data) {
		Snake snake = snakeMap.get(data.getId());
		if (snake != null) {
			snake.setLastKeyPressed(data.getKeyPressed());
			return true;
		} else {
			return false;
		}
	}

	public Snake addPlayer(String id) {
		Snake snake = makeSnake(id);
		snakeMap.put(id, snake);
		return snake;
	}
	
	/**
	 * Makes and returns a snake at a random location. Temporary solution.
	 * @param name
	 * @return
	 */
	private Snake makeSnake(String name) {
		int x = (int)Math.round(Math.random()*Main.GAME_SIZE);
		int y = (int)Math.round(Math.random()*Main.GAME_SIZE);
		Snake snake = new Snake(name,x,y);
		
		return snake;
	}

	public void updateAll(double delta) {
		for (Snake snake : snakeMap.values()) {
		    if (snake.getCurrentState() == Snake.State.ALIVE) {
		    	snake.update(delta);
		    	checkFood(snake);
		    	
			    if (checkCollision(snake)) {
			    	snake.kill();
			    }			    
		    } else if (snake.getCurrentState() == Snake.State.DYING) {
		    	snake.update(delta);
			} else if (snake.getCurrentState() == Snake.State.DEAD) {
		    	//snakeMap.remove(snake.getId());
				//We need the snakes still in the list to 
				//save their score
		    }
		}
	}

	private void checkFood(Snake snake) {
		FoodNode collidedFood = null;
		List<FoodNode> foodList = getFoodNodeList();
		synchronized(foodList) {
			for(FoodNode f : foodList) {
				if (f.collidesWith(snake.getHead())) {
					snake.increaseLength(Snake.FOOD_LENGTH_INCREASE, false);
					snake.increaseScore(f.getPoints());
					collidedFood = f;
					break;
				}
			}
			if (collidedFood != null) {
				foodList.remove(collidedFood);
			}
		}
	}
	
	//EVERYTHING BELOW IS FOR DEBUGGING PERPOSES
	/**
	 * Used by the threads to add delay
	 */
	public void delay() {
		if (isSlow) {
			try {
				Thread.sleep((long) (Math.random()*DELAY_AMOUNT));
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void togglePaused() {
		isPaused = !isPaused;
		
		if (isPaused) {
			System.out.println("GAME IS PAUSED");
		} else { 
			System.out.println("UNPAUSING...");
		}
	}
	
	public boolean getPaused() {
		return isPaused;
	}
	
	public synchronized void toggleSlow() {
		isSlow = !isSlow;
		
		if (isSlow) {
			System.out.println("SLOWED DOWN MONITOR PROCESSING SPEED");
		} else { 
			System.out.println("GOING BACK TO NORMAL SPEED");
		}
	}
	
	public boolean getSlow() {
		return isSlow;
	}

	public ConcurrentHashMap<String, Snake> getSnakeMap() {
		return snakeMap;
	}

	public Snake getSnake(String id) {
		return snakeMap.get(id);
	}

	public void resetPlayer(String id) {
		snakeMap.remove(id);
		addPlayer(id);
	}
}
