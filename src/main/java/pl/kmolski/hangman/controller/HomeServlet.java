package pl.kmolski.hangman.controller;

import pl.kmolski.hangman.model.HangmanDictionary;
import pl.kmolski.hangman.model.HangmanGame;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation for the Home page.
 *
 * This servlet is responsible for displaying information about the current state
 * (miss count, the current word, etc.) of the game. It is located under "/Home".
 *
 * @author Krzysztof Molski
 * @version 1.0.1
 */
@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {
    /**
     * Display the main screen of the game. Information about the current word
     * and the miss count is displayed along with the relevant controls. If there's
     * no model instance in the current session, a new instance is created.
     * @param request The HTTP request.
     * @param response The response (an HTML page).
     * @throws IOException May be thrown if sending the redirect or creating the PrintWriter fails.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");

        if (model == null || model.isGameOver()) {
            HangmanDictionary dictionary = new HangmanDictionary();
            model = new HangmanGame(dictionary);
            model.reset();
            model.saveGame();

            session.setAttribute("model", model);
            response.sendRedirect("add_words.html");
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
            out.println("<div class=\"row\">");
            out.println("<div class=\"col-sm\">");
            out.println("<h1>Welcome to the game!</h1> <br/>");
            out.println("The word is: " + model.getMaskedWord() + "<br/>");
            out.println("You have missed " + model.getMisses() + " times. <br/> <br/>");
            out.println("<form action=\"SubmitGuess\" method=\"post\">");
            out.println("Enter your guess: ");
            out.println("<input class=\"form-control my-2 w-25\" type=\"text\" minlength=\"1\" maxlength=\"1\" autocomplete=\"off\" name=\"guess\" id=\"name\" required autofocus>");
            out.println("<button class=\"btn btn-primary my-2\" type=\"submit\">Try guess</button>");
            out.println("<a href=\"SkipWord\" class=\"btn btn-secondary m-2\" role=\"button\">Skip word</a>");
            out.println("<a href=\"Stats\" class=\"btn btn-secondary my-2\" role=\"button\">Game stats</a>");
            out.println("</form>");
            out.println("</div>");
            out.println("<div class=\"col-sm\">");
            out.println("<img src=\"images/" + model.getMisses() + ".png\">");
            out.println("</div>");
            out.println("</div>");
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
