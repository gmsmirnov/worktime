package ru.gsmirnov.validate.impl;

import ru.gsmirnov.dao.TimeIntervalDao;
import ru.gsmirnov.dao.exception.DaoSystemException;
import ru.gsmirnov.dao.exception.NoSuchModelException;
import ru.gsmirnov.dao.exception.NullArgumentException;
import ru.gsmirnov.dao.impl.TimeIntervalDB;
import ru.gsmirnov.models.TimeInterval;
import ru.gsmirnov.validate.Validate;

import java.util.List;

public class ValidateDB implements Validate {
    /**
     * The DB validator. Singleton.
     */
    private static final ValidateDB VALIDATOR = new ValidateDB();

    /**
     * The database singleton.
     */
    private static final TimeIntervalDao DB = TimeIntervalDB.getInstanceOf();

    /**
     * No params constructor.
     */
    private ValidateDB() {
    }

    /**
     * Gets the singleton instance of DB-validator.
     *
     * @return
     */
    public static ValidateDB getInstanceOf() {
        return ValidateDB.VALIDATOR;
    }

    @Override
    public int addTimeInterval(TimeInterval timeInterval) throws NullArgumentException, DaoSystemException {
        this.checkTimeInterval(timeInterval);
        int result = ValidateDB.DB.addTimeIntervalToDB(timeInterval);
        if (result == -1) {
            throw new DaoSystemException("addTimeInterval returns -1.");
        }
        return result;
    }

    @Override
    public void updateTimeInterval(int id, TimeInterval timeInterval) throws NullArgumentException {
        //todo: check id
        this.checkTimeInterval(timeInterval);
        ValidateDB.DB.updateTimeInterval(id, timeInterval);
    }

    @Override
    public TimeInterval findTimeInterval(int id) throws NoSuchModelException {
        //todo: check id
        TimeInterval result = ValidateDB.DB.getTimeIntervalFromDB(id);
        if (result == null) {
            throw new NoSuchModelException("There is no time interval with such id.");
        }
        return result;
    }

    @Override
    public List<TimeInterval> findAllIntervals() throws NoSuchModelException {
        List<TimeInterval> result = ValidateDB.DB.getAllTimeIntervalsFromDB();
        if (result.isEmpty()) {
            throw new NoSuchModelException("There are no intervals.");
        }
        return result;
    }

    private void checkTimeInterval(TimeInterval timeInterval) throws NullArgumentException {
        if (timeInterval == null) {
            throw new NullArgumentException("Incorrect argument: timeInterval");
        }
    }

    private boolean checkId(int id) {
        boolean result = false;
        return result;
    }

    @Override
    public void emptyTable() {
        ValidateDB.DB.emptyTable();
    }
}