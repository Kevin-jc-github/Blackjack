import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(String name, int balance) {
        super(name, balance);
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean shouldHit() {
        System.out.println("Your hand: " + hand + " (" + getHandValue() + ")");
        System.out.print("Do you want to hit? (y/n): ");
        String input = scanner.next();
        return input.equalsIgnoreCase("y");
    }

    @Override
    public int placeBet() {
        System.out.print("Enter your bet: ");
        int bet = scanner.nextInt();
        return Math.min(bet, balance);
    }
}

