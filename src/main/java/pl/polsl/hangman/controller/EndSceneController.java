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
 * Controller implementation for the EndScene view.
 *
 * This class is responsible for updating the relevant information in the
 * EndScene view, which is shown to the user after they finish a game.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class EndSceneController implements Initializable {
    /**
     * This field holds the last word that was being guessed.
     */
    private static String lastWord = "";

    /**
     * A label in which the hangman is drawn.
     */
    @FXML
    private Label hangmanLabel;

    /**
     * A label used to display the result of the game.
     */
    @FXML
    private Label statusLabel;

    /**
     * A label that is used to display the word that was being guessed.
     */
    @FXML
    private Label wordLabel;

    /**
     * Set the `lastWord` class field to the provided word.
     * @param word A single word.
     */
    public static void setLastWord(String word) {
        lastWord = word;
    }

    /**
     * Event handler for the `Show scores` button.
     */
    @FXML
    private void showScoresClicked() {
        JfxApplication.setRoot("/HighScoresScene.fxml");
    }

    /**
     * Event handler for the `Quit` button.
     */
    @FXML
    private void quitClicked() {
        Platform.exit();
    }

    /**
     * Initializer function for the EndScene view.
     * @param url Location used to resolve relative paths.
     * @param resourceBundle Resources used for the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        statusLabel.setText(model.didWin() ? "Congratulations! You've won the game!"
                                           : "You've lost the game!");
        wordLabel.setText(model.didWin() ? "" : ("The word was: " + lastWord + "."));
    }
}
