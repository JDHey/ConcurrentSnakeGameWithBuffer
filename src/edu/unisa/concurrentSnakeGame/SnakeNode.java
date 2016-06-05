package edu.unisa.concurrentSnakeGame;

import java.awt.Graphics;

public class SnakeNode extends Node {
	public SnakeNode(double x, double y) {
		super(x, y);
	}

	public void draw(Graphics g, boolean drawOutline) {
		if (drawOutline) {
			int outlineSize = (int) Math.round(getSize()*1.5);
			g.fillOval((int)Math.round(x-outlineSize/2), (int)Math.round(y-outlineSize/2), outlineSize, outlineSize);
		} else {
			g.fillOval((int)Math.round(x-getSize()/2), (int)Math.round(y-getSize()/2), getSize(), getSize());
		}
	}
}
