package implementation;

import connection.GetConnection;
import model.PlayList;
import model.PlaylistDetails;
import model.Podcast;
import model.Songs;
import myExceptions.MyException;
import operation.IPlayListDetailsOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlaylistDetailsImpl implements IPlayListDetailsOperations
{
    Scanner sc = new Scanner(System.in); GetConnection obj = new GetConnection(); Connection con = null;
    PlaylistImpl playlist = new PlaylistImpl();

    @Override
    public void insertSongInPlaylist() throws MyException
    {
        try
        {
            con = obj.getConnection();
            System.out.println("\nEnter SongId To Insert A Song In PlayList ");
            int songId = sc.nextInt();
            PreparedStatement p1 = con.prepareStatement("SELECT songId FROM songs WHERE songId = ?");
            p1.setInt(1,songId);
            ResultSet rs = p1.executeQuery();
            if(rs.next())
            {
                PreparedStatement p3 = con.prepareStatement("SELECT songIdorPodcastId FROM playlistDetails WHERE songIdorPodcastId = ?");
                p3.setInt(1,songId);
                ResultSet rst = p3.executeQuery();
                if (rst.next())
                {
                    System.out.println("You Can't Insert this Song, Song Already Exist in PlayList...");
                }
                else
                {
                    System.out.println("Enter PlayList Name");
                    String pName = sc.next();
                    PreparedStatement p4 = con.prepareStatement("SELECT playlistId from playlist where playlistname = ?");
                    p4.setString(1,pName);
                    ResultSet resultSet = p4.executeQuery();
                    if (resultSet.next())
                    {
                        if(resultSet.getInt(1)>0)
                        {
                            int playListId = resultSet.getInt(1);
                            System.out.println("Enter Type");
                            String PlayListType = sc.next();
                            PreparedStatement p2 = con.prepareStatement("INSERT into playlistDetails(playlistId,songIdorPodcastId,playListType) values(?,?,?)");
                            p2.setInt(1,playListId);
                            p2.setInt(2,songId);
                            p2.setString(3,PlayListType);
                            int rowsAffected = p2.executeUpdate();
                            if (rowsAffected > 0)
                            {
                                System.out.println("\nSong Added In PlayList SuccessFully...");
                            }
                        }
                    }
                }
            }
            else
            {
                throw new MyException("Song Does Not Exist");
            }
        }
        catch (SQLException e) {
            System.out.println("Song Does Not Exist");
        }
    }

    @Override
    public void insertPodcastInPlayList() throws MyException
    {
        try
        {
            con = obj.getConnection();
            System.out.println("\nEnter PodcastId To Insert A Podcast In PlayList");
            String podcastId = sc.next();
            PreparedStatement p1 = con.prepareStatement("Select podId FROM podcast where podId = ?");
            p1.setString(1, podcastId);
            ResultSet rs = p1.executeQuery();
            if(rs.next())
            {
                System.out.println("Enter PlayList Name");
                String pName = sc.next();
                PreparedStatement p4 = con.prepareStatement("SELECT playlistId from playlist where playlistname = ?");
                p4.setString(1,pName);
                ResultSet resultSet = p4.executeQuery();
                if (resultSet.next())
                {
                    if (resultSet.getInt(1) > 0)
                    {
                        int playListId = resultSet.getInt(1);
                        System.out.println("Enter Type");
                        String PlayListType = sc.next();
                        PreparedStatement p2 = con.prepareStatement("INSERT into playlistDetails(playlistId,songIdorPodcastId,playListType) values(?,?,?)");
                        p2.setInt(1,playListId);
                        p2.setString(2,podcastId);
                        p2.setString(3,PlayListType);
                        int rowsAffected = p2.executeUpdate();
                        if (rowsAffected > 0)
                        {
                            System.out.println("Podcast Added In PlayList SuccessFully...");
                        }
                    }
                }
            }
            else
            {
                throw new MyException("Podcast Does Not Exist");
            }
        }
        catch (SQLException e) {
            System.out.println("Podcast Does Not Exist");
        }
    }

    @Override
    public void insertSongAndPodcastInPlayList() throws MyException
    {
        insertSongInPlaylist();
        insertPodcastInPlayList();
    }

    @Override
    public List<PlayList> retrievePlaylist() throws MyException, SQLException
    {
        List<PlayList> playlist = new ArrayList<>();
        con = obj.getConnection();
        System.out.println("\nEnter Your UserId");
        int uId = sc.nextInt();
        PreparedStatement p = con.prepareStatement("SELECT * FROM playlist WHERE userId = ?");
        p.setInt(1, uId);
        ResultSet rs = p.executeQuery();
        while (rs.next())
        {
            playlist.add( new PlayList ( rs.getInt(1),
                    rs.getString(2), rs.getInt(3) ));
        }
        if (playlist.isEmpty())
        {
            System.out.println("\nUnable to fetch Playlist... with given UserId.");
            System.exit(0);
        }
        return playlist;
    }

    @Override
    public List<PlaylistDetails> retrieveSongsDetailsFromPlaylist() throws MyException
    {
        List<PlaylistDetails> songsPlaylistDetails = new ArrayList<>();
        con = obj.getConnection();
        try (Statement stmt = con.createStatement())
        {
            System.out.println("\nEnter PlayList Name");
            String pName = sc.next();
            PreparedStatement ps = con.prepareStatement("Select playlistId from playlist where playlistname = ?");
            ps.setString(1,pName);
            ResultSet rh = ps.executeQuery();
            if (rh.next())
            {
                String playListType = "Song";
                int playListId = rh.getInt(1);
                PreparedStatement pst = con.prepareStatement("select * from PlaylistDetails where playListType = ? and playlistId = ?");
                pst.setString(1, playListType);
                pst.setInt(2, playListId);
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    songsPlaylistDetails.add(new PlaylistDetails(rs.getInt(1),
                            rs.getInt(2), rs.getString(3), rs.getString(4)));
                }
            }
            else
            {
                throw new MyException("The Given PlayList Don't Exist");
            }
        }
        catch (MyException e)
        {
            System.out.println("The Given PlayList Don't Exist");
        }
        catch (SQLException e)
        {
            System.out.println("The Given PlayList Don't Exist");
        }
        return songsPlaylistDetails;
    }

    @Override
    public List<PlaylistDetails> retrievepodcastsDetailsFromPlaylist() throws MyException
    {
        List<PlaylistDetails> podacstPlaylistDetails = new ArrayList<>();
        con = obj.getConnection();
        try (Statement stmt = con.createStatement())
        {
            System.out.println("\nEnter PlayList Name");
            String pName = sc.next();
            PreparedStatement ps = con.prepareStatement("Select playlistId from playlist where playlistname = ?");
            ps.setString(1,pName);
            ResultSet rh = ps.executeQuery();
            if (rh.next())
            {
                String playListType = "Podcast";
                int playListId = rh.getInt(1);
                PreparedStatement pst  = con.prepareStatement("select * from playlistdetails where playListType = ? and playlistId = ?");
                pst.setString(1,playListType);
                pst.setInt(2,playListId);
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    podacstPlaylistDetails.add(new PlaylistDetails(rs.getInt(1),
                            rs.getInt(2), rs.getString(3), rs.getString(4)));
                }
            }
            else
            {
                throw new MyException("The Given PlayList Don't Exist");
            }
        } catch (MyException e) {
            System.out.println("The Given PlayList Don't Exist");
        }
        catch (SQLException e) {
            System.out.println("The Given PlayList Don't Exist");
        }
        return podacstPlaylistDetails;
    }

    @Override
    public List<PlaylistDetails> retrieveBothFromPlaylist() throws MyException
    {
        List<PlaylistDetails> bothPlaylistDetails = new ArrayList<>();
        con = obj.getConnection();
        try (Statement stmt = con.createStatement())
        {
            String query = "select * from playlistdetails";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                bothPlaylistDetails.add(new PlaylistDetails(rs.getInt(1),
                        rs.getInt(2), rs.getString(3), rs.getString(4)));
            }
            if (bothPlaylistDetails.isEmpty()) {
                throw new MyException("Unable To fetch Both Songs And Podcasts from PlayList... try Again.");
            }
        } catch (SQLException e)
        {
            e.getMessage();
        }
        return bothPlaylistDetails;
    }

    @Override
    public void displayPlayList() throws MyException,SQLException
    {
        List<PlayList> playlist = retrievePlaylist();
        try
        {
            if (playlist.isEmpty())
            {
                throw new MyException("PlayList is Empty");
            }
            else
            {
                System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 PlayList 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                System.out.format("\n%-15s\n", "PlayList Name");
                playlist.forEach(i -> System.out.format("%-15s\n", i.getPlayListName()));
            }
        }
        catch (Exception e)
        {
            System.out.println("PlayList is Empty");
        }

        System.out.println("\nEnter PlayList Name");
        String playListName = sc.next();
        PreparedStatement pd = con.prepareStatement("Select playlistId,playlistname from playlist where playlistname = ?");
        pd.setString(1,playListName);
        ResultSet rst = pd.executeQuery();
        while (rst.next())
        {
            if(rst.getString(2).equalsIgnoreCase(playListName))
            {
                System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 "+rst.getString(2)+" 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");

                char choice;
                do
                {
                    System.out.println("\n1.\tSongs ");
                    System.out.println("2.\tPodcasts ");
//                System.out.println("3.\tBoth Songs And Podcasts");
                    MusicPlayer musicPlayerobj = new MusicPlayer();
                    int ch = sc.nextInt();
                    switch (ch)
                    {
                        case 1:
                            List<PlaylistDetails> songsPlaylistDetails = retrieveSongsDetailsFromPlaylist();
                            if (songsPlaylistDetails.isEmpty()) {
                                System.out.println("You don't have a Songs in PlayList.");
                            }
                            else
                            {
                                System.out.println("\n1.\tTo play songs by song Id");
                                System.out.println("2.\tTo play songs in loop");
                                int choc = sc.nextInt();
                                System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Songs PlayList 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                                List<Songs> songs1 = new ArrayList<>();
                                switch (choc)
                                {
                                    case 1:
                                        System.out.format("\n%-15s %-15s %-20s %-15s", "detailId", "playlistId", "songIdorPodcastId", "playListType");
                                        System.out.println("\n");
                                        songsPlaylistDetails.forEach(i->System.out.format("%-15s %-15s %-20s %-15s\n", i.getDetailId(), i.getPlaylistId(), i.getSongidorpodcastid(), i.getPlaylisttype() ));
                                        System.out.println("\nTo Play Song");
                                        musicPlayerobj.playSongs();
                                        break;
                                    case 2:
                                        try
                                        {
                                            List<String> songIdPodId = new ArrayList<>(); //List
                                            int playId = rst.getInt(1);
                                            PreparedStatement pt = con.prepareStatement("select songIdorPodcastId from playlistdetails where playlistId = ? and playListType = 'Song'");
                                            pt.setInt(1, playId);
                                            ResultSet resultSet = pt.executeQuery();
                                            while (resultSet.next())
                                            {
                                                String temp = resultSet.getString(1);
                                                songIdPodId.add(temp);
                                            }
                                            for(int i=0; i<songIdPodId.size(); i++)
                                            {
                                                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Songs where songId = ?");
                                                preparedStatement.setInt(1, Integer.parseInt(songIdPodId.get(i)));
                                                ResultSet resultSet1 = preparedStatement.executeQuery();
                                                while (resultSet1.next())
                                                {
                                                    songs1.add( new Songs ( resultSet1.getInt(1), resultSet1.getString(2),
                                                            resultSet1.getString(3), resultSet1.getString(4), resultSet1.getFloat(5),
                                                            resultSet1.getDate(6), resultSet1.getString(7), resultSet1.getString(8) ));
                                                }
                                            }
                                            System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
                                            System.out.println("\n");
                                            songs1.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
                                            List<String> songSource = musicPlayerobj.retrievingPlaylistSongsResource(songIdPodId);
                                            musicPlayerobj.playSongsPlaylist(songSource);
                                            break;
                                        }
                                        catch (SQLException e)
                                        {
                                            System.out.println("\nYou don't have any Playlist");
                                        }
                                    default:
                                        System.out.println("\nEnter A Proper Choice");
                                }
                            }
                            break;
                        case 2:
                            List<PlaylistDetails> podacstPlaylistDetails = retrievepodcastsDetailsFromPlaylist();
                            if (podacstPlaylistDetails.isEmpty()) {
                                System.out.println("\nYou don't have a Podcasts in PlayList.");
                            }
                            else
                            {
                                System.out.println("\n1.\tTo play podcasts by podcastId");
                                System.out.println("2.\tTo play podcasts in loop");
                                int choc = sc.nextInt();
                                System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Podcast PlayList 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                                PodcastImpl podcast = new PodcastImpl(); //Creating obj of PodcastImpl class
                                List<Podcast> podcasts = podcast.retrievePodcasts();
                                MusicPlayer obj = new MusicPlayer(); String podId = null;
                                switch (choc)
                                {
                                    case 1:
                                        System.out.format("\n%-15s %-15s %-20s %-15s", "detailId", "playlistId", "songIdorPodcastId", "playListType");
                                        System.out.println("\n");
                                        podacstPlaylistDetails.forEach(i -> System.out.format("%-15s %-15s %-20s %-15s\n", i.getDetailId(), i.getPlaylistId(), i.getSongidorpodcastid(), i.getPlaylisttype()));
                                        System.out.println("\nEnter PodcastId");
                                        String pID = sc.next();
                                        List<Podcast> searchedPodcasts = podcasts.stream().filter(p -> p.getPodId().equalsIgnoreCase(pID)).collect(Collectors.toList());
                                        System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                                        System.out.println("\n");
                                        searchedPodcasts.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodId(), i.getEpId(), i.getPodName(), i.getEpName(), i.getCelebrity(), i.getDuration(), i.getPublishedDate(), i.getPodSource()));
                                        System.out.println("\nTo Play Podcast");
                                        obj.playPodcasts();
                                    case 2:
                                        int playId = rst.getInt(1);
                                        PreparedStatement pt = con.prepareStatement("select songIdorPodcastId from playlistdetails where playlistId = ? and playListType = 'Podcast'");
                                        pt.setInt(1, playId);
                                        ResultSet resultSet = pt.executeQuery();
                                        while (resultSet.next())
                                        {
                                            podId = resultSet.getString(1);
                                        }
                                        String temp = podId;
                                        List<Podcast> searchedPodcasts1 = podcasts.stream().filter(p -> p.getPodId().equalsIgnoreCase(temp)).collect(Collectors.toList());
                                        System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                                        System.out.println("\n");
                                        searchedPodcasts1.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodId(), i.getEpId(), i.getPodName(), i.getEpName(), i.getCelebrity(), i.getDuration(), i.getPublishedDate(), i.getPodSource()));
                                        musicPlayerobj.playPodcastPlaylist(searchedPodcasts1);
                                        break;
                                }
                            }
                            break;
//                    case 3:
//                        List<PlaylistDetails> bothPlaylistDetails = retrieveBothFromPlaylist();
//                        if (bothPlaylistDetails.isEmpty())
//                        {
//                            System.out.println("\nYou don't have Songs And Podcasts in PlayList.");
//                        }
//                        else {
//                            System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Both PlayList 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
//                            System.out.format("\n%-15s %-15s %-20s %-15s", "detailId", "playlistId", "songIdorPodcastId", "playListType");
//                            System.out.println("\n");
//                            bothPlaylistDetails.forEach(i->System.out.format("%-15s %-15s %-20s %-15s\n", i.getDetailId(), i.getPlaylistId(), i.getSongidorpodcastid(), i.getPlaylisttype() ));
//                        }
//                        break;
                        default:
                            System.out.println("\nEnter A Proper Choice");
                    }
                    System.out.println("\ncontinue ? y/n");
                    choice = sc.next().charAt(0);
                }while (choice == 'y' || choice == 'Y');

            }
            else
            {
                System.out.println("\nYou Don't Have Any PlayList With This Name");
            }
        }
    }

    @Override
    public void close() throws Exception
    {
        try {
            con.close();
        }
        catch (SQLException se) {
            System.out.println ("Exception closing Connection: " + se);
        }
    }
}