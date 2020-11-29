package pl.polsl.hangman.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.polsl.hangman.JfxApplication;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static pl.polsl.hangman.view.JfxUI.HANGMEN;

public class WinSceneController implements Initializable {
    @FXML
    private Label hangmanLabel;

    @FXML
    private Label wordLabel;

    @FXML
    private void continueClicked() throws IOException {
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
    private void quitClicked () {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        wordLabel.setText("The word was: " + model.getCurrentWord() + ".");
    }
}
