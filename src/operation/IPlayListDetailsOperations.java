package operation;

import model.PlayList;
import model.PlaylistDetails;
import model.Songs;
import myExceptions.MyException;

import java.sql.SQLException;
import java.util.List;

public interface IPlayListDetailsOperations extends AutoCloseable
{
    public void insertSongInPlaylist() throws MyException;
    public void insertPodcastInPlayList() throws MyException;
    public void insertSongAndPodcastInPlayList() throws MyException;
    public List<PlayList> retrievePlaylist() throws MyException, SQLException;
    public List<PlaylistDetails> retrieveSongsDetailsFromPlaylist() throws MyException;
    public List<PlaylistDetails> retrievepodcastsDetailsFromPlaylist() throws MyException;
    public List<PlaylistDetails> retrieveBothFromPlaylist() throws MyException;
    public void displayPlayList() throws MyException,SQLException;

}
