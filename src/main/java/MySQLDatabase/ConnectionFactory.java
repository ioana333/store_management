package MySQLDatabase;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for establishing database connections.
 * Uses a singleton pattern to manage JDBC connection creation for all DAOs.
 *
 * Provides:
 * <ul>
 *     <li>Loading MySQL driver</li>
 *     <li>Reading database configuration</li>
 *     <li>Returning a {@link java.sql.Connection}</li>
 * </ul>
 */

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/orders_db";
    private static final String USER = "root";
    private static final String PASS = "Padurarul31+";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}

