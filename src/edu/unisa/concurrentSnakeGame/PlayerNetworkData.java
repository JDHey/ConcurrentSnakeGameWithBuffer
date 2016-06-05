package edu.unisa.concurrentSnakeGame;

public class PlayerNetworkData {
	private String id;
	private Player.Move keyPressed;

	public PlayerNetworkData(String id, Player.Move keyPressed) {
		this.id = id;
		this.keyPressed = keyPressed;
	}
	
	public String getId() {
		return id;
	}

	public Player.Move getKeyPressed() {
		return keyPressed;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    final PlayerNetworkData other = (PlayerNetworkData) obj;
	    if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
	        return false;
	    }
	    if (this.keyPressed != other.keyPressed) {
	        return false;
	    }
	    return true;
	}
	
}
