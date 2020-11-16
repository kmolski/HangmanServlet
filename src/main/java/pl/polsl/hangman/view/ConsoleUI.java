package pl.polsl.hangman.view;

import pl.polsl.hangman.HangmanGameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * View implementation for hangman.
 *
 * This class provides a CLI-based interface for the game.
 * `System.out` is used as the output stream for all communication.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class ConsoleUI implements HangmanGameView {
    /**
     * A set of hangman pictures for different miss counts (from 0 to 6).
     */
    private static final String[] HANGMEN = {
            "         =+==========||\n"   +
            "          |     \\\\   ||\n" +
            "          |      \\\\  ||\n" +
            "                  \\\\ ||\n" +
            "                   \\\\||\n" +
            "                    \\||\n"  +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "                     ||\n"   +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "          |          ||\n"    +
            "          |          ||\n"    +
            "          |          ||\n"    +
            "          |          ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "          |          ||\n"    +
            "          |\\         ||\n"   +
            "          | \\        ||\n"   +
            "          |          ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "          |          ||\n"    +
            "         /|\\         ||\n"   +
            "        / | \\        ||\n"   +
            "          |          ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "                     ||\n"    +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "          |          ||\n"    +
            "         /|\\         ||\n"   +
            "        / | \\        ||\n"   +
            "          |          ||\n"    +
            "           \\         ||\n"   +
            "            \\        ||\n"   +
            "             \\       ||\n"   +
            "                     ||\n"    +
            "=======================\n",

            "         =+==========||\n"    +
            "          |     \\\\   ||\n"  +
            "          |      \\\\  ||\n"  +
            "         ---      \\\\ ||\n"  +
            "        /   \\      \\\\||\n" +
            "        \\___/       \\||\n"  +
            "          |          ||\n"    +
            "         /|\\         ||\n"   +
            "        / | \\        ||\n"   +
            "          |          ||\n"    +
            "         / \\         ||\n"   +
            "        /   \\        ||\n"   +
            "       /     \\       ||\n"   +
            "                     ||\n"    +
            "=======================\n"
    };

    /**
     * BufferedReader used for receiving user input.
     */
    private final BufferedReader reader;

    /**
     * Create a CLI view using the provided reader.
     * @param reader The reader that will be used to receive user input.
     */
    public ConsoleUI(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    /**
     * Prompt the user for the name of a file with additional words.
     * @return Name of the word file.
     */
    public String filenamePrompt() {
        try {
            System.out.println("Do you want to load words from a file? [y/N]");
            String response = reader.readLine();
            if (response.toLowerCase().startsWith("y")) {
                System.out.println("Enter the file name: ");
                return reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Could not read file name, using internal dictionary.");
        }

        return null;
    }

    /**
     * Prompt the user for an action - quit, restart or try to guess a letter.
     * @param maskedWord The current word with secret letters masked out.
     * @param misses     Incorrect guess count.
     * @return A non-empty String that contains the user's response.
     */
    public String guessPrompt(String maskedWord, int misses) {
        System.out.println("The word is " + maskedWord);
        System.out.println("Enter a letter (or quit/restart): ");

        while (true) {
            try {
                String response = reader.readLine();
                if (response.length() != 0) {
                    return response;
                }
            } catch (IOException e) {
                System.out.println("Could not read the letter, please enter it again:");
            }
        }
    }

    /**
     * Print some information after the user has made a guess. The user is informed
     * about the number of incorrect guesses and whether their last guess was correct.
     * @param isGuessCorrect true if the guess was correct.
     * @param misses         Number of misses.
     */
    public void printGuessScreen(boolean isGuessCorrect, int misses) {
        System.out.println(HANGMEN[misses]);
        System.out.println(isGuessCorrect ? "Your guess was correct!" : "Your guess was wrong!");
        System.out.println("You have missed " + misses + " times so far.");
        System.out.println();
    }

    /**
     * Print the word that was being guessed after the user had won the round.
     * @param currentWord The word that was being guessed.
     */
    public void printWinScreen(String currentWord) {
        System.out.println("You've won this round! The word was: " + currentWord);
        System.out.println();
    }

    /**
     * Print the "Game Over" screen.
     * @param didWin      true if the player had won the game.
     * @param currentWord The last word that was being guessed.
     */
    public void printEndScreen(boolean didWin, String currentWord) {
        System.out.println(didWin ? "Congratulations, you've guessed all of my words!"
                                  : ("You've lost! The word was: " + currentWord));
        System.out.println();
    }

    /**
     * Inform the user that an error has occurred while reading a file
     * and the internal dictionary will be used instead.
     * @param filename Name of the file that was being opened.
     */
    public void printFileError(String filename) {
        System.out.println("Could not read file `" + filename + "`, using internal dictionary.");
    }
}
