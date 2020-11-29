package pl.polsl.hangman.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import pl.polsl.hangman.JfxApplication;
import pl.polsl.hangman.model.HangmanGame;
import pl.polsl.hangman.model.InvalidGuessException;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.polsl.hangman.view.JfxUI.HANGMEN;

/**
 * Controller implementation for the main view.
 *
 * This class is responsible for updating the relevant information in the main view.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class MainSceneController implements Initializable {
    /**
     * A label in which the hangman is drawn.
     */
    @FXML
    private Label hangmanLabel;

    /**
     * A label that is used to display the current word in the masked form.
     */
    @FXML
    private Label maskedWordLabel;

    /**
     * A label that is used to display information about the miss count.
     */
    @FXML
    private Label missCountLabel;

    /**
     * A text field used for receiving guesses from the user.
     */
    @FXML
    private TextField guessTextField;

    /**
     * A label that is used to inform the user about any errors.
     */
    @FXML
    private Label errorLabel;

    /**
     * Event handler for the `Try guess` button.
     */
    @FXML
    private void tryGuessClicked() {
        HangmanGame model = JfxApplication.getModel();
        String guessText = guessTextField.getText();

        try {
            boolean isGuessCorrect = JfxApplication.getModel().tryLetter(guessText);
            GuessSceneController.setGuessCorrect(isGuessCorrect);
            if (model.isGameOver()) {
                JfxApplication.setRoot("/EndScene.fxml");
            } else if (model.isRoundOver()) {
                JfxApplication.setRoot("/WinScene.fxml");
            } else {
                JfxApplication.setRoot("/GuessScene.fxml");
            }
        } catch (InvalidGuessException e) {
            errorLabel.setText("The guess must be exactly 1 letter long!");
        }
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
    private void quitClicked() {
        Platform.exit();
    }

    /**
     * Initializer function for the MainScene view.
     * @param url Location used to resolve relative paths.
     * @param resourceBundle Resources used for the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        maskedWordLabel.setText("The word is " + model.getMaskedWord() + ".");
        missCountLabel.setText("You have missed " + model.getMisses() + " times so far.");

        guessTextField.setTextFormatter(new TextFormatter<Change>(change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 1) {
                    errorLabel.setText("The guess must be exactly 1 letter long!");
                    return null;
                }
            }
            errorLabel.setText("");
            return change;
        }));

        errorLabel.setText("");
    }
}
