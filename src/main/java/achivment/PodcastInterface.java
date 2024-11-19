package main.java.achivment;

import SongDAOException.SongException;
import model.PlaylistDetail;

import java.sql.SQLException;
import java.util.List;

public interface PodcastInterface extends AutoCloseable
{
     List<PlaylistDetail> displaypodCastList(int user_Id) throws SQLException, SongException;
     List<PlaylistDetail> displaySong(int user_Id) throws SQLException, SongException;
     List<PlaylistDetail> displayBothSongPodcast(int user_Id) throws SQLException, SongException;
}
