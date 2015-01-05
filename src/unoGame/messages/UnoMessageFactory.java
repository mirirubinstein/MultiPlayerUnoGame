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
		}
		return null;
	}
	

}
