package pl.kmolski.hangman;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Application class for the web-based hangman.
 *
 * This class provides a way to initialize and access the
 * Thymeleaf template engine from other classes.
 *
 * @author Krzysztof Molski
 * @version 1.0.0
 */
public class HangmanApplication {
    /**
     * The Thymeleaf template resolver for the application.
     */
    private static final TemplateEngine templateEngine;

    /**
     * This is an application class - it should not be instantiated.
     */
    private HangmanApplication() {}

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * Get the Thymeleaf TemplateEngine object.
     * @return The unique TemplateEngine object.
     */
    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
