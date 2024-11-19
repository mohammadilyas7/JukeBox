package implementation;

import connection.GetConnection;
import model.Podcast;
import model.Songs;
import myExceptions.MyException;
import operation.IMusicPlayerOperations;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MusicPlayer implements IMusicPlayerOperations
{
    Long currentFrame;
    Clip clip;
    String status;
    AudioInputStream inputStream;

    GetConnection obj = new GetConnection(); Connection con = null; Scanner scanObj=new Scanner(System.in);

    @Override
    public String retrieveSongSource() throws MyException, SQLException
    {
        String songSource = null;
        con = obj.getConnection();
        try
        {
            System.out.print("\nEnter SongID:  ");
            int songID = scanObj.nextInt();

            //--------------------------------------------------------------------------------------------------------//
            SongsImpl songObj = new SongsImpl();
            List<Songs> songs = songObj.retrieveSongs();
            List<Songs> searchedSongs = songs.stream().filter(p -> p.getSongId() == (songID)).collect(Collectors.toList());
            System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
            System.out.println("\n");
            searchedSongs.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
            //--------------------------------------------------------------------------------------------------------//

            PreparedStatement ps = con.prepareStatement("SELECT songSource FROM SONGS where songId = ?");
            ps.setInt(1,songID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                songSource = rs.getString(1);
            }
            else
            {
                throw new MyException("\nSong With Given SongId Is Not Present");
            }
        }catch (Exception e)
        {
            System.out.println("\nSong With Given SongId Is Not Present");
        }
        return songSource;
    }

    @Override
    public String retrievePodcastSource() throws MyException, SQLException
    {
        con = obj.getConnection();
        String podSource = null;
        try {
            System.out.print("\nEnter PodcastID:  ");
            String podId = scanObj.next();
            System.out.print("Enter EpisodeID:  ");
            String epId = scanObj.next();
            PreparedStatement ps = con.prepareStatement("SELECT podSource FROM Podcast where podId = ? and epId = ?");
            ps.setString(1,podId);
            ps.setString(2,epId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                podSource = rs.getString(1);
            }
            else {
                throw new MyException("\nPodcast With Given PodcastID And EpisodeID Is Not Present");
            }
        }
        catch (Exception e)
        {
            System.out.println("\nPodcast With Given PodcastID And EpisodeID Is Not Present");
        }
        return podSource;
    }

    @Override
    public void playSongs() throws MyException
    {
        try
        {
            String source = retrieveSongSource();

            if(source.isEmpty())
            {
                throw new MyException("\nSong With Given SongId Is Not Present");
            }

            System.out.println("\nPlaying");
            String url = "D:\\Wave21\\Sprints\\Course 7\\JAP_C7_S1_Java_Programming_Project\\songsResource\\" + source;

            clip = AudioSystem.getClip();
            File file = new File(url);

            inputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            status = "play";

            while (true)
            {
                System.out.println("\n1. Pause");
                System.out.println("2. Resume");
                System.out.println("3. Restart");
                System.out.println("4. Stop");

                int choice = scanObj.nextInt();
                operations(choice, url);
                if (choice == 4)
                {
                    System.out.println("\nDo You Wish To Continue?(Y/N)");
                    String plays = scanObj.next();
                    if (plays.equalsIgnoreCase("Y")) {
                        playSongs();
                    } else if (plays.equalsIgnoreCase("N"))
                    {
                        int ch = 1;
                        operations(ch, url);
                        UserImpl user = new UserImpl();
                        user.userMenu();
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("\nSong With Given SongId Is Not Present");
        }
    }

    public List<String> retrievingPlaylistSongsResource(List<String> songIdPodId) throws SQLException
    {
        List<String> songSource = new ArrayList<>();
        for(int i=0; i<songIdPodId.size(); i++)
        {
            con = obj.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT songSource from songs where songId = ? ");
            preparedStatement.setInt(1, Integer.parseInt(songIdPodId.get(i)));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String temp = resultSet.getString(1);
                songSource.add(temp);
            }
        }
        return songSource;
    }

    public void playSongsPlaylist(List<String> songSource)
    {
        try {
            for(int i=0;i<songSource.size();i++)
            {

                String url = "D:\\Wave21\\Sprints\\Course 7\\JAP_C7_S1_Java_Programming_Project\\songsResource\\" + songSource.get(i);
                Clip clip = AudioSystem.getClip();
                File f = new File(url);

                AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.getAbsoluteFile());

                clip.open(inputStream);
                clip.loop(0);
                clip.start();

                System.out.println("\nPlaying");
                System.out.println("1.\tNext");
                System.out.println("2.\tPrev");
                System.out.println("3.\tRestart");
                System.out.println("4.\tStop");

                int choice = new Scanner(System.in).nextInt();
                while (true) {
                    if (clip.isActive()) {


                        if (choice == 1) {
                            clip.close();
                            clip.stop();
                            break;
                        } else if (choice == 2) {
                            clip.close();
                            clip.stop();
                            i = i - 2;
                            break;
                        } else if (choice == 3) {
                            clip.close();
                            clip.stop();
                            i = i - 1;
                            break;
                        } else if (choice == 4) {
                            break;
                        }

                    } else {
                        clip.close();
                        clip.stop();
                        break;
                    }
                }
                if (choice == 4)
                {
                    clip.close();
                    clip.stop();
                    System.out.println("\nDo you wish to continue?(Y/N) ");
                    String listen = new Scanner(System.in).next();
                    if (listen.equalsIgnoreCase("Y")) {
                        playSongsPlaylist(songSource);
                    }
                    else if (listen.equalsIgnoreCase("N"))
                    {
                        if (listen.equalsIgnoreCase("N"))
                        {
                            UserImpl user = new UserImpl();
                            user.userMenu();
                        }
                    }
                }
            }

        }
        catch(Exception e)
        {
            System.out.print("\nJukebox stopped");
        }
    }

    public void playPodcastPlaylist(List<Podcast> searchedPodcasts1)
    {
        try {
            for(int i=0;i<searchedPodcasts1.size();i++)
            {

                String url = "D:\\Wave21\\Sprints\\Course 7\\JAP_C7_S1_Java_Programming_Project\\songsResource\\" + searchedPodcasts1.get(i).getPodSource();
                Clip clip = AudioSystem.getClip();
                File f = new File(url);

                AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.getAbsoluteFile());

                clip.open(inputStream);
                clip.loop(0);
                clip.start();

                System.out.println("\nPodcast Name : "+searchedPodcasts1.get(i).getPodName());
                System.out.println("Now Playing : "+searchedPodcasts1.get(i).getEpName());
                System.out.println("1.\tNext");
                System.out.println("2.\tPrev");
                System.out.println("3.\tRestart");
                System.out.println("4.\tStop");

                int choice = new Scanner(System.in).nextInt();
                while (true) {
                    if (clip.isActive()) {


                        if (choice == 1) {
                            clip.close();
                            clip.stop();
                            break;
                        } else if (choice == 2) {
                            clip.close();
                            clip.stop();
                            i = i - 2;
                            break;
                        } else if (choice == 3) {
                            clip.close();
                            clip.stop();
                            i = i - 1;
                            break;
                        } else if (choice == 4) {
                            break;
                        }

                    } else {
                        clip.close();
                        clip.stop();
                        break;
                    }
                }
                if (choice == 4)
                {
                    clip.close();
                    clip.stop();
                    System.out.println("\nDo you wish to continue?(Y/N) ");
                    String listen = new Scanner(System.in).next();
                    if (listen.equalsIgnoreCase("Y")) {
                        playPodcastPlaylist(searchedPodcasts1);
                    }
                    else if (listen.equalsIgnoreCase("N"))
                    {
                        if (listen.equalsIgnoreCase("N"))
                        {
                            UserImpl user = new UserImpl();
                            user.userMenu();
                        }
                    }
                }
            }

        }
        catch(Exception e)
        {
            System.out.print("\nJukebox stopped");
        }
    }

    @Override
    public void playPodcasts() throws MyException
    {
        try
        {   String source = retrievePodcastSource();
            if(source.isEmpty())
            {
                throw new MyException("\nPodcast With Given PodcastID And EpisodeID Is Not Present");
            }

            System.out.println("\nPlaying");
            String url="D:\\Wave21\\Sprints\\Course 7\\JAP_C7_S1_Java_Programming_Project\\songsResource\\"+source;

            clip = AudioSystem.getClip();
            File file = new File(url);

            inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            status = "play";

            while(true)
            {
                System.out.println("\n1. Pause");
                System.out.println("2. Resume");
                System.out.println("3. Restart");
                System.out.println("4. Stop");

                int choice=scanObj.nextInt();
                operations(choice, url);
                if (choice == 4)
                {
                    System.out.println("\nDo You Wish To Continue?(Y/N)");
                    String plays = scanObj.next();
                    if (plays.equalsIgnoreCase("Y")) {
                        playPodcasts();
                    } else if (plays.equalsIgnoreCase("N")) {
                        int ch = 1;
                        operations(ch, url);
                        UserImpl user = new UserImpl();
                        user.userMenu();
                    }
                }
            }
        }
        catch(Exception e)
        {
//            System.out.print("\nPodcast With Given PodcastID And EpisodeID Is Not Present");
        }
    }

    @Override
    public void operations(int choice, String url) throws MyException
    {
        try {
            switch(choice) {
                case 1:
                    this.currentFrame=this.clip.getMicrosecondPosition();
                    clip.stop();
                    status="paused";
                    break;
                case 2:

                    clip.setMicrosecondPosition(currentFrame);
                    clip.start();
                    status="play";
                    break;

                case 3:
                    clip.stop();
                    clip.close();

                    clip=AudioSystem.getClip();
                    File file=new File(url);

                    inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

                    clip.open(inputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    status = "play";
                    break;
                case 4:
                    currentFrame=0L;
                    clip.stop();
                    clip.close();
                    break;
                default: throw new MyException("\nEnter A Proper Choice");
            }
        }
        catch(MyException | UnsupportedAudioFileException | IOException e)
        {
            System.out.println("\nEnter A Proper Choice");
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
}
