package pl.polsl.hangman.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.BreakIterator;

import static org.junit.jupiter.api.Assertions.*;

public class HangmanGameTest {
    HangmanGame model;

    @BeforeEach
    void setUp() {
        HangmanDictionary dictionary = new HangmanDictionary();
        model = new HangmanGame(dictionary);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testReset() {
        // The model must always be reset before use.
        model.reset();
        String maskedWord = model.getMaskedWord();

        assertDoesNotThrow(() -> {
            model.tryLetter("a");
            // We could've missed - in which case `misses` is greater than zero,
            // or guessed correctly - which means that the current `maskedWord` is different.
            assertTrue(model.getMisses() > 0 || !model.getMaskedWord().equals(maskedWord));

            model.reset();
            // After resetting the model, the incorrect guess count must be zero,
            // and the `maskedWord` must contain only `_` characters.
            assertTrue(model.getMisses() == 0 && model.getMaskedWord().matches("_*"));
        });
    }

    @Test
    void testWordMasking() {
        // The model must always be reset before use.
        model.reset();

        // At this point, the masked word should consist of `_` characters only.
        assertTrue(model.getMaskedWord().matches("_*"));

        String currentWord = model.getCurrentWord();
        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(currentWord);
        String firstLetter = currentWord.substring(0, it.next());

        assertDoesNotThrow(() -> {
            // Because the first letter of `currentWord` was used, the result of `tryLetter` should be true.
            assertTrue(model.tryLetter(firstLetter));
            // The masked word should now consist of the first letter of `currentWord` and `_` letters.
            assertTrue(model.getMaskedWord().matches("[" + firstLetter + "_]*"));
        });
    }

    @Test
    void testRoundOverCondition() {
        // The model must always be reset before use.
        model.reset();

        String currentWord = model.getCurrentWord();
        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(currentWord);

        assertDoesNotThrow(() -> {
            // Iterate over the letters of the current word.
            int start = it.first();
            for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
                // `tryLetter` should always return true, as the letters are taken directly from `currentWord`.
                assertTrue(model.tryLetter(currentWord.substring(start, end)));
            }
        });

        // After guessing all the letters correctly, we should have won the round.
        assertTrue(model.isRoundOver());
    }

    @Test
    void testGameOverConditions() {
    }

    @Test
    void testTryLetter() {
    }

    @Test
    void testGuessLengthEnforcement() {
        // The model must always be reset before use.
        model.reset();

        // Guesses longer than 1 letter are rejected.
        assertThrows(GuessTooLongException.class, () -> model.tryLetter("long"));
        assertThrows(GuessTooLongException.class, () -> model.tryLetter("excessive"));
        assertThrows(GuessTooLongException.class, () -> model.tryLetter("wrong"));

        // Guesses that are exactly 1 letter long are accepted.
        assertDoesNotThrow(() -> model.tryLetter("l"));
        assertDoesNotThrow(() -> model.tryLetter("e"));
        assertDoesNotThrow(() -> model.tryLetter("w"));
    }
}