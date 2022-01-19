package webwow.adapters.web;

import static com.vtence.molecule.http.HttpStatus.CREATED;
import static com.vtence.molecule.http.HttpStatus.NO_CONTENT;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.routing.Routes;

import webwow.adapters.database.DAO;

/**
 * This endpoint class belongs to the web adapter layer.
 *
 * It's responsibility is to know how to handle the web: * How to decode an HTTP
 * request, extract data from it and pass it on to the domain model * How to
 * encode the domain model as HTTP responses in a JSON format
 *
 * This layer does not have any user story logic in it. It only hides web
 * knowledge.
 */
public class AlbumsEndpoint {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private WebServer webServer;
    private DAO dao;

    public AlbumsEndpoint(DAO dao) {
        this();
        this.dao = dao;
    }

    private AlbumsEndpoint() {
        this(WebServer.create("127.0.0.1", 8080));
    }

    private AlbumsEndpoint(WebServer server) {
        webServer = server.add(new AllowCrossOrigin("http://127.0.0.1:5500")).add(new PreflightHandler());

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
                get("/albums").to(request -> getAllAlbums(request));

                post("/albums").to(request -> addAlbum(request));

                delete("/albums/:id").to(request -> deleteAlbum(request));

                put("/albums/:id").to(request -> editAlbum(request));

                get("/favourite-year").to(request -> getFavouriteYear(request));
            }
        }));
    }

    private Response getAllAlbums(Request request) {
        List<AlbumModel> albums = dao.getAllAlbums();
        String jsonResponse = new Gson().toJson(albums);

        return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
    }

    private Response addAlbum(Request request) {
        try {
            // Can't get this to work with a record
            AlbumModel album = new Gson().fromJson(request.body(), AlbumModel.class);
            int givenID = dao.addAlbum(album);

            String jsonResponse = new Gson().toJson(("localhost:8080/albums/" + givenID));
            return Response.of(CREATED).contentType(CONTENT_TYPE_JSON).done(jsonResponse);

        } catch (IOException | NullPointerException e) {
            System.out.println("Error in addAlbum");
            throw new AlbumsEndpointException(e);
        }
    }

    private Response deleteAlbum(Request request) {
        int id = Integer.parseInt(request.parameter("id"));
        dao.deleteAlbum(id);

        return Response.of(NO_CONTENT).done();
    }

    private Response editAlbum(Request request) {
        int id = Integer.parseInt(request.parameter("id"));

        try {
            AlbumModel album = new Gson().fromJson(request.body(), AlbumModel.class);
            album.setId(id);
            dao.editAlbum(album);
            return Response.of(NO_CONTENT).done();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Error while editing album");
            e.printStackTrace();
            return Response.of(HttpStatus.INTERNAL_SERVER_ERROR).done();
        }
    }

    private Response getFavouriteYear(Request request) {
        String favouriteYear = dao.getFavouriteYear();
        String jsonResponse = new Gson().toJson(favouriteYear);

        return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
    }
}
