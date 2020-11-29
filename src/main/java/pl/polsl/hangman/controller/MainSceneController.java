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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static pl.polsl.hangman.view.JfxUI.HANGMEN;

public class MainSceneController implements Initializable {
    @FXML
    private Label hangmanLabel;

    @FXML
    private Label maskedWordLabel;

    @FXML
    private Label missCountLabel;

    @FXML
    private TextField guessTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private void tryGuessClicked() throws IOException {
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

    @FXML
    private void skipWordClicked() throws IOException {
        HangmanGame model = JfxApplication.getModel();
        model.reset();
        if (model.isGameOver()) {
            JfxApplication.setRoot("/EndScene.fxml");
        } else {
            EndSceneController.setLastWord(model.getCurrentWord());
            JfxApplication.setRoot("/MainScene.fxml");
        }
    }

    @FXML
    private void quitClicked() {
        Platform.exit();
    }

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
