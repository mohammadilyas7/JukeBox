package main.java.achivment;

import main.java.SongDAOException.SongException;
import main.java.model.PodCast;

import java.sql.SQLException;
import java.util.List;

public interface SongOperation extends AutoCloseable
{
    public List<PodCast> podcast() throws SongException, SQLException;
    public void searchPodcast(List<PodCast> podCastList) throws SQLException, SongException;
    public void songPodcast() throws SQLException, SongException;
}
