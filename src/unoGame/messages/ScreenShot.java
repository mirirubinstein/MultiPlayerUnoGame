package unoGame.messages;



import unoGame.Card;
import unoGame.CardColor;
import unoGame.PlayerBasicInfo;

import java.io.Serializable;

public class ScreenShot implements Serializable {
    public Card[] myCards;
 //   public PlayerBasicInfo[] playersInfo;
    public int currentPlayerIndex;
    public Card topCard;
    public boolean isInAscendingOrder;
    public int myPlayerIndex;
    
    
}