package webwow.adapters.database;

public interface MyDAO {
//    @SqlUpdate("get all albums")
    void getAllAlbums();

    void close();
}
