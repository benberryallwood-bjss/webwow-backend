package webwow.repository;

import java.util.List;

import webwow.adapters.web.AlbumModel;

public interface AlbumRepository {
    int addAlbum(AlbumModel album);

    List<AlbumModel> getAlbums();

    void editAlbum(AlbumModel album);

    void deleteAlbum(int id);

    String getFavouriteYear();
}
