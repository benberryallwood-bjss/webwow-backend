package webwow.repository;

import java.util.List;

import webwow.adapters.database.DAO;
import webwow.adapters.web.AlbumModel;

public class AlbumRepositoryImpl implements AlbumRepository {

    private DAO dao;

    public AlbumRepositoryImpl(DAO dao) {
        this.dao = dao;
    }

    @Override
    public int addAlbum(AlbumModel album) {
        dao.addAlbum(album);
        int id = dao.getLastID();
        return id;
    }

    @Override
    public List<AlbumModel> getAlbums() {
        List<AlbumModel> albums = dao.getAllAlbums();
        return albums;
    }

    @Override
    public void editAlbum(AlbumModel album) {
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
