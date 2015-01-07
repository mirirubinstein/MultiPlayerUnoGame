package unoGame;

import java.io.Serializable;

public class PlayerBasicInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
    public int cardsInHand;
    private boolean calledUno;

    public PlayerBasicInfo(String name, int cardsInHand, boolean calledUno) {

        this.name = name;
        this.cardsInHand = cardsInHand;
        this.calledUno = calledUno;
    }

    public String getName() {
        return name;
    }

    public int getCardsInHand() {
        return cardsInHand;
    }

    public boolean isCalledUno() {
        return calledUno;
    }
}
