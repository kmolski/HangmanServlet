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

public class EndSceneController implements Initializable {
    private static String lastWord = "";

    @FXML
    private Label hangmanLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label wordLabel;

    public static void setLastWord(String word) {
        lastWord = word;
    }

    @FXML
    private void quitClicked () {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        statusLabel.setText(model.didWin() ? "Congratulations! You've won the game!"
                                           : "You've lost the game!");
        wordLabel.setText(model.didWin() ? "" : ("The word was: " + lastWord + "."));
    }
}
