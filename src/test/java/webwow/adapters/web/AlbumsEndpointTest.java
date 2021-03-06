package webwow.adapters.web;

import com.vtence.molecule.WebServer;

import webwow.adapters.database.DatabaseConnector;
import webwow.adapters.database.jdbc.AlbumRepositoryImpl;
import webwow.adapters.database.jdbc.JdbcAlbumDao;
import webwow.domain.AlbumRepository;
import java.sql.Connection;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class AlbumsEndpointTest {

    WebServer server = WebServer.create(9999);
    Connection databaseConnection = DatabaseConnector.getConnection("");
    AlbumRepository repository = new AlbumRepositoryImpl(new JdbcAlbumDao(databaseConnection));
    AlbumsEndpoint albumsEndpoint = new AlbumsEndpoint(repository, server);

//    HttpClient client = HttpClient.newHttpClient();
//    HttpRequest.Builder request = HttpRequest.newBuilder(server.uri());
//
//    @Before
//    public void startServer() throws IOException {
//        rest.run(server);
//    }
//
//    @After
//    public void stopServer() throws IOException {
//        server.stop();
//    }
//
//    @Test
//    public void managingAlbumResources() throws Exception {
//        var response = client.send(request.copy().uri(server.uri().resolve("/albums"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .POST(Form.urlEncoded()
//                                .addField("title", "My Favorite Things")
//                                .addField("artist", "John Coltrane")).build(),
//                ofString());
//
//        assertThat(response).hasStatusCode(201);
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums/1"))
//                        .GET().build(),
//                ofString());
//        assertThat(response).isOK()
//                .hasBody("Title: My Favorite Things, Artist: John Coltrane");
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .POST(Form.urlEncoded()
//                                .addField("title", "Blue Train")
//                                .addField("artist", "John Coltrane")).build(),
//                ofString());
//        assertThat(response).hasStatusCode(201);
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums"))
//                        .GET().build(),
//                ofString());
//        assertThat(response).isOK()
//                .hasBody("1: Title: My Favorite Things, Artist: John Coltrane\n" +
//                        "2: Title: Blue Train, Artist: John Coltrane\n");
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums/2"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .PUT(Form.urlEncoded()
//                                .addField("title", "Kind of Blue")
//                                .addField("artist", "Miles Davis")).build(),
//                ofString());
//        assertThat(response).isOK()
//                .hasBody("Title: Kind of Blue, Artist: Miles Davis");
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums/1")).DELETE().build(), ofString());
//        assertThat(response).isOK();
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums")).GET().build(), ofString());
//        assertThat(response).isOK()
//                .hasBody("2: Title: Kind of Blue, Artist: Miles Davis\n");
//    }
//
//    @Test
//    public void makingAPostActLikeAnUpdateOrDelete() throws Exception {
//        var response = client.send(request.copy().uri(server.uri().resolve("/albums"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .POST(Form.urlEncoded()
//                                .addField("title", "My Favorite Things")
//                                .addField("artist", "John Coltrane")).build(),
//                ofString());
//        assertThat(response).hasStatusCode(201);
//
//        response = client.send(request.copy()
//                        .uri(server.uri().resolve("/albums/1"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .POST(Form.urlEncoded()
//                                .addField("_method", "PUT")
//                                .addField("title", "Kind of Blue")
//                                .addField("artist", "Miles Davis")).build(),
//                ofString());
//        assertThat(response).isOK();
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums/1"))
//                .GET().build(), ofString());
//        assertThat(response).isOK()
//                .hasBody("Title: Kind of Blue, Artist: Miles Davis");
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums/1"))
//                        .header("Content-Type", Form.urlEncoded().contentType())
//                        .POST(Form.urlEncoded()
//                                .addField("_method", "DELETE")).build(),
//                ofString());
//        assertThat(response).isOK();
//
//        response = client.send(request.copy().uri(server.uri().resolve("/albums"))
//                .GET().build(), ofString());
//        assertThat(response).isOK()
//                .hasBody("Your music library is empty");
//    }
//
//    @Test
//    public void askingForAMissingAlbum() throws Exception {
//        var response = client.send(request.uri(server.uri().resolve("/albums/9999")).build(), ofString());
//        assertThat(response).hasStatusCode(404);
//    }
}
