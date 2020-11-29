package pl.polsl.hangman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.polsl.hangman.controller.EndSceneController;
import pl.polsl.hangman.model.HangmanDictionary;
import pl.polsl.hangman.model.HangmanGame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is an extension of the JavaFX Application class, and is responsible
 * for initializing and providing useful methods to the rest of the application.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class JfxApplication extends Application {
    /**
     * The model instance for the game.
     */
    private static HangmanGame model;
    /**
     * The main scene of the GUI.
     */
    private static Scene scene;

    /**
     * Load a widget from an FXML file at the provided path.
     * @param fxml Path to the FXML file.
     * @return A Parent widget.
     * @throws IOException May be thrown if the file could not be read.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(JfxApplication.class.getResource(fxml));
    }

    /**
     * Set a widget from an FXML file at the provided path as the root of the main scene.
     * @param fxml Path to the FXML file.
     */
    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            showExceptionAlert(e);
        }
    }

    /**
     * Get the HangmanGame model.
     * @return The game's model.
     */
    public static HangmanGame getModel() {
        return model;
    }

    /**
     * This method is the entry point of the application.
     * @param args Names of the files with additional words.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Get the names of additional words files in case they were not provided
     * in the command-line arguments. Uses Alert and FileChooser windows.
     * @param stage The parent stage of the file open dialog.
     * @return A list of file names.
     */
    private List<String> getFilenamesInDialog(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Add words from a file?");
        alert.setContentText("No dictionary files were provided in the program arguments.\n" +
                "Would you like to add them now?");

        alert.setResizable(true);
        alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));

        Optional<ButtonType> dialogResult = alert.showAndWait();

        if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add dictionary files");
            List<File> result = fileChooser.showOpenMultipleDialog(stage);
            return (result != null) ? result.stream().map(File::getAbsolutePath).collect(Collectors.toList())
                                    : List.of();
        } else {
            return List.of();
        }
    }

    /**
     * Show an exception Alert with the exception description.
     * @param e The exception to be described.
     */
    public static void showExceptionAlert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("An exception has occurred.");
        alert.setContentText(e.toString());

        alert.setResizable(true);
        alert.onShownProperty().addListener(ev -> Platform.runLater(() -> alert.setResizable(false)));

        alert.show();
    }

    /**
     * Start the application.
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        HangmanDictionary dictionary = new HangmanDictionary();

        model = new HangmanGame(dictionary);
        model.reset();
        EndSceneController.setLastWord(model.getCurrentWord());

        ArrayList<String> args = new ArrayList<>(getParameters().getRaw());
        if (args.isEmpty()) {
            args.addAll(getFilenamesInDialog(primaryStage));
        }
        args.forEach(filename -> {
            try (Stream<String> lines = Files.lines(Paths.get(filename))) {
                model.addWords(lines.collect(Collectors.toList()));
            } catch (IOException e) {
                showExceptionAlert(e);
            }
        });

        try {
            scene = new Scene(loadFXML("/MainScene.fxml"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("hangman");
            primaryStage.show();
        } catch (IOException e) {
            showExceptionAlert(e);
        }
    }

}
