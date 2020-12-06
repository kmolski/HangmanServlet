package pl.polsl.hangman.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.polsl.hangman.model.HangmanGame;
import pl.polsl.hangman.model.InvalidGuessException;

import java.io.IOException;

@WebServlet(name = "SubmitGuessServlet")
public class SubmitGuessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");
        if (session.getAttribute("model") == null) {
            // TODO: Error out!
        }

        String guess = request.getParameter("guess");
        if (guess == null) {
            // TODO: Error out!
        }

        try {
            boolean isGuessCorrect = model.tryLetter(guess);

            if (model.isGameOver() && model.didWin()) {
                response.sendRedirect("game_won.html");
            } else if (model.isGameOver()) {
                response.sendRedirect("game_lost.html");
            } else if (model.isRoundOver()) {
                model.reset();
                response.sendRedirect("round_over.html");
            } else if (isGuessCorrect) {
                response.sendRedirect("guess_correct.html");
            } else {
                response.sendRedirect("guess_wrong.html");
            }
        } catch (InvalidGuessException e) {
            // TODO: Error out!
        }
    }
}
