package webwow.domain;

public class Album {

    private int id;
    private String name;
    private String artist;
    private String year;

    public Album(int id, String name, String artist, String year) {
        this(name, artist, year);
        this.id = id;
    }

    public Album(String name, String artist, String year) {
        this.name = name;
        this.artist = artist;
        this.year = year;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public String artist() {
        return artist;
    }

    public String year() {
        return year;
    }

    @Override
    public String toString() {
        return "AlbumModel [artist=" + artist + ", id=" + id + ", name=" + name + ", year=" + year + "]";
    }

}
