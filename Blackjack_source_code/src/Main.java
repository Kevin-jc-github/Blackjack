import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.next();
        System.out.print("Enter your initial balance: ");
        int playerBalance = scanner.nextInt();

        HumanPlayer humanPlayer = new HumanPlayer(playerName, playerBalance);
        List<Player> players = new ArrayList<>();
        players.add(humanPlayer);
        players.add(new AIPlayer("AI 1", 1000));
        players.add(new AIPlayer("AI 2", 1000));
        players.add(new AIPlayer("AI 3", 1000));

        AIPlayer dealer = new AIPlayer("Dealer", 1000);
        players.add(dealer);

        Deck deck = new Deck();

        boolean play = true;
        while (play) {
            for (Player player : players) {
                player.clearHand();
                player.addCard(deck.deal());
                player.addCard(deck.deal());
            }

            int[] bets = new int[players.size()];
            for (int i = 0; i < players.size(); i++) {
                bets[i] = players.get(i).placeBet();
            }

            boolean[] done = new boolean[players.size()];
            boolean allDone;
            do {
                allDone = true;
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    if (!done[i] && player.shouldHit()) {
                        player.addCard(deck.deal());
                        if (player.isBusted()) {
                            done[i] = true;
                        }
                    } else {
                        done[i] = true;
                    }
                    allDone &= done[i];
                }
            } while (!allDone);

            for (int i = 0; i < players.size() - 1; i++) {
                Player player = players.get(i);
                int bet = bets[i];
                if (player.isBusted()) {
                    player.setBalance(player.getBalance() - bet);
                } else {
                    int playerValue = player.getHandValue();
                    int dealerValue = dealer.getHandValue();
                    if (dealer.isBusted() || playerValue > dealerValue) {
                        player.setBalance(player.getBalance() + bet);
                    } else if (playerValue < dealerValue) {
                        player.setBalance(player.getBalance() - bet);
                    }
                }
            }

            for (int i = 0; i < players.size() - 1; i++) {
                Player player = players.get(i);
                String result;
                int playerValue = player.getHandValue();
                int dealerValue = dealer.getHandValue();
                if (player.isBusted()) {
                    result = "lost";
                } else if (dealer.isBusted() || playerValue > dealerValue) {
                    result = "won";
                } else if (playerValue < dealerValue) {
                    result = "lost";
                } else {
                    result = "tied";
                }

                System.out.println(player + " hand: " + player.hand + " (" + player.getHandValue() + ")");
                System.out.println(player + " " + result + " , and the current balance is: " + player.getBalance());
            }

            System.out.println(dealer + " hand: " + dealer.hand + " (" + dealer.getHandValue() + ")");
            System.out.println(dealer + " balance: " + dealer.getBalance());


            System.out.print("Do you want to play again? (y/n): ");
            String input = scanner.next();
            play = input.equalsIgnoreCase("y");
            // If the deck is running low on cards, create a new deck and shuffle.
            if (deck.remainingCards() < 10) {
                deck = new Deck();
            }
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
