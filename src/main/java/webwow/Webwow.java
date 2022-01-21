package webwow;

import java.sql.Connection;

import webwow.adapters.database.DAO;
import webwow.adapters.database.DatabaseConnector;
import webwow.adapters.web.AlbumsEndpoint;
import webwow.adapters.web.AlbumsEndpointException;
import webwow.repository.AlbumRepository;
import webwow.repository.AlbumRepositoryImpl;

public class Webwow {
    public static void main(String[] commandLineArguments) {
        new Webwow().run();
    }

    private void run() {
        try {
            Connection databaseConnection = DatabaseConnector.getConnection();
            DAO dao = new DAO(databaseConnection);
            AlbumRepository repository = new AlbumRepositoryImpl(dao);
            AlbumsEndpoint albumsEndpoint = new AlbumsEndpoint(repository);
            System.out.println("Access at: " + albumsEndpoint.getUri());
        } catch (AlbumsEndpointException tee) {
            tee.printStackTrace();
        }
    }

}
