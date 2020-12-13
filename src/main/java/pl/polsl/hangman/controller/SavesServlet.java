package pl.polsl.hangman.controller;

import pl.polsl.hangman.model.HangmanGame;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation for the Saves page.
 *
 * This servlet is responsible for displaying information about the
 * game saves that exist in the database. It is located under "/Saves".
 *
 * @author Krzysztof Molski
 * @version 1.0.0
 */
@WebServlet(name = "SavesServlet")
public class SavesServlet extends HttpServlet {
    /**
     * Display information about the game saves that are in the database: the last word that was being
     * guessed, the number of words that were guessed correctly/are remaining and the miss count.
     * @param response The response (an HTML page).
     * @throws IOException May be thrown if sending the redirect or creating the PrintWriter fails.
     */
    private void processRequest(HttpServletResponse response) throws IOException {
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
            out.println("<h1>Existing game saves:</h1> <br/>");
            out.println("<table class=\"table table-bordered table-striped\">");
            out.println("<thead class=\"thead-light\">");
            out.println("<tr> <th scope=\"col\">#</th> <th scope=\"col\">Current word</th>");
            out.println("<th scope=\"col\">Misses</th> <th scope=\"col\">Words guessed</th>");
            out.println("<th scope=\"col\">Words remaining</th> <th/> </tr>");
            out.println("</thead>");
            out.println("<tbody>");
            int i = 0;
            for (HangmanGame model : HangmanGame.getAllGameSaves()) {
                out.println("<tr> <th scope=\"row\">" + (++i) + "</th>");
                out.println("<td>" + model.getMaskedWord() + "</td>");
                out.println("<td>" + model.getMisses() + "</td>");
                out.println("<td>" + model.getWordsGuessed() + "</td>");
                out.println("<td>" + model.getWordsRemaining() + "</td>");
                out.println("<td> <a href=\"LoadSave?id=" + model.getId() +
                            "\" class=\"btn btn-primary\">Load save</a> </td> </tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("<a href=\"index.html\" class=\"btn btn-secondary my-2\" role=\"button\">Go back</a>");
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
        processRequest(response);
    }

    /**
     * Process GET requests from the client.
     * @param request The HTTP request.
     * @param response The response associated with the request.
     * @throws IOException May be thrown if sending the redirect fails.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(response);
    }
}
