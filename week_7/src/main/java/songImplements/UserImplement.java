package songImplements;

import SongDAOException.SongException;
import achivment.UserAchivment;
import model.PodCast;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserImplement extends UserAchivment
{
    static Connection connection = null;
    Scanner scan = new Scanner(System.in);
    int check_user_ID=0;

    private static Connection getconnetion() throws SongException, SQLException
    {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/song", "root", "Password@123");
        return connection;
    }

    // Create an ArrayList to save resulting records
    public List<Song> songList = new ArrayList<>();

    @Override
    public void register() throws SQLException, SongException
    {
        System.out.println("Enter You User_Name");String user_Name = scan.nextLine();

        System.out.println("Enter Your Email_Id special charecter ie. a-z,A-Z @ ,0-9,");String email = scan.nextLine();

        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Creating a Matcher object
        Matcher matcher = pattern.matcher(email);
        //Verifying whether given emailId number is valid
        if (matcher.matches())
        {
            System.out.println("Enter Your Mobile Number");String moblie = scan.nextLine();

            System.out.println("Enter Your PassWord");String password = scan.nextLine();

            connection = getconnetion();

            PreparedStatement pre1 = connection.prepareStatement(" insert into sign_up(User_Name,Email_Id,Mobile_No,Pass_Word) values(?,?,?,?)");
            pre1.setString(1, user_Name);
            pre1.setString(2, email);
            pre1.setString(3, moblie);
            pre1.setString(4, password);

            int rowAffected = pre1.executeUpdate();

            if (rowAffected != 0)
            {
                System.out.println("Account Created Successfully");
            }
            System.out.println("Given email id is valid");
        } else
        {
            System.out.println("Invalid Email ID ");
        }

    }


    @Override
    public void loggin() throws SQLException, SongException
    {
        System.out.println("Enter Your Email_Id ");
        String emailId = scan.next();


        System.out.println("Enter Your PassWord ");
        String password = scan.next();

        connection = getconnetion();
        PreparedStatement pre2 = connection.prepareStatement("select Email_Id,Pass_Word from sign_up where Email_Id = ? AND Pass_Word = ?");
        pre2.setString(1, emailId);
        pre2.setString(2, password);

        ResultSet resultSet = pre2.executeQuery();
        try
        {

             if(resultSet.next())
            {
                SwitchCase switchCase = new SwitchCase();
                switchCase.Search();
            }
             else {
                 System.out.println("Your Email Id is Wrong");
             }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (SongException e)
        {
            e.printStackTrace();
        }
    }

    List<PodCast> podlist = new ArrayList<>();
    public List<Song> retrieveAllSong() throws SQLException, SongException
    {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("                          <<<<<<<   Welcome to SongList  >>>>>>                              ");
        System.out.println("---------------------------------------------------------------------------------------------");

        connection = getconnetion();
        Statement statement = connection.createStatement();
        ResultSet resu = statement.executeQuery("select * from songs");

        while (resu.next())
        {
            songList.add(new Song(resu.getInt(1),resu.getString(2), resu.getString(3),resu.getString(4), resu.getFloat(5),resu.getDate(6),resu.getString(7), resu.getString(8)));
        }
        List<Song> sortedList = (List<Song>) songList.stream().sorted((p1, p2)->p1.getSong_Name().compareTo(p2.getSong_Name())).collect(Collectors.toList());
        System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nSong_Id","Song_Name","Genre","Artist","Duration","ReleaseDate","Album","SongSource");
        sortedList.forEach(i-> System.out.format("%-16s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getSong_Id(),i.getSong_Name(),i.getGenre(),i.getArtist(),i.getDuration(),i.getReleasDate(),i.getAlbum(),i.getSource()));

        return sortedList;
    }

    public void searchSongs(List<Song> sortedList) throws SQLException, SongException {
        Scanner scan = new Scanner(System.in);
        int choice ;

        List<Song> songList;
        int y;
        do
        {
            System.out.println("\n\n**** **** >>>> Searching The Song To Your Choice >>>> **** ****");
            System.out.println("1. Song Name\n2. Artist\n3. Album\n4. Play Song");
            choice = scan.nextInt();
            switch (choice)
            {
                case 1:
                    System.out.println("Enter The Name Of Song Name");
                    String songname = scan.next();
                    songList = sortedList.stream().filter(i->i.getSong_Name().equalsIgnoreCase(songname)).collect(Collectors.toList());
                    System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nSong_Id","Song_Name","Genre","Artist","Duration","ReleaseDate","Album","SongSource");
                    songList.forEach(i-> System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getSong_Id(),i.getSong_Name(),i.getGenre(),i.getArtist(),i.getDuration(),i.getReleasDate(),i.getAlbum(),i.getSource()));
                    break;

                case 2:  System.out.println("Enter The Artist Name");
                    String artist = scan.next();
                    songList = sortedList.stream().filter(i->i.getArtist().equalsIgnoreCase(artist)).collect(Collectors.toList());
                    System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nSong_Id","Song_Name","Genre","Artist","Duration","ReleaseDate","Album","SongSource");
                    songList.forEach(i-> System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getSong_Id(),i.getSong_Name(),i.getGenre(),i.getArtist(),i.getDuration(),i.getReleasDate(),i.getAlbum(),i.getSource()));
                    break;
                case 3:  System.out.println("Enter The Album Name");
                    String album = scan.next();
                    songList = sortedList.stream().filter(i->i.getAlbum().equalsIgnoreCase(album)).collect(Collectors.toList());
                    System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nSong_Id","Song_Name","Genre","Artist","Duration","ReleaseDate","Album","SongSource");
                    songList.forEach(i-> System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getSong_Id(),i.getSong_Name(),i.getGenre(),i.getArtist(),i.getDuration(),i.getReleasDate(),i.getAlbum(),i.getSource()));
                    break;
                case 4: MusicPlayerImpl musicPlayer = new MusicPlayerImpl();
                        musicPlayer.playSongs();
                    break;
                default:
                    System.out.println("Invalid Choice...?");
            }
            System.out.println("\nDO You Want To Continue press 1, No press 2");
            y = scan.nextInt();
        }while (y == 1);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<      user id   >>>>>>>>>>>>>>>>>>>>>>
    public  int retrievingUserId()
    {

        return check_user_ID;
    }

}
