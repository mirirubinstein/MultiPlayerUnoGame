package unoGame;

import java.awt.Color;

public enum CardColor {
	RED(Color.RED),
	YELLOW(Color.YELLOW),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE);
	
	private final Color color;
	
	private CardColor(Color color){
		this.color = color;
	}
	public Color getColor(){
		return color;
	}
	
	

}
