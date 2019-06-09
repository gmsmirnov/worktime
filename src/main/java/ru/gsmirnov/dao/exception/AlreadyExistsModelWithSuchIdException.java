package ru.gsmirnov.dao.exception;

/**
 * There is another model with the specified id in the storage.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 08/06/2019
 */
public class AlreadyExistsModelWithSuchIdException extends DaoBusinessException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public AlreadyExistsModelWithSuchIdException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public AlreadyExistsModelWithSuchIdException(String message, Throwable cause) {
        super(message, cause);
    }
}