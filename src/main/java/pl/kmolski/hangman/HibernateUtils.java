package pl.kmolski.hangman;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate utility class for the DB-enabled hangman.
 *
 * This class provides a way to initialize the DB connection and
 * access the Hibernate SessionFactory from other classes.
 *
 * @author Krzysztof Molski
 * @version 1.0.0
 */
public class HibernateUtils {
    /**
     * This is a utility class - it should not be instantiated.
     */
    private HibernateUtils() {}

    /**
     * The Hibernate SessionFactory.
     */
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Initial SessionFactory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Get the global Hibernate SessionFactory object.
     * @return The unique SessionFactory object.
     */
    public static SessionFactory getFactory() {
        return sessionFactory;
    }
}
