package webwow;

import webwow.adapters.database.DAO;
import webwow.adapters.database.DatabaseConnector;
import webwow.adapters.web.AlbumsEndpoint;
import webwow.adapters.web.AlbumsEndpointException;

public class Webwow {
    public static void main(String[] commandLineArguments) {
        new Webwow().run();
    }

    private void run() {
        try {
            var databaseConnection = DatabaseConnector.getConnection();
            var albumsEndpoint = new AlbumsEndpoint(new DAO(databaseConnection));
            System.out.println("Access at: " + albumsEndpoint.getUri());
        } catch (AlbumsEndpointException tee) {
            tee.printStackTrace();
        }
    }

}
