package main.java.achivment;
import main.java.SongDAOException.SongException;

import java.sql.SQLException;

public interface PlaylistDetailInterface extends AutoCloseable
{
     void insertOnlySongsInPlayList(int user_Id) throws SongException, SQLException;
     void inserytOnlyPodcastInPlayList(int user_Id) throws SongException, SQLException;
//    void insertBothSongsInPlayList() throws SongException, SQLException;
}
