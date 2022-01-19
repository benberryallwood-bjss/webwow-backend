package webwow.adapters.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import webwow.adapters.web.AlbumModel;

public class DAO {
    Connection databaseConnection;

    public DAO(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public int nextID() {
        int result = -1;
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "select max(\"ID\") from \"Albums\";";
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
            String query = "select * from \"Albums\" order by \"Artist\", \"Year\", \"Name\";";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String artist = result.getString(3);
                String year = result.getString(4);
                albums.add(new AlbumModel(id, name, artist, year));
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllAlbums");
            e.printStackTrace();
        }
        return albums;
    }

    public int addAlbum(AlbumModel album) {
        int id = nextID();
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "insert into \"Albums\"" + "(\"ID\", \"Name\", \"Artist\", \"Year\")" + "values ('" + id
                    + "', '" + album.name() + "', '" + album.artist() + "', '" + album.year() + "');";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void editAlbum(AlbumModel album) {
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "update \"Albums\"" + "set \"Name\" = '" + album.name() + "', \"Artist\" = '"
                    + album.artist() + "', \"Year\" = '" + album.year() + "'" + "where \"ID\" = " + album.id() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAlbum(int id) {
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "delete from \"Albums\" where \"ID\" = " + id + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFavouriteYear() {
        String favouriteYear = "";
        try (Statement statement = databaseConnection.createStatement()) {
            String query = "select \"Year\" from \"Albums\" group by \"Year\" order by count(\"ID\") desc;";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            favouriteYear = resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println("Error in getFavouriteYear");
            e.printStackTrace();
        }
        return favouriteYear;
    }
}
