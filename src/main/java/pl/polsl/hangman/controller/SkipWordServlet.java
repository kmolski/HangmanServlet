package pl.polsl.hangman.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;

/**
 * Servlet implementation for the SkipWord page.
 *
 * This servlet is responsible for processing users' requests to skip the current word.
 * It is located under "/SkipWord".
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
@WebServlet(name = "SkipWordServlet")
public class SkipWordServlet extends HttpServlet {
    /**
     * Process the word skip request from the client. If there's no model instance
     * in the current session, the client is redirected to HomeServlet.
     * @param request The request that contains the guess.
     * @param response The response associated with the request.
     * @throws IOException May be thrown if sending the redirect fails.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");
        if (model == null) {
            response.sendRedirect("Home");
            return;
        }

        model.reset();

        if (model.isGameOver()) {
            model.deleteGameSave();
            session.removeAttribute("model");

            Cookie cookie = null;
            for (Cookie requestCookie : request.getCookies()) {
                if (requestCookie.getName().equals("loseCount")) {
                    cookie = requestCookie;
                }
            }

            if (cookie != null) {
                int count = Integer.parseInt(cookie.getValue());
                cookie.setValue(Integer.toString(count + 1));
            } else {
                cookie = new Cookie("loseCount", "1");
            }

            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            response.sendRedirect("game_lost.html");
        } else {
            model = model.updateGameSave();
            session.setAttribute("model", model);
            response.sendRedirect("Home");
        }
    }

    /**
     * Process POST requests from the client.
     * @param request The HTTP request.
     * @param response The response associated with the request.
     * @throws IOException May be thrown if sending the redirect fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    /**
     * Process GET requests from the client.
     * @param request The HTTP request.
     * @param response The response associated with the request.
     * @throws IOException May be thrown if sending the redirect fails.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
}
