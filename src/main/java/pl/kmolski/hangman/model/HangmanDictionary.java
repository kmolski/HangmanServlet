package pl.kmolski.hangman.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Dictionary implementation for hangman.
 *
 * This class is a container for words and supports the following operations:
 * adding new words, taking a random word, checking if the dictionary is empty.
 *
 * @author Krzysztof Molski
 * @version 1.0.5
 */
@Entity
@Table(name = "dictionary_saves")
public class HangmanDictionary {
    /**
     * The identifier of the HangmanDictionary in the database.
     */
    @Id
    @NotNull
    @Column(name="dict_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The default set of words for the dictionary.
     */
    private static final List<String> DEFAULT_WORDS = List.of("koło", "drzwi", "drzewo", "powóz", "pole", "słońce");

    /**
     * An ArrayList that contains the dictionary's words.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotNull
    private final List<String> words;
    /**
     * Random number generator that is used to generate array indices.
     */
    @Transient
    private final Random randomGenerator;
    /**
     * The number of words inside the dictionary.
     */
    @NotNull
    private int wordCount = 0;

    /**
     * Create a new dictionary with the default set of words.
     */
    public HangmanDictionary() {
        words = new ArrayList<>();
        words.addAll(DEFAULT_WORDS);
        wordCount += DEFAULT_WORDS.size();
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
        if (words == null) { return; }
        for (String word : words) {
            this.words.add(word);
            this.wordCount++;
        }
    }

    /**
     * Check whether the dictionary is empty or not.
     * @return true if the dictionary is empty.
     */
    public boolean isEmpty() {
        return words.isEmpty();
    }

    /**
     * Get the number of words inside the dictionary.
     * @return Number of words in the dictionary.
     */
    public int getWordCount() {
        return wordCount;
    }
}
