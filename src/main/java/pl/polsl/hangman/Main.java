package pl.polsl.hangman;

import javafx.application.Application;

/**
 * This class contains the program's entry point.
 *
 * @author Krzysztof Molski
 * @version 1.0.3
 */
public class Main {
    /**
     * This method is the entry point of the program.
     * @param args Names of the files with additional words.
     */
    public static void main(String[] args) {
        Application.launch(JfxApplication.class, args);
    }
}
