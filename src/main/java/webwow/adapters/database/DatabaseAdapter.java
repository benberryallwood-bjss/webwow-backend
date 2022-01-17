package webwow.adapters.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.jdbi.v3.core.Jdbi;

public class DatabaseAdapter {

    public DatabaseAdapter() {
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
    }

//    public List<Album> getAllAlbums() {
//        jdbi.withHandle(handle -> {
//            return computeValue(handle);
//        });
//    }
}
