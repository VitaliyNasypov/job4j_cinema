package ru.job4j.job4jcinema.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.job4jcinema.persistence.Hall;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class ServicePsql implements Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePsql.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private ServicePsql() {
        Properties cfg = new Properties();
        try (InputStream io = ServicePsql.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            cfg.load(io);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        try {
            Class.forName(cfg.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        pool.setDriverClassName(cfg.getProperty("driver"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Service INST = new ServicePsql();
    }

    public static Service instOf() {
        return Lazy.INST;
    }

    @Override
    public boolean buyTicket(int row, int place, int sessionId, String nameUser, String phoneUser) {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO TICKETS (DATA_OF_SALE, SESSION_ID, ROW, PLACE, "
                             + "PRICE,STATUS, NAME_USER, PHONE_USER) "
                             + "SELECT ?,?,?,?,?,?,?,? WHERE NOT EXISTS "
                             + "(SELECT ID FROM TICKETS WHERE SESSION_ID=? AND ROW = ? "
                             + "AND PLACE = ?);")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, sessionId);
            preparedStatement.setInt(3, row);
            preparedStatement.setInt(4, place);
            preparedStatement.setInt(5, 200);
            preparedStatement.setString(6, "Sold");
            preparedStatement.setString(7, nameUser);
            preparedStatement.setString(8, phoneUser);
            preparedStatement.setInt(9, sessionId);
            preparedStatement.setInt(10, row);
            preparedStatement.setInt(11, place);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    public Hall getHall(int sessionId) {
        Hall hall = new Hall();
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from halls "
                             + "where id =(select hall_id from sessions "
                             + "where id=?);")) {
            preparedStatement.setInt(1, sessionId);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            if (filteredRowSet.next()) {
                hall = new Hall(filteredRowSet.getInt("ID"),
                        filteredRowSet.getString("TITLE"),
                        filteredRowSet.getInt("NUMBER_OF_PLACES"),
                        filteredRowSet.getInt("NUMBER_OF_ROWS"),
                        filteredRowSet.getInt("CAPACITY"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return hall;
    }

    @Override
    public String[] getPurchasedSeats(int sessionId, String statusTicket) {
        String[] purchasedSeats = new String[0];
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from TICKETS where session_id = ? "
                             + "AND status = ?")) {
            preparedStatement.setInt(1, sessionId);
            preparedStatement.setString(2, statusTicket);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
                purchasedSeats = new String[filteredRowSet.size()];
            }
            for (int i = 0; i < purchasedSeats.length; i++) {
                if (filteredRowSet.next()) {
                    purchasedSeats[i] = filteredRowSet.getInt("ROW")
                            + "." + filteredRowSet.getInt("PLACE");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return purchasedSeats;
    }
}
