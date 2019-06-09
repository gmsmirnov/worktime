package ru.gsmirnov.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gsmirnov.dao.TimeIntervalDao;
import ru.gsmirnov.models.TimeInterval;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class TimeIntervalDB implements TimeIntervalDao {
    private final static String CONFIG_URL = "url";

    private final static String CONFIG_USER = "username";

    private final static String CONFIG_PASSWORD = "password";

    private final static String CONFIG_DRIVER = "driver-class-name";

    private final static int CONFIG_MIN_IDLE_CONNECTIONS = 5;

    private final static int CONFIG_MAX_IDLE_CONNECTIONS = 10;

    private final static int CONFIG_MAX_PREPARED_STATEMENTS = 100;

    private final static String COLUMN_START = "start";

    private final static String COLUMN_FINISH = "finish";

    private final static String COLUMN_ID = "id";

    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(TimeIntervalDB.class.getName());
    /**
     * The connections' pool.
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    private static final TimeIntervalDB INSTANCE = new TimeIntervalDB();

    private TimeIntervalDB() {
        Properties config = new Properties();
        try (InputStream in = TimeIntervalDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            config.load(in);
        } catch (IOException e) {
            TimeIntervalDB.LOG.error(e.getMessage(), e);
        }
        TimeIntervalDB.SOURCE.setDriverClassName(config.getProperty(TimeIntervalDB.CONFIG_DRIVER));
        TimeIntervalDB.SOURCE.setUrl(config.getProperty(TimeIntervalDB.CONFIG_URL));
        TimeIntervalDB.SOURCE.setUsername(config.getProperty(TimeIntervalDB.CONFIG_USER));
        TimeIntervalDB.SOURCE.setPassword(config.getProperty(TimeIntervalDB.CONFIG_PASSWORD));
        TimeIntervalDB.SOURCE.setMinIdle(TimeIntervalDB.CONFIG_MIN_IDLE_CONNECTIONS);
        TimeIntervalDB.SOURCE.setMaxIdle(TimeIntervalDB.CONFIG_MAX_IDLE_CONNECTIONS);
        TimeIntervalDB.SOURCE.setMaxOpenPreparedStatements(TimeIntervalDB.CONFIG_MAX_PREPARED_STATEMENTS);
    }

    public static TimeIntervalDB getInstanceOf() {
        return TimeIntervalDB.INSTANCE;
    }

    @Override
    public int addTimeIntervalToDB(TimeInterval timeInterval) {
        int result = -1;
        try (Connection connection = TimeIntervalDB.SOURCE.getConnection()) {
            result = this.addTimeTransaction(connection, timeInterval);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int addTimeTransaction(Connection connection, TimeInterval timeInterval) {
        int result = -1;
        try {
            connection.setAutoCommit(false);
            this.addStartTimeToDB(connection, timeInterval);
            result = this.getTimeIntervalID(connection, timeInterval);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    private void addStartTimeToDB(Connection connection, TimeInterval timeInterval) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "insert into worktime(start) values (?);"
             )) {
            statement.setTimestamp(1, timeInterval.getStartTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getTimeIntervalID(Connection connection, TimeInterval timeInterval) {
        int result = -1;
        try (PreparedStatement statement = connection.prepareStatement(
                "select id from worktime where worktime.start = ?;"
        )) {
            statement.setTimestamp(1, timeInterval.getStartTime());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    result = rslSet.getInt(TimeIntervalDB.COLUMN_ID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateTimeInterval(int id, TimeInterval timeInterval) {
        try (Connection connection = TimeIntervalDB.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "update worktime set finish = ? where id = ?;"
             )) {
            statement.setTimestamp(1, timeInterval.getFinishTime());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TimeInterval getTimeIntervalFromDB(int id) {
        TimeInterval result = null;
        try (Connection connection = TimeIntervalDB.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select start, finish from worktime where id = ?;"
             )) {
            statement.setInt(1, id);
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    result = new TimeInterval(
                            rslSet.getTimestamp(TimeIntervalDB.COLUMN_START),
                            rslSet.getTimestamp(TimeIntervalDB.COLUMN_FINISH));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TimeInterval> getAllTimeIntervalsFromDB() {
        List<TimeInterval> result = new LinkedList<TimeInterval>();
        try (Connection connection = TimeIntervalDB.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from worktime"
             )) {
            try (ResultSet rslSet = statement.executeQuery()) {
                while (rslSet.next()) {
                    result.add(new TimeInterval(
                            rslSet.getTimestamp(TimeIntervalDB.COLUMN_START),
                            rslSet.getTimestamp(TimeIntervalDB.COLUMN_FINISH)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void emptyTable() {
        try (Connection connection = TimeIntervalDB.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "delete from worktime;"
             )) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}