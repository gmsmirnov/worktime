package ru.gsmirnov.validate;

import ru.gsmirnov.dao.exception.DaoSystemException;
import ru.gsmirnov.dao.exception.NoSuchModelException;
import ru.gsmirnov.dao.exception.NullArgumentException;
import ru.gsmirnov.models.TimeInterval;

import java.util.List;

public interface Validate {
    int addTimeInterval(TimeInterval timeInterval) throws NullArgumentException, DaoSystemException;

    void updateTimeInterval(int id, TimeInterval timeInterval) throws NullArgumentException;

    TimeInterval findTimeInterval(int id) throws NoSuchModelException;

    List<TimeInterval> findAllIntervals() throws NoSuchModelException;

    void emptyTable();
}
