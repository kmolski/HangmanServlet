package pl.polsl.hangman.controller;

import pl.polsl.hangman.HangmanGameModel;
import pl.polsl.hangman.HangmanGameView;
import pl.polsl.hangman.model.InvalidGuessException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller implementation for hangman.
 *
 * This class ties the HangmanGameModel and the HangmanGameView together by
 * communicating with the user, updating the model and managing the game's control flow.
 *
 * @author Krzysztof Molski
 * @version 1.0.3
 */
public class HangmanGameController {
    /**
     * The model instance for the game.
     */
    private final HangmanGameModel model;
    /**
     * The view instance for the game.
     */
    private final HangmanGameView view;

    /**
     * Create a new controller instance for the game, using the provided model and view.
     * @param model The model instance that will be used.
     * @param view  The view instance that will be used.
     */
    public HangmanGameController(HangmanGameModel model, HangmanGameView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Run the hangman game.
     * @param args Command line arguments of the program.
     */
    public void run(String... args) {
        String filename = (args.length > 0) ? args[0] : view.filenamePrompt();

        if (filename != null) {
            try (Stream<String> lines = Files.lines(Paths.get(filename))) {
                model.addWords(lines.collect(Collectors.toList()));
            } catch (IOException e) {
                view.printFileError(filename);
            }
        }

        model.reset();
        while (true) {
            if (model.isGameOver()) {
                break;
            }

            String response = view.guessPrompt(model.getMaskedWord(), model.getMisses());
            if (response.equals("quit")) {
                break;
            } else if (response.equals("restart")) {
                model.reset();
                continue;
            }

            try {
                boolean isGuessCorrect = model.tryLetter(response);
                view.printGuessScreen(isGuessCorrect, model.getMisses());
            } catch (InvalidGuessException e) {
                System.out.println("Your guess was too long!");
                System.out.println();
                continue;
            }

            if (model.isRoundOver()) {
                view.printWinScreen(model.getCurrentWord());
                model.reset();
            }
        }

        view.printEndScreen(model.didWin(), model.getCurrentWord());
    }
}
