package webwow;

import webwow.adapters.database.AlbumDatabase;
import webwow.adapters.web.AlbumsEndpoint;
import webwow.adapters.web.AlbumsEndpointException;
import webwow.domain.AlbumRepository;

public class Webwow {
    public static void main(String[] commandLineArguments) {
        new Webwow().run();
    }

    private void run() {
        try {
            AlbumRepository repository = new AlbumDatabase().createAlbumRepository();
            AlbumsEndpoint albumsEndpoint = new AlbumsEndpoint(repository);
            System.out.println("Access at: " + albumsEndpoint.getUri());
        } catch (AlbumsEndpointException tee) {
            tee.printStackTrace();
        }
    }

}
