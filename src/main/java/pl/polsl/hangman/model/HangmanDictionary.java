package pl.polsl.hangman.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Dictionary implementation for hangman.
 *
 * This class is a container for words and supports the following operations:
 * adding new words, taking a random word, checking if the dictionary is empty.
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
public class HangmanDictionary {
    /**
     * The default set of words for the dictionary.
     */
    private static final String[] DEFAULT_WORDS = { "koło", "drzwi", "drzewo", "powóz", "pole", "słońce" };

    /**
     * An ArrayList that contains the dictionary's words.
     */
    private final ArrayList<String> words;
    /**
     * Random number generator that is used to generate array indices.
     */
    private final Random randomGenerator;

    /**
     * Create a new dictionary with the default set of words.
     */
    public HangmanDictionary() {
        words = new ArrayList<>();
        Collections.addAll(words, DEFAULT_WORDS);
        randomGenerator = new Random();
    }

    /**
     * Pick a random word (the selected word is removed from the dictionary).
     * @return A random word from the dictionary.
     */
    public String takeWord() {
        if (words.isEmpty()) {
            return null;
        } else {
            int randomIndex = randomGenerator.nextInt(words.size());
            String word = words.get(randomIndex);
            words.remove(randomIndex);
            return word;
        }
    }

    /**
     * Add new words to the dictionary. Duplicates are not removed.
     * @param words A collection of words to be added.
     */
    public void addWords(Collection<String> words) {
        this.words.addAll(words);
    }

    /**
     * Check whether the dictionary is empty or not.
     * @return true if the dictionary is empty.
     */
    public boolean isEmpty() {
        return words.isEmpty();
    }
}
