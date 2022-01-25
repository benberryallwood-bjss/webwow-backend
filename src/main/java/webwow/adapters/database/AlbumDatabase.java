package webwow.adapters.database;

import webwow.adapters.database.jdbc.JdbcAlbumDao;
import webwow.domain.AlbumRepository;

import java.sql.Connection;

public class AlbumDatabase {
    public AlbumRepository createAlbumRepository() {
        Connection databaseConnection = DatabaseConnector.getConnection();
        Dao dao = new JdbcAlbumDao(databaseConnection);
        AlbumRepository repository = new AlbumRepositoryImpl(dao);
        return repository;
    }
}
