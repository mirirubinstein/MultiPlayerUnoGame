package unoGame;

import java.awt.Color;

public enum CardType {
	RED(Color.RED),
	YELLOW(Color.YELLOW),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE),
	WILD(Color.BLACK);
	
	private final Color color;
	
	private CardType(Color color){
		this.color = color;
	}
	public Color getColor(){
		return color;
	}
	
	

}
