import java.util.*;

public class DiceGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TWO-PLAYER DICE GAME ===");

        int player1Score = playTurn(scanner, "Player 1");
        int player2Score = playTurn(scanner, "Player 2");

        System.out.println("\n=== FINAL RESULTS ===");
        System.out.println("Player 1 Score: " + player1Score);
        System.out.println("Player 2 Score: " + player2Score);

        if(player1Score > player2Score) {
            System.out.println("Winner: Player 1!");
        } else if (player2Score > player1Score) {
            System.out.println("Winner: Player 2!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // Play one player's full 3-roll turn
    private static int playTurn(Scanner scanner, String playerName) {
        System.out.println("\n--- " + playerName + " Turn ---");

        int[] dice = new int[5];
        boolean[] keep = new boolean[5];

        // Up to three rolls
        for(int roll = 1; roll <= 3; roll++) {
            System.out.println("\nRoll #" + roll);

            // Roll any dice not marked "keep"
            rollDice(dice, keep);

            // Sort and display
            Arrays.sort(dice);
            System.out.println("Current Dice: " + Arrays.toString(dice));

            // Show probability score of improvement
            showProbabilityScore(dice);

            if (roll == 3) {
                System.out.println("No re-rolls left.");
                break;
            }

            // Ask player which dice to re-roll
            System.out.println("Enter dice positions to re-roll (1-5), seperated by spaces");
            System.out.println("Or press ENTER to keep all:");

            Arrays.fill(keep, true); //assume keep everything

            String input = scanner.nextLine().trim();
            if(!input.isEmpty()) {
                Arrays.fill(keep, true);
                for (String s : input.split(" ")) {
                    try {
                        int pos = Integer.parseInt(s) - 1;
                        if (pos >= 0 && pos < 5) {
                            keep[pos] = false;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        // Calculate final score = sum of dice
        int total = 0;
        for (int d : dice) total += d;

        System.out.println(playerName + " Final Dice: " + Arrays.toString(dice));
        System.out.println(playerName + " Score: " + total);

        return total;
    }

    // Rolls only dice that are NOT flagged to keep
    private static void rollDice(int[] dice, boolean[] keep) {
        Random rand = new Random();
        for(int i = 0; i < dice.length; i++) {
            if (!keep[i]) {
                dice[i] = rand.nextInt(6) + 1;
            }
        }
    }

    // Simple "probability score" estimating improvement potential
    private static void showProbabilityScore(int[] dice) {
        int sum = 0;
        for (int d : dice) sum += d;

        // Rough heuristic: Lower sum = higher chance  of improvement
        double probability = Math.max(0, (30 -sum) / 24.0);

        System.out.printf("Probability of Improvement: %.2f%%\n", probability * 100);
    }
}