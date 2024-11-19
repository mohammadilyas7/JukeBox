package main.java.achivment;

import SongDAOException.SongException;

import java.sql.SQLException;

public interface MusicPlayerInterface extends AutoCloseable
{
     void playSongs() throws SongException, SQLException;
     void operations(int choice, String url) throws SongException;
}
