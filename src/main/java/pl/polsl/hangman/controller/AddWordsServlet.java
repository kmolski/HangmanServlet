package pl.polsl.hangman.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.polsl.hangman.model.HangmanGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@WebServlet(name = "AddWordsServlet")
@MultipartConfig(maxFileSize = 4096, maxRequestSize = 6144)
public class AddWordsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        HangmanGame model = (HangmanGame) session.getAttribute("model");
        if (session.getAttribute("model") == null) {
            // TODO: Error out!
        }

        Part wordFilePart = request.getPart("wordFile");
        if (wordFilePart == null) {
            // TODO: Error out!
        }

        ArrayList<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(wordFilePart.getInputStream());
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        model.addWords(lines);

        response.sendRedirect("Home");
    }
}
