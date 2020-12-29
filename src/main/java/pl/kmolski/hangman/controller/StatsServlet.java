package pl.kmolski.hangman.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import pl.kmolski.hangman.model.HangmanGame;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation for the Stats page.
 *
 * This servlet is responsible for displaying information about the current state
 * and history of the games. It is located under "/Stats".
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
@WebServlet(name = "Stats", urlPatterns = "/Stats")
public class StatsServlet extends HttpServlet {
    /**
     * Find the appropriate cookie and return its value. If the cookie
     * does not exist, the string "0" is returned instead.
     * @param request The request that contains the cookie.
     * @param cookieName The name of the cookie.
     * @return The value of the cookie.
     */
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return "0";
    }

    /**
     * Display information about the total number of wins/losses, correct/wrong guesses
     * number of words that were guessed correctly/are remaining and the miss count.
     * If there's no model instance in the current session, the client is redirected to HomeServlet.
     * @param request The HTTP request.
     * @param response The response (an HTML page).
     * @throws IOException May be thrown if sending the redirect or creating the PrintWriter fails.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");

        if (model == null) {
            response.sendRedirect("Home");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
            out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
            out.println("<title>hangman</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<header class=\"navbar navbar-dark bg-dark\">");
            out.println("<div class=\"navbar-brand\">hangman</div>");
            out.println("</header>");
            out.println("<div class=\"container my-4\">");
            out.println("<h1>Statistics from all games:</h1> <br/>");
            out.println("You have won " + getCookieValue(request, "winCount") + " games. <br/>");
            out.println("You have lost " + getCookieValue(request, "loseCount") + " games. <br/>");
            out.println("You have made " + getCookieValue(request, "correctGuesses") + " correct guesses. <br/>");
            out.println("You have made " + getCookieValue(request, "wrongGuesses") + " wrong guesses. <br/> <br/>");
            out.println("<h1>Statistics from the current game:</h1> <br/>");
            out.println("You have guessed " + model.getWordsGuessed() + " words correctly. <br/>");
            out.println("There are " + model.getWordsRemaining() + " words left. <br/>");
            out.println("You have missed " + model.getMisses() + " times in the current round. <br/> <br/>");
            out.println("<a href=\"Home\" class=\"btn btn-primary my-2\" role=\"button\">Go back</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
