package unoGame.messages;

import java.util.Scanner;

public class UnoMessageFactory {
	public String getMessage(String message){
		Scanner scanner = new Scanner(message);
		String messageType = scanner.next();
		switch(messageType){
		case "NEWPLAYER": 
			String name = scanner.next();
			return name;
		case "DRAW":
			return "DRAW\n";
		case "PLAY_CARD":
			String color =  scanner.next();
			String number = scanner.next();
			return messageType + " " + color + " " + number ;
		}
		
		return null;
	}
	

}
