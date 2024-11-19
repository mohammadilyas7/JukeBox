package operation;

import model.Songs;
import myExceptions.MyException;

import java.util.List;

public interface ISongsOperations extends AutoCloseable
{
    //Songs Exception
    public List<Songs> retrieveSongs() throws MyException;
    public void displaySongs(List<Songs> songs) throws MyException;
    public void search(List<Songs> songs) throws MyException;
    public void sortedSongs(List<Songs> songs);
}
