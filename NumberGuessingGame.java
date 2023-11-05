import java.io.*;
import java.util.*;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int maxAttempts = 10;
        int totalRounds = 3;
        int totalScore = 0;

        ArrayList<Integer> highScores = new ArrayList<>();
        loadHighScores(highScores); // Load high scores from a file

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I will randomly select a number between " + lowerBound + " and " + upperBound + ".");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start Game");
            System.out.println("2. View High Scores");
            System.out.println("3. Quit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    totalScore = playGame(lowerBound, upperBound, maxAttempts, totalRounds, random);
                    if (totalScore > 0) {
                        highScores.add(totalScore);
                        Collections.sort(highScores, Collections.reverseOrder());
                        saveHighScores(highScores); // Save high scores to a file
                    }
                    break;
                case 2:
                    viewHighScores(highScores);
                    break;
                case 3:
                    System.out.println("Thank you for playing!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static int playGame(int lowerBound, int upperBound, int maxAttempts, int totalRounds, Random random) {
        Scanner scanner = new Scanner(System.in);
        int totalScore = 0;

        for (int round = 1; round <= totalRounds; round++) {
            int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;

            System.out.println("\nRound " + round + ":");
            System.out.println("You have " + maxAttempts + " attempts to guess the number.");

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + ": Enter your guess: ");
                int userGuess = scanner.nextInt();

                if (userGuess < lowerBound || userGuess > upperBound) {
                    System.out.println("Please guess a number within the specified range.");
                    continue;
                }

                attempts++;

                if (userGuess == randomNumber) {
                    int roundScore = maxAttempts - attempts + 1;
                    totalScore += roundScore;
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    System.out.println("Round " + round + " Score: " + roundScore);
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("Try a higher number.");
                } else {
                    System.out.println("Try a lower number.");
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry, you've reached the maximum number of attempts. The number was " + randomNumber + ".");
            }
        }

        return totalScore;
    }

    private static void viewHighScores(ArrayList<Integer> highScores) {
        System.out.println("\nHigh Scores:");
        if (highScores.isEmpty()) {
            System.out.println("No high scores yet.");
        } else {
            for (int i = 0; i < highScores.size(); i++) {
                System.out.println((i + 1) + ". " + highScores.get(i));
            }
        }
    }

    private static void loadHighScores(ArrayList<Integer> highScores) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(Integer.parseInt(line));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading high scores.");
        }
    }

    private static void saveHighScores(ArrayList<Integer> highScores) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"));
            for (int score : highScores) {
                writer.write(Integer.toString(score));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving high scores.");
        }
    }
}
