package webwow.adapters.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webwow", "postgres", "postgres");
            if (connection != null) {
                System.out.println("Connected to database");
            } else {
                System.out.println("Connection to database failed");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Postgresql Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Unable to access database");
            e.printStackTrace();
        }
        return connection;
    }

}
