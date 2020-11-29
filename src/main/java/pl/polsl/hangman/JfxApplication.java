package pl.polsl.hangman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.hangman.controller.EndSceneController;
import pl.polsl.hangman.model.HangmanDictionary;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;

public class JfxApplication extends Application {
    private static HangmanGame model;
    private static Scene scene;

    private static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(JfxApplication.class.getResource(fxml));
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static HangmanGame getModel() {
        return model;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HangmanDictionary dictionary = new HangmanDictionary();
        model = new HangmanGame(dictionary);
        model.reset();
        EndSceneController.setLastWord(model.getCurrentWord());

        try {
            scene = new Scene(loadFXML("/MainScene.fxml"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("hangman");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
