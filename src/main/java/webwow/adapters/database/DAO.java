package webwow.adapters.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import webwow.adapters.web.AlbumModel;

public class DAO {
    private Connection databaseConnection;

    public DAO(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Deprecated
    // Use SERIAL type in db instead
    public int nextID() {
        int result = -1;
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "SELECT MAX(id) FROM album;";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result + 1;
    }

    public List<AlbumModel> getAllAlbums() {
        List<AlbumModel> albums = new ArrayList<>();
        try (Statement statement = databaseConnection.createStatement()) {
            albums = tryToGetAllAlbums(statement);
        } catch (SQLException e) {
            System.out.println("Error in getAllAlbums");
            e.printStackTrace();
        }
        return albums;
    }

    private List<AlbumModel> tryToGetAllAlbums(Statement statement) throws SQLException {
        String query = "SELECT * FROM album ORDER BY artist, year, name;";
        ResultSet result = statement.executeQuery(query);
        return getAlbumsFromResultSet(result);
    }

    private List<AlbumModel> getAlbumsFromResultSet(ResultSet result) throws SQLException {
        List<AlbumModel> albums = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String artist = result.getString("artist");
            String year = result.getString("year");
            albums.add(new AlbumModel(id, name, artist, year));
        }
        return albums;
    }

    public int addAlbum(AlbumModel album) {
        int id = 0;
        try (Statement statement = databaseConnection.createStatement()) {
            id = tryToAddAlbum(statement, album);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private int tryToAddAlbum(Statement statement, AlbumModel album) throws SQLException {
        int newID = nextID();
        String query = String.format("INSERT INTO album (name, artist, year) VALUES ('%s', '%s', '%s');",
                album.name(), album.artist(), album.year());
        statement.executeUpdate(query);
        // TODO Get new id from SERIAL
        return newID;
    }

    public void editAlbum(AlbumModel album) {
        try (Statement statement = databaseConnection.createStatement()) {
            tryToEditAlbum(statement, album);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryToEditAlbum(Statement statement, AlbumModel album) throws SQLException {
        String query = String.format("UPDATE album SET name = '%s', artist = '%s', year = '%s' WHERE id = %d;", album.name(), album.artist(), album.year(), album.id());
        statement.executeUpdate(query);
    }

    public void deleteAlbum(int id) {
        try (Statement statement = databaseConnection.createStatement()) {
            tryToDeleteAlbum(statement, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryToDeleteAlbum(Statement statement, int id) throws SQLException {
        String query = String.format("DELETE FROM album WHERE id = %d;", id);
        statement.executeUpdate(query);
    }

    public String getFavouriteYear() {
        String favouriteYear = "";
        try (Statement statement = databaseConnection.createStatement()) {
            favouriteYear = tryToGetFavouriteYear(statement);
        } catch (SQLException e) {
            System.out.println("Error in getFavouriteYear");
            e.printStackTrace();
        }
        return favouriteYear;
    }

    private String tryToGetFavouriteYear(Statement statement) throws SQLException {
        String query = "SELECT year FROM album GROUP BY year ORDER BY count(id) DESC";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString(1);
    }
}
