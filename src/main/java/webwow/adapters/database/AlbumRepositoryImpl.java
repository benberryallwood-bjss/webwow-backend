package webwow.adapters.database;

import java.util.List;

import webwow.domain.Album;
import webwow.domain.AlbumRepository;

public class AlbumRepositoryImpl implements AlbumRepository {

    private Dao dao;

    public AlbumRepositoryImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public int addAlbum(Album album) {
        dao.addAlbum(album);
        int id = dao.getLastID();
        return id;
    }

    @Override
    public List<Album> getAlbums() {
        List<Album> albums = dao.getAllAlbums();
        return albums;
    }

    @Override
    public void editAlbum(Album album) {
        dao.editAlbum(album);
    }

    @Override
    public void deleteAlbum(int id) {
        dao.deleteAlbum(id);
    }

    @Override
    public String getFavouriteYear() {
        String favouriteYear = dao.getFavouriteYear();
        return favouriteYear;
    }

}
