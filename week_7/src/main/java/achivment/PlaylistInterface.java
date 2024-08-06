package achivment;

import SongDAOException.SongException;
import model.PlaList;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistInterface extends AutoCloseable
{
     void createPlaylist(int user_Id) throws SongException;
     List<PlaList> display_Plalist(int user_Id) throws SongException, SQLException;
}
