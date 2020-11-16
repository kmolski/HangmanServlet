package pl.polsl.hangman;

/**
 * Interface for the view implementations for hangman.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public interface HangmanGameView {
    /**
     * Prompt the user for the name of a file with additional words.
     * @return Name of the word file.
     */
    String filenamePrompt();

    /**
     * Prompt the user for an action - quit, restart or try to guess a letter.
     * @param maskedWord The current word with secret letters masked out.
     * @param misses     Incorrect guess count.
     * @return A non-empty String that contains the user's response.
     */
    String guessPrompt(String maskedWord, int misses);

    /**
     * Print some information after the user has made a guess. The user is informed
     * about the number of incorrect guesses and whether their last guess was correct.
     * @param isGuessCorrect true if the guess was correct.
     * @param misses         Number of misses.
     */
    void printGuessScreen(boolean isGuessCorrect, int misses);

    /**
     * Print the word that was being guessed after the user had won the round.
     * @param currentWord The word that was being guessed.
     */
    void printWinScreen(String currentWord);

    /**
     * Print the "Game Over" screen.
     * @param didWin      true if the player had won the game.
     * @param currentWord The last word that was being guessed.
     */
    void printEndScreen(boolean didWin, String currentWord);

    /**
     * Inform the user that an error has occurred while reading a file
     * and the internal dictionary will be used instead.
     * @param filename Name of the file that was being opened.
     */
    void printFileError(String filename);
}
