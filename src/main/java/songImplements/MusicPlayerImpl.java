package main.java.songImplements;
import SongDAOException.SongException;
import achivment.MusicPlayerInterface;
import model.PodCast;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static songConnection.SongConnection.getconnetion;
    public class MusicPlayerImpl implements MusicPlayerInterface
    {
        Scanner scan = new Scanner(System.in);
        Long currentFrame;
        Clip clip;
        String status;
        AudioInputStream inputStream;

        //<<<<<<<<<<<   Play Song  >>>>>>>>>>>>
        @Override
        public void playSongs() throws SongException, SQLException
        {
            try
            {
                    MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
                    String songUrl =musicPlayer.sourceSong();

                    System.out.println("Playing");
                    String url="D:\\NIIT\\javacode\\week_7\\src\\main\\java\\songSource\\"+songUrl;

                    clip=AudioSystem.getClip();
                    File file=new File(url);

                    inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

                    clip.open(inputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    status = "play.....";
                int choice;
                do{
                        System.out.println("\t\t1. Pause\t\t2. Resume\t\t3. Restart\t\t4. Stop");

                        choice = scan.nextInt();
                        operations(choice, url);

                    }while (choice < 4);
                } catch (UnsupportedAudioFileException ex)
            {
                ex.printStackTrace();
            } catch (LineUnavailableException ex)
            {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            }

        //<<<<<<<<<<<<    Podcast Songs  >>>>>>>>>>>>
        public void playPodcast() throws SongException, SQLException
        {
            try
            {
                MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
               String podcast_song = user_Id_Podcast();

                System.out.println("Playing");
                String url="D:\\NIIT\\javacode\\week_7\\src\\main\\java\\srk\\"+podcast_song;

                clip=AudioSystem.getClip();
                File file=new File(url);

                inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                status = "play.....";
                int choice;
                do{
                    System.out.println("\t\t1. Pause\t\t2. Resume\t\t3. Restart\t\t4. Stop");

                    choice = scan.nextInt();
                    operations(choice, url);

                }while (choice < 4);
            } catch (UnsupportedAudioFileException ex)
            {
                ex.printStackTrace();
            } catch (LineUnavailableException ex)
            {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        //<<<<<<<<<<<<    Switch Case   >>>>>>>>>>>>>
        @Override
        public void operations(int choice, String url) throws SongException
        {
            try
            {
                switch(choice)
                {
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

                }
            }

            catch(Exception e)
            {
                e.getMessage();
            }

        }


        //<<<<<<<<<<<   Song  Source    >>>>>>>>
        String sourceSong() throws SQLException, SongException
        {
            String songUrl = null;
            System.out.println("<<<<    Enter the Song_Id   >>>>");
            int song_Id = scan.nextInt();

            PreparedStatement ppt = getconnetion().prepareStatement("SELECT SongSource FROM SONGS WHERE Song_Id = ?");
            ppt.setInt(1,song_Id);
            ResultSet resultSet = ppt.executeQuery();
            if (resultSet.next())
            {
                songUrl = resultSet.getString(1);
            }else {
                System.out.println("Song Does't Exist");
            }
            return songUrl;
        }

        //<<<<<<<<<<   PODCAST SOURCE   >>>>>>>>>>>>>>>>>>>
        String user_Id_Podcast() throws SQLException, SongException
        {
            System.out.println("\nEnter The Episode Id");
            String episode_id = scan.next();

            PreparedStatement preparedStatement = getconnetion().prepareStatement("SELECT songSource FROM podcast WHERE Episod_Id = ?");
            preparedStatement.setString(1,episode_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            String song_Source = null;
            if (resultSet.next())
            {
                song_Source = resultSet.getString(1);
                System.out.println(song_Source);
            }else
            {
                System.out.println("Does't Exist");
            }
            return song_Source;
        }

        //<<<<<<<<<<<<<<<<<<     playSongsLoop     >>>>>>>>>>>>>>>>>>>>
        public void playSongsPlaylist(List<String> songSource)
        {
            try
            {
                for(int i=0;i<songSource.size();i++)
                {
                    String url = "D:\\NIIT\\javacode\\week_7\\src\\main\\java\\songSource\\" + songSource.get(i);
                    Clip clip = AudioSystem.getClip();
                    File f = new File(url);

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.getAbsoluteFile());

                    clip.open(inputStream);
                    clip.loop(0);
                    clip.start();

                    System.out.println("\nPlaying.....");
                    System.out.println("\t\t1. Next\t\t2. Prev\t\t3. Restart\t\t4. Stop");

                    int choice = new Scanner(System.in).nextInt();
                    while (true)
                    {
                        if (clip.isActive())
                        {
                            if (choice == 1)
                            {
                                clip.close();
                                clip.stop();
                                break;
                            } else if (choice == 2)
                            {
                                clip.close();
                                clip.stop();
                                i = i - 2;
                                break;
                            }
                            else if (choice == 3)
                            {
                                clip.close();
                                clip.stop();
                                i = i - 1;
                                break;
                            } else if (choice == 4)
                            {
                                break;
                            }

                        } else
                        {
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
                        if (listen.equalsIgnoreCase("Y"))
                        {
                            playSongsPlaylist(songSource);
                        }
                    }
                }

            }
            catch(Exception e)
            {
                System.out.print("\nJukebox stopped");
            }
        }

        //<<<<<<<<<<   Close Connection  >>>>>>>>>>>>>>>
        @Override
        public void close() throws Exception
        {
            getconnetion().close();
        }

        //<<<<<<<<<<<<<<<<<<<<<     >>>>>>>>>>>>>>>>>>
        public List<String> retrievingPlaylistSongsResource(List<String> songIdPodId) throws SQLException, SongException
        {
            List<String> songSource = new ArrayList<>();
            for(int i=0; i<songIdPodId.size(); i++)
            {
                PreparedStatement preparedStatement = getconnetion().prepareStatement("SELECT SongSource from songs where Song_Id = ? ");
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


        //<<<<<<<<<<<<<<<<<<<<<<<<      >>>>>>>>>>>>>>>>>>>>>>>>>
        public void playPodcastPlaylist(List<PodCast> searchedPodcasts1)
        {
            try {
                for(int i=0;i<searchedPodcasts1.size();i++)
                {

                    String url = "D:\\NIIT\\javacode\\week_7\\src\\main\\java\\srk\\" + searchedPodcasts1.get(i).getSongSource();
                    Clip clip = AudioSystem.getClip();
                    File f = new File(url);

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.getAbsoluteFile());

                    clip.open(inputStream);
                    clip.loop(0);
                    clip.start();

                    System.out.println("\nPodcast Name : "+searchedPodcasts1.get(i).getPodcastName());
                    System.out.println("Now Playing : "+searchedPodcasts1.get(i).getEpisod_Name());
                    System.out.println("\t\t1. Next\t\t2. Prev\t\t3. Restart\t\t4. Stop");

                    int choice = new Scanner(System.in).nextInt();
                    while (true) {
                        if (clip.isActive())
                        {
                            if (choice == 1)
                            {
                                clip.close();
                                clip.stop();
                                break;
                            } else if (choice == 2)
                            {
                                clip.close();
                                clip.stop();
                                i = i - 2;
                                break;
                            } else if (choice == 3)
                            {
                                clip.close();
                                clip.stop();
                                i = i - 1;
                                break;
                            } else if (choice == 4)
                            {
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
                        if (listen.equalsIgnoreCase("Y"))
                        {
                            playPodcastPlaylist(searchedPodcasts1);
                        }else
                        {
                            SwitchCase switchCase = new SwitchCase();
                            switchCase.Search();

                        }
                    }
                }

            }
            catch(Exception e)
            {
                System.out.print("\nJukebox stopped");
            }
        }


    }

