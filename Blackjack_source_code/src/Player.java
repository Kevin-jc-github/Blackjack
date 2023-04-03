import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected List<Card> hand;
    protected String name;
    protected int balance;

    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if (card.getRank().equals("A")) {
                aceCount++;
            }
            value += card.getValue();
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public abstract boolean shouldHit();

    public abstract int placeBet();

    @Override
    public String toString() {
        return name;
    }
}
