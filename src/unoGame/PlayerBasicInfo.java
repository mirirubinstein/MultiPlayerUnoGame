package unoGame;

import java.io.Serializable;

public class PlayerBasicInfo implements Serializable {
    public String name;
    public int cardsInHand;
    private boolean calledUno;

    public PlayerBasicInfo(String name, int cardsInHand, boolean declaredUno) {

        this.name = name;
        this.cardsInHand = cardsInHand;
        this.calledUno = calledUno;
    }
}
