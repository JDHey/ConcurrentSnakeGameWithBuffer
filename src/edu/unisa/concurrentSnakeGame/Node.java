package edu.unisa.concurrentSnakeGame;

public abstract class Node {
	public static final int DEFAULT_SIZE = 8;
	protected double x, y;
	protected int size;
	
	public Node(double x, double y, int size) {
		this.x = x; 
		this.y = y;
		this.size = size;
	}
	
	public Node(double x, double y) {
		this(x,y,DEFAULT_SIZE);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean collidesWith(Node head) {
		//this node
		int myTop = (int) (getY() + getSize()/2);
		int myBottom = (int) (getY() - getSize()/2);
		int myRight = (int) (getX() + getSize()/2);
		int myLeft = (int) (getX() - getSize()/2);

		//other head
		int theirTop = (int) (head.getY() + head.getSize()/2);
		int theirBottom = (int) (head.getY() - head.getSize()/2);
		int theirRight = (int) (head.getX() + head.getSize()/2);
		int theirLeft = (int) (head.getX() - head.getSize()/2);

		//If there's any gap, return false. If not, return true
		return !(myRight < theirLeft || myLeft > theirRight || myTop < theirBottom || myBottom > theirTop);
	}
}
