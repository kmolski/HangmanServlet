package pl.polsl.hangman.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.polsl.hangman.JfxApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.collections.FXCollections.observableList;

/**
 * Controller implementation for the HighScoresScene view.
 *
 * This class is responsible for updating the relevant information in the
 * HighScoresScene view, which can be seen by the user after they win a game.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class HighScoresSceneController implements Initializable {
    /**
     * File that is used for permanent storage of information about high scores.
     */
    private static File highScoreFile;
    /**
     * List that is used to store information about high scores.
     */
    private static ObservableList<String[]> scores;

    /**
     * A table of high scores, which tracks the `scores` observable list.
     */
    @FXML
    private TableView<String[]> highScoreTable;

    /**
     * A field that is used to receive the user's name.
     */
    @FXML
    private TextField nameField;

    /**
     * Event handler for the `Add score` button.
     */
    @FXML
    private void addScoreClicked() {
        String name = nameField.getText();
        String wordsGuessed = Integer.toString(JfxApplication.getModel().getWordsGuessed());
        scores.add(new String[]{ name, wordsGuessed });

        try (FileOutputStream stream = new FileOutputStream(highScoreFile.getName(), true)) {
            String newLine = name + ";" + wordsGuessed + "\n";
            stream.write(newLine.getBytes());
        } catch (IOException e) {
            JfxApplication.showExceptionAlert(e);
        }
    }

    /**
     * Event handler for the `Go back` button.
     */
    @FXML
    private void goBackClicked() {
        JfxApplication.setRoot("/EndScene.fxml");
    }

    /**
     * Initializer function for the HighScoresScene view.
     * @param url Location used to resolve relative paths.
     * @param resourceBundle Resources used for the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (highScoreFile == null && scores == null) {
            highScoreFile = new File("high_scores.txt");

            try (Stream<String> lines = Files.lines(highScoreFile.toPath())) {
                scores = observableList(lines.map(line -> line.split(";")).collect(Collectors.toList()));
            } catch (IOException e) {
                JfxApplication.showExceptionAlert(e);
            }
        }

        TableColumn<String[], String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> {
            String[] x = data.getValue();
            return new SimpleStringProperty(x[0]);
        });
        TableColumn<String[], String> wordsColumn = new TableColumn<>("Words guessed");
        wordsColumn.setCellValueFactory(data -> {
            String[] x = data.getValue();
            return new SimpleStringProperty(x[1]);
        });

        highScoreTable.getColumns().addAll(nameColumn, wordsColumn);
        highScoreTable.setItems(scores);
    }
}
