package operation;

import myExceptions.MyException;

import java.sql.SQLException;

public interface IMusicPlayerOperations
{
    public String retrieveSongSource() throws MyException, SQLException;
    public String retrievePodcastSource() throws MyException, SQLException;
    public void playSongs() throws MyException;
    public void playPodcasts() throws MyException;
    public void operations(int choice, String url) throws MyException;
}
