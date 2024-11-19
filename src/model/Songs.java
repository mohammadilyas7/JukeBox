package model;

import java.util.Date;

public class Songs implements Comparable<Songs>
{
    protected int songId;
    protected String songName;
    protected String genre;
    protected String artist;
    protected float duration;
    protected Date releaseDate;
    protected String album;
    protected String songLocation;

    public Songs() {} // Default Constructor

    public Songs(int songId, String songName, String genre, String artist,
    float duration, Date releaseDate, String album, String songLocation)
    {
        this.songId = songId;
        this.songName = songName;
        this.genre = genre;
        this.artist = artist;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.album = album;
        this.songLocation = songLocation;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSongLocation() {
        return songLocation;
    }

    public void setSongLocation(String songLocation) {
        this.songLocation = songLocation;
    }

    @Override
    public String toString() {
        return "Songs{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", releaseDate=" + releaseDate +
                ", album='" + album + '\'' +
                ", songLocation='" + songLocation + '\'' +
                '}';
    }

    @Override
    public int compareTo(Songs o)
    {
        return this.getSongName().compareToIgnoreCase(o.getSongName());
    }
}