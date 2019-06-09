package ru.gsmirnov.dao.exception;

/**
 * System problems of data access object. E.g. DB connection lost, wrong password, connection timeout.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 08/06/2019
 */
public class DaoSystemException extends DaoException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public DaoSystemException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}