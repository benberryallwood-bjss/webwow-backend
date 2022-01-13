package webwow.adapters.web;

import com.google.gson.Gson;
import com.vtence.molecule.Middleware;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.routing.Routes;

import java.io.IOException;
import java.util.List;

import static com.vtence.molecule.http.HttpStatus.*;

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

  public AlbumsEndpoint() {
    this(WebServer.create("127.0.0.1", 8080));
  }

  AlbumsEndpoint(WebServer server) {
    webServer = server;
    addAllowCrossOriginMiddleware();

    try {
      run();
    } catch (IOException ioe) {
      throw new AlbumsEndpointException(ioe);
    }
  }

  private void addAllowCrossOriginMiddleware() {
    Middleware allowCrossOrigin = next -> request -> next.handle(request)
        .whenSuccessful(resp -> resp.addHeader("Access-Control-Allow-Origin", "*"));
    webServer.add(allowCrossOrigin);
  }

  public String getUri() {
    return webServer.uri() + "/albums";
  }

  private void run() throws IOException {
    webServer.route((new Routes() {
      {
        get("/albums").to(request -> fetchAllAlbums(request));

        get("/albums/:id").to(request -> fetchAlbumById(request));

        post("/albums").to(request -> addAlbum(request));

        delete("/albums/:id").to(request -> deleteAlbum(request));
      }
    }));
  }

  private Response deleteAlbum(Request request) {
    int id = Integer.parseInt(request.parameter("id"));

    // NOTE: get some object to delete this album
    System.out.println("DELETE called with id " + id);

    return Response.of(NO_CONTENT).done();
  }

  private Response fetchAlbumById(Request request) {
    int id = Integer.parseInt(request.parameter("id"));

    AlbumModel model = new AlbumModel(id, "Album 1 name", "Artist 1", "Year 1");
    String jsonResponse = new Gson().toJson(model);

    return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
  }

  private Response addAlbum(Request request) {
    try {
      var albumModel = new Gson().fromJson(request.body(), AlbumModel.class);

      // NOTE: we normally call other classes to do something with our request
      System.out.println(
          "Request to add new album received: " + albumModel.name + ", " + albumModel.artist + ", " + albumModel.year);
      System.out.println("Given id: " + albumModel.id);

      // NOTE: We often return some info to help the client know where to find the new
      // 'thing'
      // String jsonResponse = new Gson().toJson(("localhost:8080/albums/" +
      // albumModel.id));
      String jsonResponse = new Gson().toJson(albumModel);
      return Response.of(CREATED).contentType(CONTENT_TYPE_JSON).done(jsonResponse);

    } catch (IOException | NullPointerException e) {
      throw new AlbumsEndpointException(e);
    }
  }

  private Response fetchAllAlbums(Request request) {
    var allModels = List.of(new AlbumModel(1, "Telefone", "Noname", "2016"),
        new AlbumModel(2, "How Long", "Ariel Posen", "2019"));

    String jsonResponse = new Gson().toJson(allModels);
    return Response.ok().contentType(CONTENT_TYPE_JSON).done(jsonResponse);
  }
}
