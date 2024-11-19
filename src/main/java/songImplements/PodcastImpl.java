package main.java.songImplements;

import SongDAOException.SongException;
import achivment.PodcastInterface;
import model.PlaylistDetail;
import model.PodCast;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static songConnection.SongConnection.getconnetion;
public class PodcastImpl implements PodcastInterface
{
    Scanner scan = new Scanner(System.in);
    //<<<<<<<<<<<    DISPLAY PODCAST LIST   >>>>>>>>>>>>
    @Override
    public List<PlaylistDetail> displaypodCastList(int user_Id) throws SQLException, SongException
    {
        List<PlaylistDetail> list_Podcast = new ArrayList<>();

        PreparedStatement ppt = getconnetion().prepareStatement("SELECT * FROM playlistdetail WHERE Type = 'podcast' AND Playlist_Id = ANY (select Playlist_Id from playlist WHERE User_id = ?)");
        ppt.setInt(1,user_Id);
        ResultSet resultSet = ppt.executeQuery();

        while(resultSet.next())
        {
            list_Podcast.add(new PlaylistDetail(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4) ));
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.format("%-20s%-20s%-20s%-20s","\n"+"Detail_Id","Playlist_Id","Podcast Id","Type");
        System.out.println("\n-----------------------------------------------------------------------------------------------");
        list_Podcast.forEach(i-> System.out.format("%-20s%-20s%-20s%-20s","\n"+i.getDetail_Id(),i.getPlaylist_Id(),i.getSongIdOrPostcast_Id(),i.getSongTypes()));

//        MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
//        musicPlayer.playPodcast();

        return list_Podcast;
    }

    //<<<<<<<<<<<<<<<<     Display Song     >>>>>>>>>>>
    @Override
    public List<PlaylistDetail> displaySong(int user_Id) throws SQLException, SongException
    {
        List<PlaylistDetail> songList = new ArrayList<>();

        PreparedStatement ppt = getconnetion().prepareStatement("SELECT * FROM playlistdetail WHERE Type = 'song' AND Playlist_Id = ANY (select Playlist_Id from playlist WHERE User_id = ?);");
        ppt.setInt(1,user_Id);
        ResultSet resultSet = ppt.executeQuery();
        while (resultSet.next())
        {
            songList.add(new PlaylistDetail(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4) ));
        }
        System.out.println("____________________________________________________________________");
        System.out.format("%-20s%-20s%-20s%-20s","\n"+"Detail_Id","Playlist_Id","Song Id","Type");
        System.out.println("\n_____________________________________________________________________");
        songList.forEach(i-> System.out.format("%-20s%-20s%-20s%-20s","\n"+i.getDetail_Id(),i.getPlaylist_Id(),i.getSongIdOrPostcast_Id(),i.getSongTypes()));
//        System.out.println("\n\nPlay the song press 1");
//        int play = scan.nextInt();
//        if (play == 1)
//        {
//            MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
//            musicPlayer.playSongs();
//        }
        return songList;
    }

    //<<<<<<<<<<    Display Both Song & Podcast    >>>>>>>>>>>
    @Override
    public List<PlaylistDetail> displayBothSongPodcast(int user_Id) throws SQLException, SongException
    {

        List<PlaylistDetail> playlistDetailList = new ArrayList<>();
        // use static method
        PreparedStatement pp3 = getconnetion().prepareStatement("SELECT * FROM playlistdetail WHERE Playlist_Id = ANY (SELECT Playlist_Id FROM playlist WHERE User_id = ?)");
        pp3.setInt(1,user_Id);
        ResultSet resultSet = pp3.executeQuery();
        while (resultSet.next())
        {
            playlistDetailList.add(new PlaylistDetail(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
        }
        System.out.format("%-20s%-20s%-20s%-20s","\n"+"Detail_Id","Playlist_Id","Song & Podcast Id","Type");
        playlistDetailList.forEach(i-> System.out.format("%-20s%-20s%-20s%-20s","\n"+i.getDetail_Id(),i.getPlaylist_Id(),i.getSongIdOrPostcast_Id(),i.getSongTypes()));
        return playlistDetailList;
    }

    @Override
    public void close() throws Exception
    {
            getconnetion().close();
    }


    //<<<<<<<<<<<<<        All Podcast     >>>>>>>>>>>>>>>>>>>>
    public List<PodCast> retrievePodcasts()
    {
        List<PodCast> podcasts = new ArrayList<>();
        try (Statement stmt = getconnetion().createStatement())
        {
            String query = "SELECT * FROM PODCAST";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                podcasts.add( new PodCast ( rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getDate(7), rs.getString(8) ));
            }
            if (podcasts.isEmpty()) {
                System.out.println("Unable to fetch all Podcast details... try Again.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (SongException e) {
            e.printStackTrace();
        }
        return podcasts;
    }
}
