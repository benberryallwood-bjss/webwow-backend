package webwow.adapters.database;

import java.util.List;

import webwow.domain.Album;

public interface Dao {

    int getLastID();

    void addAlbum(Album album);

    List<Album> getAllAlbums();

    void editAlbum(Album album);

    void deleteAlbum(int id);

    String getFavouriteYear();

}
