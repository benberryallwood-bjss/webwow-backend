package webwow;

import webwow.adapters.web.AlbumsEndpoint;
import webwow.adapters.web.AlbumsEndpointException;

public class Webwow {
    public static void main(String[] commandLineArguments) {
        new Webwow().run();
    }

    private void run() {
        try {
            var albumsEndpoint = new AlbumsEndpoint();
            System.out.println("Access at: " + albumsEndpoint.getUri());
        } catch (AlbumsEndpointException tee) {
            tee.printStackTrace();
        }
    }

}
