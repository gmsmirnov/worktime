package ru.gsmirnov.models;

import java.sql.Timestamp;
import java.util.Objects;

public class TimeInterval {
    private Timestamp startTime;

    private Timestamp finishTime;

    public TimeInterval(Timestamp startTime, Timestamp finishTime) {
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public TimeInterval(Timestamp startTime) {
        this.startTime = startTime;
        this.finishTime = new Timestamp(0);
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            TimeInterval that = (TimeInterval) o;
            return this.startTime == that.startTime &&
                    this.finishTime == that.finishTime;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startTime, this.finishTime);
    }
}