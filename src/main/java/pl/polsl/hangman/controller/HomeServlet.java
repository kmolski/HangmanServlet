package pl.polsl.hangman.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.polsl.hangman.model.HangmanDictionary;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");

        if (model == null || model.isGameOver()) {
            HangmanDictionary dictionary = new HangmanDictionary();
            model = new HangmanGame(dictionary);
            model.reset();

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
            out.println("<h1>Welcome to the game!</h1> <br/>");
            out.println("The word is: " + model.getMaskedWord() + "<br/>");
            out.println("You have missed " + model.getMisses() + " times. <br/>");
            out.println("<form action=\"SubmitGuess\" method=\"post\">");
            out.println("Enter your guess: ");
            out.println("<input class=\"form-control my-2\" type=\"text\" minlength=\"1\" maxlength=\"1\" required=\"required\" name=\"guess\" id=\"name\">");
            out.println("<button class=\"btn btn-primary my-2\" type=\"submit\">Try guess</button>");
            out.println("<a href=\"SkipWord\" class=\"btn btn-secondary m-2\" role=\"button\">Skip word</a>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
