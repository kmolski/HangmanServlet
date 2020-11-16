package pl.polsl.hangman.model;

/**
 * An exception that is thrown when the guess is not a single letter.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class GuessTooLongException extends Exception {
    /**
     * Create a new GuessTooLongException for the incorrect guess.
     * @param guess The guess taken from the user.
     */
    public GuessTooLongException(String guess) {
        super(guess);
    }
}
