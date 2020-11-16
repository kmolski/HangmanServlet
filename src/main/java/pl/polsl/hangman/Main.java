package pl.polsl.hangman;

import pl.polsl.hangman.controller.HangmanGameController;
import pl.polsl.hangman.model.HangmanDictionary;
import pl.polsl.hangman.model.HangmanGame;
import pl.polsl.hangman.view.ConsoleUI;

import java.io.InputStreamReader;

/**
 * This class contains the program's entry point.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class Main {
    /**
     * This method is the entry point of the program.
     * @param args Program arguments. The first argument is the name of a file with additional words.
     */
    public static void main(String[] args) {
        InputStreamReader reader = new InputStreamReader(System.in);
        ConsoleUI view = new ConsoleUI(reader);

        HangmanDictionary dictionary = new HangmanDictionary();

        HangmanGame game = new HangmanGame(dictionary);
        HangmanGameController controller = new HangmanGameController(game, view);
        controller.run(args);
    }
}
