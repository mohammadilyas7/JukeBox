package main.java.songImplements;
import main.java.SongDAOException.SongException;
import achivment.PlaylistInterface;
import model.PlaList;
import model.PlaylistDetail;
import main.java.model.PodCast;
import model.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static songConnection.SongConnection.getconnetion;

public class PlayListImpl implements PlaylistInterface
{
    Scanner scan = new Scanner(System.in);

    //<<<<<<<<<<<<<<     Create A PlayList    >>>>>>>>>>>>>>>>>>
    @Override
    public void createPlaylist(int user_Id) throws SongException
    {
        try
        {
            PreparedStatement pre1 = getconnetion().prepareStatement("SELECT User_id FROM sign_up WHERE User_id = ?");
            pre1.setInt(1,user_Id);

            ResultSet resultSet1 = pre1.executeQuery();
            if (resultSet1.next())
            {
               System.out.println("<<< ** Enter PlayList Name Which You Are Creating ** >>>");
               String playlistName = scan.next();

               PreparedStatement preparedStatement =getconnetion().prepareStatement("SELECT User_id FROM playlist where PlaylistName= ? AND User_id = ?");
               preparedStatement.setString(1,playlistName);
               preparedStatement.setInt(2,user_Id);

               ResultSet resulSet2 = preparedStatement.executeQuery();

               if (resulSet2.next())
               {
                   System.out.println("\n>>>>>> PlayList Exist Already <<<<<<<");
               }
               else
               {
                   PreparedStatement prep3 = getconnetion().prepareStatement("INSERT INTO playlist(PlaylistName,User_id) VALUES(?,?)");
                   prep3.setString(1,playlistName);
                   prep3.setInt(2,user_Id);

                   int rowaffected = prep3.executeUpdate();
                   if (rowaffected != 0)
                   {
                        System.out.println("\n>> Your PlayList Created Successfully << ");
                   }
               }
            }else
            {
                System.out.println("<<<<  User Id  Does't Exist  >>>>");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    //<<<<<<<<<<<<<<<<<<    Add Plalist table in List   >>>>>>>>>>>>>>>>>>>>
    @Override
    public List<PlaList> display_Plalist(int user_Id) throws SongException, SQLException
    {
        List<PlaList> playList_List = new ArrayList<>();

        PreparedStatement statement = getconnetion().prepareStatement("SELECT * FROM PlayList WHERE User_id = ?");
        statement.setInt(1,user_Id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
        {
            playList_List.add(new PlaList(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
        }
        System.out.print("-------------------------------------------------------------");
        System.out.format("%-20s%-20s%-20s","\n"+"Playlist Id","PlayListName","User_Id");
        System.out.println("\n---------------------------------------------------------------");
        playList_List.forEach(i-> System.out.format("%-20s%-20s%-20s","\n"+i.getPlaylist_Id(),i.getPlayListName(),i.getUser_Id()));


        return playList_List;
    }




    //<<<<<<<<<<<<<<<       banaya  1 >>>>>>>>>>>>>>>>>>>

    public void displayPlayList(int user_Id) throws SongException,SQLException
    {
        PodcastImpl podcast = new PodcastImpl();

        List<PlaList> playlist = display_Plalist(user_Id);
        try
        {
            if (playlist.isEmpty())
            {
                throw new SongException("PlayList is Empty");
            }
            else
            {
                System.out.println("\n<<<<<<<<<<<  PlayList  >>>>>>>>>>>>");
                System.out.format("\n%-15s\n", "PlayList Name");
                playlist.forEach(i -> System.out.format("%-15s\n", i.getPlayListName()));
            }
        }
        catch (Exception e)
        {
            System.out.println("PlayList is Empty");
        }

        System.out.println("\nEnter PlayList Name");
        String playListName = scan.next();
        PreparedStatement pd = getconnetion().prepareStatement("Select Playlist_Id,PlaylistName from playlist where PlaylistName = ?");
        pd.setString(1,playListName);
        ResultSet rst = pd.executeQuery();
        while (rst.next())
        {
            if(rst.getString(2).equalsIgnoreCase(playListName))
            {
                System.out.println("\n<<<<<<<<<<<<<<<<"+rst.getString(2)+">>>>>>>>>>>>>>>>");

                char choice;
                do
                {
                    System.out.println("\n1.\tSongs ");
                    System.out.println("2.\tPodcasts ");
                    MusicPlayerImpl musicPlayerobj = new MusicPlayerImpl();
                    int ch = scan.nextInt();
                    switch (ch)
                    {
                        case 1:
                            PodcastImpl podcast1 = new PodcastImpl();
                            List<PlaylistDetail> songsPlaylistDetails =podcast1.displaySong(user_Id);
                            if (songsPlaylistDetails.isEmpty()) {
                                System.out.println("You don't have a Songs in PlayList.");
                            }
                            else
                            {
                                System.out.println("\n1.\tTo play songs by song Id");
                                System.out.println("2.\tTo play songs in loop");
                                int choc = scan.nextInt();
                                System.out.println("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Songs PlayList  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                List<Song> songs1 = new ArrayList<>();
                                switch (choc)
                                {
                                    case 1:
                                        System.out.format("\n%-15s %-15s %-20s %-15s", "detailId", "playlistId", "songIdorPodcastId", "playListType");
                                        System.out.println("\n");
                                        songsPlaylistDetails.forEach(i->System.out.format("%-15s %-15s %-20s %-15s\n", i.getDetail_Id(), i.getPlaylist_Id(), i.getSongIdOrPostcast_Id(), i.getSongTypes() ));
                                        System.out.println("\nTo Play Song");
                                        musicPlayerobj.playSongs();
                                        break;
                                    case 2:
                                        try
                                        {
                                            List<String> songIdPodId = new ArrayList<>(); //List
                                            int playId = rst.getInt(1);
                                            PreparedStatement pt = getconnetion().prepareStatement("select SongIdOrPostcast_Id from playlistdetail where Playlist_Id = ? and Type = 'Song'");
                                            pt.setInt(1, playId);
                                            ResultSet resultSet = pt.executeQuery();
                                            while (resultSet.next())
                                            {
                                                String temp = resultSet.getString(1);
                                                songIdPodId.add(temp);
                                            }
                                            for(int i=0; i<songIdPodId.size(); i++)
                                            {
                                                PreparedStatement preparedStatement = getconnetion().prepareStatement("SELECT * FROM Songs where Song_Id = ?");
                                                preparedStatement.setInt(1, Integer.parseInt(songIdPodId.get(i)));
                                                ResultSet resultSet1 = preparedStatement.executeQuery();
                                                while (resultSet1.next())
                                                {
                                                    songs1.add( new Song ( resultSet1.getInt(1), resultSet1.getString(2),
                                                            resultSet1.getString(3), resultSet1.getString(4), resultSet1.getFloat(5),
                                                            resultSet1.getDate(6), resultSet1.getString(7), resultSet1.getString(8) ));
                                                }
                                            }
                                            System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
                                            System.out.println("\n");
                                            songs1.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSong_Id(), i.getSong_Name(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleasDate(), i.getAlbum(), i.getSource()));
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
                            PodcastImpl podcast4 = new PodcastImpl();
                            List<PlaylistDetail> podacstPlaylistDetails = podcast4.displaypodCastList(user_Id);
                            if (podacstPlaylistDetails.isEmpty())
                            {
                                System.out.println("\nYou don't have a Podcasts in PlayList.");
                            }
                            else
                            {
                                System.out.println("\n1.\tTo play podcasts by podcastId");
                                System.out.println("2.\tTo play podcasts in loop");
                                int choc = scan.nextInt();
                                System.out.println("\n<<<<<<<<<<<<<<<<<<<<<<<<<<   Podcast PlayList  >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                PodcastImpl podcast2 = new PodcastImpl(); //Creating obj of PodcastImpl class
                                MusicPlayerImpl obj = new MusicPlayerImpl(); String podId = null;
                                List<PodCast> podcasts =podcast2.retrievePodcasts();
                                switch (choc)
                                {
                                    case 1:
                                        System.out.format("\n%-15s %-15s %-20s %-15s", "detailId", "playlistId", "songIdorPodcastId", "playListType");
                                        System.out.println("\n");
                                        podacstPlaylistDetails.forEach(i -> System.out.format("%-15s %-15s %-20s %-15s\n", i.getDetail_Id(), i.getPlaylist_Id(), i.getSongIdOrPostcast_Id(), i.getSongTypes()));
                                        System.out.println("\nEnter PodcastId");
                                        String pID = scan.next();
                                        List<PodCast> searchedPodcasts =  podcasts.stream().filter(p -> p.getPodcast_Id().equalsIgnoreCase(pID)).collect(Collectors.toList());
                                        System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                                        System.out.println("\n");
                                        searchedPodcasts.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodcast_Id(), i.getEpisod_Id(), i.getPodcastName(), i.getEpisod_Name(), i.getCelebrity(), i.getDuration(), i.getRealeasDate(), i.getSongSource()));
                                        System.out.println("\nTo Play Podcast");
                                        obj.playPodcast();
                                    case 2:
                                        int playId = rst.getInt(1);
                                        PreparedStatement pt = getconnetion().prepareStatement("select SongIdOrPostcast_Id from playlistdetail where Playlist_Id = ? and Type = 'Podcast'");
                                        pt.setInt(1, playId);
                                        ResultSet resultSet = pt.executeQuery();
                                        while (resultSet.next())
                                        {
                                            podId = resultSet.getString(1);
                                        }
                                        String temp = podId;
                                        List<PodCast> searchedPodcasts1 = podcasts.stream().filter(p -> p.getPodcast_Id().equalsIgnoreCase(temp)).collect(Collectors.toList());
                                        System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                                        System.out.println("\n");
                                        searchedPodcasts1.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodcast_Id(), i.getEpisod_Id(), i.getPodcastName(), i.getEpisod_Name(), i.getCelebrity(), i.getDuration(), i.getRealeasDate(), i.getSongSource()));
                                        musicPlayerobj.playPodcastPlaylist(searchedPodcasts1);
                                        break;
                                }
                            }
                            break;
                        default:
                            System.out.println("\nEnter A Proper Choice");
                    }
                    System.out.println("\ncontinue ? y/n");
                    choice = scan.next().charAt(0);
                }while (choice == 'y' || choice == 'Y');

            }
            else
            {
                System.out.println("\nYou Don't Have Any PlayList With This Name");
            }
        }
    }
    //<<<<<<<<<<<<<    Close       >>>>>>>>>
    @Override
    public void close() throws Exception
    {
        try
        {
            getconnetion().close();
        }
        catch (SQLException exception)
        {
            System.out.println ("closing Connection: " + exception);
        }
    }


}
