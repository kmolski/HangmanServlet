package pl.polsl.hangman.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;

@WebServlet(name = "SkipWordServlet")
public class SkipWordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");
        if (session.getAttribute("model") == null) {
            // TODO: Error out!
        }

        model.reset();

        if (model.isGameOver()) {
            response.sendRedirect("game_lost.html");
        } else {
            response.sendRedirect("Home");
        }
    }
}
