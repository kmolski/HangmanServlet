package pl.polsl.hangman.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HangmanDictionaryTest {
    HangmanDictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new HangmanDictionary();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testAddWords() {
        List<String> additionalWords = new ArrayList<>(Arrays.asList("ok", "fine", "correct"));
        dictionary.addWords(additionalWords);

        // Drain the dictionary.
        while (!dictionary.isEmpty()) {
            // Remove the word from `additionalWords` when it's taken from the dictionary.
            additionalWords.remove(dictionary.takeWord());
        }

        // `additionalWords` should be empty, because its words were added to `dictionary`
        // and then removed from `additionalWords` as they were encountered in `dictionary`.
        assertTrue(additionalWords.isEmpty());
    }

    @Test
    void testDrainWords() {
        // Take all words from the dictionary.
        while (!dictionary.isEmpty()) {
            // Words taken from a non-empty dictionary _must not_ be null.
            assertNotNull(dictionary.takeWord(), "Got null String from non-empty dictionary!");
        }

        // Words taken from an empty dictionary _must_ be null.
        assertNull(dictionary.takeWord(), "Got non-null String from empty dictionary!");
    }
}