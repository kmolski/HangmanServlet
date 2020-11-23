package pl.polsl.hangman.model;

import pl.polsl.hangman.HangmanGameModel;

import java.text.BreakIterator;
import java.util.Collection;

/**
 * Model implementation for hangman.
 *
 * This class implements most of the game's logic - starting a new round,
 * guessing letters, managing the dictionary and win/lose conditions.
 *
 * @author Krzysztof Molski
 * @version 1.0.3
 */
public class HangmanGame implements HangmanGameModel {
    /**
     * The maximum number of incorrect guesses.
     */
    private static final int MAX_MISSES = 6;

    /**
     * The dictionary from which words will be taken.
     */
    private final HangmanDictionary dictionary;
    /**
     * The word that is currently being guessed.
     */
    private String currentWord;
    /**
     * Letters that have been tried so far.
     */
    private String guessedLetters;
    /**
     * Incorrect guess count.
     */
    private int misses;
    /**
     * Number of words that were guessed correctly.
     */
    private int wordsGuessed = 0;

    /**
     * Create a new model instance for the game, using the provided dictionary.
     * @param dictionary The dictionary that will be used in the game.
     */
    public HangmanGame(HangmanDictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Add new words to the dictionary. Duplicates are not removed.
     * @param words Collection of words to be added.
     */
    public void addWords(Collection<String> words) {
        dictionary.addWords(words);
    }

    /**
     * Start a new round of the game - select a new random word, reset the miss count and guessed letters.
     */
    public void reset() {
        currentWord = dictionary.takeWord();
        guessedLetters = "";
        misses = 0;
    }

    /**
     * Return the word that is being guessed right now, letters that
     * have not been tried so far are replaced with `_` characters.
     * @return The current word with secret characters masked out.
     */
    public String getMaskedWord() {
        if (guessedLetters.length() == 0) {
            return "_".repeat(currentWord.length());
        } else {
            return currentWord.replaceAll("([^" + guessedLetters + "])", "_");
        }
    }

    /**
     * Get the incorrect guess count.
     * @return The number of incorrect guesses.
     */
    public int getMisses() {
        return misses;
    }

    /**
     * Check whether the current word has been guessed.
     * @return true if the current word has been guessed correctly.
     */
    public boolean isRoundOver() {
        return currentWord.replaceAll("([" + guessedLetters + "])", "").equals("");
    }

    /**
     * Check if the game is over (either all words have been guessed, or the player has lost a round)
     * @return true if the game is over.
     */
    public boolean isGameOver() {
        return misses == MAX_MISSES || currentWord == null;
    }

    /**
     * Guess a letter and check if the guess was correct. The guess has to be a single letter.
     * @param guess Guessed character (has to be a single letter).
     * @return true if the guess was correct.
     * @throws InvalidGuessException Thrown if the guess is not a single letter.
     */
    public boolean tryLetter(String guess) throws InvalidGuessException {
        if (guess == null || guess.equals("")) {
            throw new InvalidGuessException("empty or null guess");
        }

        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(guess);
        if (it.next() != it.last()) {
            throw new InvalidGuessException(guess);
        }

        guessedLetters += guess;
        boolean isGuessInWord = currentWord.contains(guess);
        if (!isGuessInWord) {
            ++misses;
        }

        if (isRoundOver()) {
            ++wordsGuessed;
        }
        return isGuessInWord;
    }

    /**
     * Get the word that is currently being guessed.
     * @return The current word.
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Check if the player has won the game through guessing all words correctly.
     * @return true if the player has won the game.
     */
    public boolean didWin() {
        return dictionary.isEmpty() && dictionary.getWordCount() == wordsGuessed;
    }
}
