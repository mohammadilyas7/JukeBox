package main.java.achivment;

import main.java.SongDAOException.SongException;
import model.Song;

import java.sql.SQLException;
import java.util.List;

public abstract class  UserAchivment
{
    public abstract void loggin() throws SQLException, SongException;
    public abstract void register() throws SQLException, SongException;
    public abstract List<Song> retrieveAllSong() throws SQLException, SongException;
    public abstract void searchSongs(List<Song> songList) throws SQLException, SongException;
}
