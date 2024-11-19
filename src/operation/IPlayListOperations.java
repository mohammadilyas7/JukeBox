package operation;

import myExceptions.MyException;

public interface IPlayListOperations extends AutoCloseable
{
    public void createPlaylist() throws MyException;
}
