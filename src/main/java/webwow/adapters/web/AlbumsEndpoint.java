package webwow.adapters.web;

import static com.vtence.molecule.http.HttpStatus.CREATED;
import static com.vtence.molecule.http.HttpStatus.NO_CONTENT;
import static com.vtence.molecule.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.routing.Routes;

import webwow.adapters.web.filters.AllowCrossOrigin;
import webwow.adapters.web.filters.PreflightHandler;
import webwow.domain.Album;
import webwow.domain.AlbumRepository;

/**
 * This endpoint class belongs to the web adapter layer.
 *
 * It's responsibility is to know how to handle the web:
 * - How to decode an HTTP request, extract data from it and pass it on to the domain Model
 * - How to encode the domain model as HTTP responses in a JSON format
 *
 * This layer does not have any user story logic in it. It only hides web
 * knowledge.
 */
public class AlbumsEndpoint {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private AlbumRepository repository;
    private WebServer webServer;

    public AlbumsEndpoint(AlbumRepository repository) {
        this(repository, WebServer.create("127.0.0.1", 8080));
    }

    public AlbumsEndpoint(AlbumRepository repository, WebServer server) {
        this.repository = repository;
        webServer = server
            .add(new AllowCrossOrigin("http://127.0.0.1:5500"))
            .add(new PreflightHandler());

        try {
            run();
        } catch (IOException ioe) {
            throw new AlbumsEndpointException(ioe);
        }
    }

    public String getUri() {
        return webServer.uri() + "/albums";
    }

    private void run() throws IOException {
        webServer.route((new Routes() {
            {
                get("/albums").to(request -> getAlbums(request));
//                get("/v1/albums").to(request -> getAlbums(request));

                post("/albums").to(request -> addAlbum(request));

                delete("/albums/:id").to(request -> deleteAlbum(request));

                put("/albums/:id").to(request -> editAlbum(request));

                get("/favourite-year").to(request -> getFavouriteYear(request));
            }
        }));
    }

    private Response getAlbums(Request request) {
        List<Album> albums = repository.getAlbums();

        String jsonResponse = new Gson().toJson(albums);
        return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
    }

    private Response addAlbum(Request request) {
        try {
            // Can't get this to work with a record
            Album album = new Gson().fromJson(request.body(), Album.class);
            int givenID = repository.addAlbum(album);

            String jsonResponse = new Gson().toJson(("localhost:8080/albums/" + givenID));
            return Response.of(CREATED).contentType(CONTENT_TYPE_JSON).done(jsonResponse);
        } catch (IOException | NullPointerException e) {
            System.out.println("Error in addAlbum");
            e.printStackTrace();
            return Response.of(INTERNAL_SERVER_ERROR).done();
        }
    }

    private Response deleteAlbum(Request request) {
        int id = Integer.parseInt(request.parameter("id"));
        repository.deleteAlbum(id);

        return Response.of(NO_CONTENT).done();
    }

    private Response editAlbum(Request request) {
        int id = Integer.parseInt(request.parameter("id"));

        try {
            // request.body() == {"name": "name","artist": "artist","year": "year"}
            Album album = new Gson().fromJson(request.body(), Album.class);
            album.setId(id);
            repository.editAlbum(album);
            return Response.of(NO_CONTENT).done();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Error while editing album");
            e.printStackTrace();
            return Response.of(INTERNAL_SERVER_ERROR).done();
        }
    }

    private Response getFavouriteYear(Request request) {
        String favouriteYear = repository.getFavouriteYear();

        String jsonResponse = new Gson().toJson(favouriteYear);
        return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
    }
}
