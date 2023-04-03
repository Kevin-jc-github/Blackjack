import java.util.Random;

public class AIPlayer extends Player {
    private Random random;

    public AIPlayer(String name, int balance) {
        super(name, balance);
        random = new Random();
    }

    @Override
    public boolean shouldHit() {
        return getHandValue() < 17;
    }

    @Override
    public int placeBet() {
        int bet = random.nextInt(balance / 2) + 1;
        return bet;
    }
}
