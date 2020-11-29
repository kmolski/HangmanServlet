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
 * Controller implementation for the WinScene view.
 *
 * This class is responsible for updating the relevant information in the
 * WinScene view, which is shown to the user after they win a round.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class WinSceneController implements Initializable {
    /**
     * A label in which the hangman is drawn.
     */
    @FXML
    private Label hangmanLabel;

    /**
     * A label that describes what the current word was.
     */
    @FXML
    private Label wordLabel;

    /**
     * Event handler for the `Continue` button.
     */
    @FXML
    private void continueClicked() {
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
     * Initializer function for the WinScene view.
     * @param url Location used to resolve relative paths.
     * @param resourceBundle Resources used for the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HangmanGame model = JfxApplication.getModel();
        hangmanLabel.setText(HANGMEN.get(model.getMisses()));
        wordLabel.setText("The word was: " + model.getCurrentWord() + ".");
    }
}
