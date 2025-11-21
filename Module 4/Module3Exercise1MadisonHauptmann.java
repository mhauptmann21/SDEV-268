import java.util.HashMap;
import java.util.Scanner;

public class Module3Exercise1MadisonHauptmann {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Ask user for a word
        System.out.print("Enter a word: ");
        String word = scanner.nextLine().toLowerCase();

        // Ask user for a letter
        System.out.print("Enter a letter to count: ");
        char selectedLetter = scanner.nextLine().toLowerCase().charAt(0);

        // Create a HashMap to store letter counts
        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        // Populate the hash table
        for (char c: word.toCharArray()) {
            if (Character.isLetter(c)) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        // Display result
        int count = frequencyMap.getOrDefault(selectedLetter, 0);

        System.out.println("Word: " + word);
        System.out.println("Selected letter: " + selectedLetter);
        System.out.println("Count: " + count);
    }
}
