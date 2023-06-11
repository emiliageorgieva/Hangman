package advanced.hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/advanced/hangman/words.txt"));
        Scanner keyboard = new Scanner(System.in); //another scanner for the user's input

        List<String> words = new ArrayList<String>();
        while (scanner.hasNext()) { //while there is another line to get
            words.add(scanner.nextLine());
        }
        Random rand = new Random();

//get the word (the picked word) in one line
        String word = words.get(rand.nextInt(words.size())); //pick one word from the list of words.txt with index between 0 and the size of the words.txt List

        //System.out.println(word);

        List<Character> playerGuesses = new ArrayList<>(); //list for the guessed letters to replace the dashes


        Integer wrongCount = 0;
        while (true) {

            printHangedMan(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The word was: " + word);
                break;
            }

            printWordState(word, playerGuesses);
            if (!getPlayerGuess(keyboard, word, playerGuesses)) {
                wrongCount++;
            }
            if (printWordState(word, playerGuesses)) { //letter should replace the dash
                System.out.println("You win!");
                break; //if the player has won = end the game
            }
            System.out.println("Please enter your guess for the word.");
            if(keyboard.nextLine().equals(word)) { //if player guesses th right word, end the game
                System.out.println("You win!");
                break;
            } else {
                System.out.println("Nope! Try again.");
            }
        }
    }

    private static void printHangedMan(Integer wrongCount) {
        System.out.println("  --------------");
        System.out.println("    |         |");
        if (wrongCount >= 1) {
            System.out.println("    O");
        }
        if (wrongCount >= 2) {
            System.out.print("   \\");
            if (wrongCount >= 3) {
                System.out.println(" /");
            } else {
                System.out.println("");
            }
        }
        if (wrongCount >= 4) {
            System.out.println("    |");
        }
        if (wrongCount >= 5) {
            System.out.print("   /");
            if (wrongCount >= 6) {
                System.out.println(" \\");
            } else {
                System.out.println("");
            }
        }
        System.out.println("");
        System.out.println("");
    }

    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) {
        System.out.println("Please enter a letter: ");
        String letterGuess = keyboard.nextLine(); //gets the next string input from the user
        playerGuesses.add(letterGuess.charAt(0)); //get just the first letter if the user is trying to type more than one letter at once

    return word.contains(letterGuess);
    }

    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) { //from beginning to end of the word
//if the player has guessed a letter - print it, if not - print "_"
            if (playerGuesses.contains(word.charAt(i))) { //if the player has guessed the character at the location I am currently working at
                System.out.print(word.charAt(i)); // NOT println, print the actual character
            correctCount++; //increment when the player guesses a word
            } else {
                System.out.print("-");
            }
        }
        System.out.println();

        return (word.length() == correctCount); // true if the player has already guessed all the letters in the word
    }
}
