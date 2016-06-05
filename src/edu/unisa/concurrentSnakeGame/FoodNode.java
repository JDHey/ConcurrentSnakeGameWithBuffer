package edu.unisa.concurrentSnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class FoodNode extends Node{
		
	public static enum Type {
		FOOD, BONUS, SUPER;
	}
	private int id;
	private Type type;
	private int points;
	private Color colour;
	
	public FoodNode(int id, int x, int y) {
		super(x, y);
		this.id = id;
		points = 1;
		
		if (id % 5 == 0 && id % 10 == 0) {
			setType(Type.SUPER);
		} else if (id % 5 == 0) {
			setType(Type.BONUS);
		} else setType(Type.FOOD);
		
		if (type == Type.FOOD) {
			colour = new Color(225,0,0);
		}
		else if (type == Type.BONUS) {
			points = points * 2;
			colour = new Color(0,255,0);
		}
		else if (type == Type.SUPER) {
			points = points * 4;
			colour = new Color(0,0,255);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setType(Type type) {
		this.type = type;
	}

	public int getPoints() {
		return points;
	}

	public void draw(Graphics g) {
		g.setColor(colour);
		g.fillOval((int)Math.round(x-getSize()/2), (int)Math.round(y-getSize()/2), getSize(), getSize());
	}
}
