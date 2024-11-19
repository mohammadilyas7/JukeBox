package main.java.songImplements;
//import main.java.SongDAOException.SongException;
//import achivment.SongOperation;
//import main.java.model.PodCast;
//import model.Song;
//import songConnection.SongConnection;

import main.java.SongDAOException.SongException;
import main.java.achivment.SongOperation;
import main.java.model.PodCast;
import main.java.songConnection.SongConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static main.java.songConnection.SongConnection.getconnetion;
//import static songConnection.SongConnection.getconnetion;
public class ImplementOperation implements SongOperation
{
    List<PodCast> podCastList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    //
    @Override
    public List<PodCast> podcast() throws SongException, SQLException
    {

        System.out.println("\n\n<<<<<<< *****   Welcome to PodcastList Song   **** >>>>>>");

        getconnetion();
        Statement statement = getconnetion().createStatement();
        ResultSet resu = statement.executeQuery("select * from Podcast");

        while (resu.next())
        {
            podCastList.add(new PodCast(resu.getString(1),resu.getString(2),resu.getString(3), resu.getString(4),resu.getString(5),resu.getString(6),resu.getDate(7),resu.getString(8 )));
        }
        List<PodCast> sortedpodcast = podCastList.stream().sorted((p1, p2)->p1.getPodcastName().compareTo(p2.getPodcastName())).collect(Collectors.toList());
        System.out.format("%-15s%-15s%-15s%-20s%-20s%-15s%-15s%-15s","\nPodcast_Id","Episod_Id","PodcastName","EpisodName","Celebrity","Duration","ReleaseDate","SongSource");
        sortedpodcast.forEach(i-> System.out.format("%-16s%-15s%-15s%-20s%-20s%-15s%-15s%-15s","\n\n"+i.getPodcast_Id(),i.getEpisod_Id(),i.getPodcastName(),i.getEpisod_Name(),i.getCelebrity(),i.getDuration(),i.getRealeasDate(),i.getSongSource()));

        return sortedpodcast;
    }


    @Override
    public void searchPodcast(List<PodCast> podCastList) throws SQLException, SongException
    {
        System.out.println("\n**** **** >>>> Searching The PodCastList Celebrity >>>> **** ****");
        List<PodCast> songList;
        System.out.println("\nEnter The Celebrity Name");
        String songname = scan.nextLine();
        songList = podCastList.stream().filter(i->i.getCelebrity().equalsIgnoreCase(songname)).collect(Collectors.toList());
        System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nPodcast_Id","Episod_Id","PodcastName","EpisodName","Celebrity","Duration","ReleaseDate","SongSource");
        songList.forEach(i-> System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getPodcast_Id(),i.getEpisod_Id(),i.getPodcastName(),i.getEpisod_Name(),i.getCelebrity(),i.getDuration(),i.getRealeasDate(),i.getSongSource()));

        MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
        musicPlayer.playPodcast();
    }



    @Override
    public void songPodcast() throws SQLException, SongException
    {
//        UserImplement userImplement = new UserImplement();
//        List<Song> songList;
//        songList = userImplement.retrieveAllSong();
//
//        ImplementOperation imploperation = new ImplementOperation();
//        List<PodCast> podCastList;
//        podCastList = imploperation.podcast();
//
//        System.out.println("\n*****  >>>>>  *****  What You Want To Search  *****  >>>>>  *****");
//        System.out.println("1.  SongList\n2.    Postcast");
//
//        int choice,yes = 0;
//       do
//       {
//           PodcastImpl podcast = new PodcastImpl();
//           choice = scan.nextInt();
//           switch (choice)
//           {
//               case 1:userImplement.searchSongs(songList);
//                   break;
//               case 2:imploperation.searchPodcast(podCastList);
//                   break;
//               default:
//                   System.out.println(" Invalid Choice...? ");
//           }
//       }while (yes == 1);
//
//        SwitchCase switchCase = new SwitchCase();
//        switchCase.Search();
    }


    @Override
    public void close() throws Exception
    {
        getconnetion().close();
    }
}

