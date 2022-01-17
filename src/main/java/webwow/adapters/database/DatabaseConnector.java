package webwow.adapters.database;

import java.sql.Connection;
import java.sql.DriverManager;

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
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

}
