package main.java.songImplements;

import SongDAOException.SongException;
import achivment.PlaylistDetailInterface;
import model.PlaylistDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static songConnection.SongConnection.getconnetion;

public class PlayLIstDetailImpl implements PlaylistDetailInterface
{
    Scanner scan = new Scanner(System.in);

    //<<<<<<<<<<        Add A Song In PlayList        >>>>>>>>>>
    @Override
    public void insertOnlySongsInPlayList(int user_Id) throws SongException, SQLException
    {
        System.out.println("<<< Enter Song Id >>>");
        int song_Id = scan.nextInt();

        PreparedStatement prepare = getconnetion().prepareStatement("SELECT Song_Id FROM songs WHERE Song_Id = ?");
        prepare.setInt(1,song_Id);

        ResultSet resultSet = prepare.executeQuery();

        if (resultSet.next())
        {
                System.out.println("<<<<   Enter Playlist Name   >>>>");
                String plalist_Name = scan.next();

                PreparedStatement prst = getconnetion().prepareStatement("Select Playlist_Id FROM playlist WHERE PlaylistName = ? AND User_Id = ?");
                prst.setString(1,plalist_Name);
                prst.setInt(2,user_Id);

                ResultSet resultSet1 = prst.executeQuery();
                if (resultSet1.next())
                {
                    int plalist_Id = resultSet1.getInt(1);

                    PreparedStatement prepare1 = getconnetion().prepareStatement("INSERT INTO playlistdetail(Playlist_Id,SongIdOrPostcast_Id,Type) VALUES(?,?,'song')");
                    prepare1.setInt(1,plalist_Id);
                    prepare1.setInt(2,song_Id);

                    int rows = prepare1.executeUpdate();
                    if (rows != 0)
                    {
                        System.out.println("<<< Song added Successfully >>>");
                    }
                }
                else
                {
                    System.out.println("<<<  PlayList Name Does't Exist >>>");
                }

        }else
        {
            System.out.println("<<< Song Id Does't Exist >>>");
        }

    }

    //<<<<<<<<<<<<      Add A PodCast Song      >>>>>>>>>>>
    @Override
    public void inserytOnlyPodcastInPlayList(int user_Id) throws SongException, SQLException
    {
        System.out.println("<<< Enter Podcast Id");
        String podcast = scan.next();

        PreparedStatement pre4 = getconnetion().prepareStatement("SELECT Podcast_Id FROM podcast WHERE Podcast_Id = ?");
        pre4.setString(1,podcast);

        ResultSet resultSet4 = pre4.executeQuery();
        if (resultSet4.next())
        {
            System.out.println("<<< Enter Episode Id >>>");
            String episod_Id = scan.next();

            PreparedStatement pre5 = getconnetion().prepareStatement("SELECT Episod_Id FROM podcast WHERE Episod_Id = ?");
            pre5.setString(1,episod_Id);
            ResultSet resultSet5 = pre5.executeQuery();
            if (resultSet5.next())
            {
                PreparedStatement pre1 = getconnetion().prepareStatement("SELECT Podcast_Id,Podcast_Id FROM podcast WHERE Podcast_Id = ? AND Episod_Id = ?");
                pre1.setString(1, podcast);
                pre1.setString(2, episod_Id);

                ResultSet resultSet = pre1.executeQuery();
                if (resultSet.next())
                {

                    System.out.println("<<<<   Enter Playlist Name   >>>>");
                    String plalist_Name = scan.next();

                    PreparedStatement prst = getconnetion().prepareStatement("Select Playlist_Id FROM playlist WHERE PlaylistName = ? AND User_Id = ?");
                    prst.setString(1,plalist_Name);
                    prst.setInt(2,user_Id);
                    ResultSet resultSet1 = prst.executeQuery();

                    if (resultSet1.next())
                    {
                        int playlist_Id = resultSet1.getInt(1);
                        PreparedStatement pre2 = getconnetion().prepareStatement("INSERT INTO playlistdetail(Playlist_Id,SongIdOrPostcast_Id,Type) VALUES(?,?,'podcast')");
                        pre2.setInt(1, playlist_Id);
                        pre2.setString(2, podcast);

                        int rows = pre2.executeUpdate();
                        if (rows != 0)
                        {
                            System.out.println("<< Podcast Detail Added SuccessFully >>");
                        }
                    }else {
                        System.out.println("Playlist Name Does't Exist");
                    }
                }
            }
            else
            {
                System.out.println("<<< || Episode Does't Exist || >>>");
            }
        }else
        {
            System.out.println("<<< || Pod Cast id Does' t Exist || >>>");
        }


    }

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


    public List<PlaylistDetail> retrieveSongsDetailsFromPlaylist()
    {
        List<PlaylistDetail> songsPlaylistDetails = new ArrayList<>();
        try (Statement stmt = getconnetion().createStatement())
        {
            System.out.println("\nEnter PlayList Name");
            String pName = scan.next();
            PreparedStatement ps = getconnetion().prepareStatement("Select playlistId from playlist where playlistname = ?");
            ps.setString(1,pName);
            ResultSet rh = ps.executeQuery();
            if (rh.next())
            {
                String playListType = "Song";
                int playListId = rh.getInt(1);
                PreparedStatement pst = getconnetion().prepareStatement("select * from PlaylistDetails where playListType = ? and playlistId = ?");
                pst.setString(1, playListType);
                pst.setInt(2, playListId);
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    songsPlaylistDetails.add(new PlaylistDetail(rs.getInt(1),
                            rs.getInt(2), rs.getString(3), rs.getString(4)));
                }
            }
            else
            {
                throw new SongException("The Given PlayList Don't Exist");
            }
        }
        catch (SQLException e)
        {
            System.out.println("The Given PlayList Don't Exist");
        } catch (SongException e) {
            e.printStackTrace();
        }
        return songsPlaylistDetails;
    }
}
