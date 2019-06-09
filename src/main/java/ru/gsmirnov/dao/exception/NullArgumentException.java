package ru.gsmirnov.dao.exception;

/**
 * Argument is null.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 08/06/2019
 */
public class NullArgumentException extends DaoBusinessException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public NullArgumentException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public NullArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}