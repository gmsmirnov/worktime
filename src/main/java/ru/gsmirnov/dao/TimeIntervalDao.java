package ru.gsmirnov.dao;

import ru.gsmirnov.models.TimeInterval;

import java.util.List;

public interface TimeIntervalDao {

    int addTimeIntervalToDB(TimeInterval timeInterval);

    void updateTimeInterval(int id, TimeInterval timeInterval);

    TimeInterval getTimeIntervalFromDB(int id);

    List<TimeInterval> getAllTimeIntervalsFromDB();

    void emptyTable();
}