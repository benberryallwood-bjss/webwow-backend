package webwow.adapters.web;

/**
 * This is a simple data structure that maps directly to the JSON used in the
 * response
 */
class AlbumModel {

  int id;
  String name;
  String artist;
  String year;

  public AlbumModel(int id, String name, String artist, String year) {
    this(name, artist, year);
    this.id = id;
  }

  public AlbumModel(String name, String artist, String year) {
    this.name = name;
    this.artist = artist;
    this.year = year;
    this.id = 101;
    // TODO Calculate new id?
  }

}
