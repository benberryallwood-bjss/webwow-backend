package webwow.domain;

import java.util.List;

public interface AlbumRepository {
    int addAlbum(Album album);

    List<Album> getAlbums();

    void editAlbum(Album album);

    void deleteAlbum(int id);

    String getFavouriteYear();
}
