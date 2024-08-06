package songImplements;

import SongDAOException.SongException;
import model.Song;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserImplementTest
{
   UserImplement userImplement = new UserImplement();

    @Test
    void retrieveAllSong() throws SQLException, SongException
    {
        List<Song> songList = userImplement.retrieveAllSong();
        assertEquals(8,songList.size());
    }
    @Test
    public int retrievingUserId()
    {
        List<Integer> songList2 = Collections.singletonList(userImplement.retrievingUserId());
        assertEquals(2,songList2);
        return 0;
    }
}