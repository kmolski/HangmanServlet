package pl.polsl.hangman.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.polsl.hangman.JfxApplication;
import pl.polsl.hangman.model.HangmanGame;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.polsl.hangman.view.JfxUI.HANGMEN;

/**
 * Controller implementation for the GuessScene view.
 *
 * This class is responsible for updating the relevant information in the
 * GuessScene view, which is shown to the user after they make a guess.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class GuessSceneController implements Initializable {
    /**
     * This class field is used to communicate the guess result between
     * this controller and the MainScene controller.
     */
    private static boolean guessCorrect = false;

    /**
     * A label in which the hangman is drawn.
     */
    @FXML
    private Label hangmanLabel;

    /**
     * A label that is used to display information about the correctness of the guess.
     */
    @FXML
    private Label guessCorrectLabel;

    /**
     * A label that is used to display information about the miss count.
     */
    @FXML
    private Label missCountLabel;

    /**
     * Set the `guessCorrect` class field to the result of a guess.
     * @param isGuessCorrect The result of a guess.
     */
    public static void setGuessCorrect(boolean isGuessCorrect) {
        GuessSceneController.guessCorrect = isGuessCorrect;
    }

    /**
     * Event handler for the `Continue` button.
     */
    @FXML
    private void continueClicked() {
        JfxApplication.setRoot("/MainScene.fxml");
    }

    /**
     * Event handler for the `Skip word` button.
     */
    @FXML
    private void skipWordClicked() {
        HangmanGame model = JfxApplication.getModel();
        model.reset();
        if (model.isGameOver()) {
            JfxApplication.setRoot("/EndScene.fxml");
        } else {
            EndSceneController.setLastWord(model.getCurrentWord());
            JfxApplication.setRoot("/MainScene.fxml");
        }
    }

    /**
     * Event handler for the `Quit` button.
     */
    @FXML
    private void quitClicked () {
        Platform.exit();
    }

    /**
     * Initializer function for the GuessScene view.
     * @param url Location used to resolve relative paths.
     * @param resourceBundle Resources used for the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        guessCorrectLabel.setText("Your guess was " + (guessCorrect ? "correct" : "wrong") + ".");
        missCountLabel.setText("You have missed " + model.getMisses() + " times so far.");
    }
}
