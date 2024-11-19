package implementation;

import connection.GetConnection;
import model.Podcast;
import model.Songs;
import model.User;
import myExceptions.MyException;
import operation.IUserOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserImpl extends User implements IUserOperations
{
    Scanner sc = new Scanner(System.in); GetConnection obj = new GetConnection(); Connection con = null;
    SongsImpl sObj = new SongsImpl(); PodcastImpl pObj = new PodcastImpl();
    List<Songs> songs = new ArrayList<>(); List<Podcast> podcasts = new ArrayList<>();
    PlaylistImpl playlist = new PlaylistImpl(); PlaylistDetailsImpl playlistDetails = new PlaylistDetailsImpl();MusicPlayer musicPlayer = new MusicPlayer();

    public String mobileNoVerifier(String mobile) throws MyException
    {
        try
        {
            //Regular expression to accept valid phone number
            String regex = "\\d{10}";
            //Matching the given phone number with regular expression
            boolean result = mobile.matches(regex);
            if(result)
            {
                return mobile;
            }
            else
            {
                throw new MyException("\nGiven Phone Number is Invalid");
            }
        }
        catch (MyException e)
        {
            System.out.println("\nGiven Phone Number is Invalid");
            signUp();
            return null;
        }
    }

    public String emailIdVerifier(String emailId) throws MyException
    {
        try {
            //Regular Expression
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
            //Compile regular expression to get the pattern
            Pattern pattern = Pattern.compile(regex);
            //Create instance of matcher
            Matcher matcher = pattern.matcher(emailId);
            if(matcher.matches())
            {
                return emailId;
            }
            else
            {
                throw new MyException("\nEntered EmailId is Invalid.");
            }
        }
        catch (MyException e)
        {
            System.out.println("\nEntered EmailId is Invalid.");
            signUp();
            return null;
        }
    }


    @Override
    public void signUp() throws MyException
    {
        try
        {
            con = obj.getConnection();
            System.out.println("\nSign Up :");
            System.out.println("Enter userName :");
            String userName = sc.nextLine();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT userName FROM Signup where userName = ?");
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                if (resultSet.getString(1).equalsIgnoreCase(userName))
                {
                    throw new MyException("User Already Exists With Given UserName, Please Change Your UserName");
                }
            }

            System.out.println("Enter Mobile Number :"); //Giving Message to user To Enter a Mobile Number
            String mobile = sc.next(); // Taking mobile Number as input from user
            String mobileNo = mobileNoVerifier(mobile); // passing mobile Number as parameter to mobileNoVerifier(mobile) method and Storing the result in mobileNo variable

            System.out.println("Enter emailId :"); //Giving Message to user To Enter a Mobile Number
            String emailId = sc.next(); // Taking emailId as input from user
            String EmailId = emailIdVerifier(emailId); // passing emailId as parameter to emailIdVerifier(emailId) method and Storing the result in EmailId variable

            System.out.println("Enter a password which is a combination of characters,alphabets & numbers ie. Hamza@123 :");
            String password = sc.next();
            PreparedStatement pst = con.prepareStatement("insert into signup(userName,mobile,emailId,pWord) values(?,?,?,?);");
            pst.setString(1, userName);
            pst.setString(2, mobileNo);
            pst.setString(3, EmailId);
            pst.setString(4, password);
            int i = pst.executeUpdate();
            if (i > 0)
            {
                System.out.println("Account Created Sucessfully");
            }
        }
        catch (MyException e)
        {
            System.out.println("\nUser Already Exists With Given UserName, Please Change Your UserName");
        }
        catch (SQLException e)
        {
            System.out.println("\nUser Already Exists With Given UserName, Please Change Your UserName");
        }
    }

    @Override
    public void login() throws MyException
    {
        try
        {
            System.out.println("\nLog In :");
            System.out.println("Enter userName :");
            String userName = sc.next(); // this userName i am taking from user
            System.out.println("Enter Password :");
            String pWord = sc.next(); // this pWord i am taking from user
            con = obj.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT userName,pWord from SignUp where(userName = ? and pWord = ?)");
            ps.setString(1, userName);
            ps.setString(2, pWord);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("\n<<<<<<<<<<<<<<< Welcome to JukeBox " + userName + " >>>>>>>>>>>>>>>");
                userMenu(); // userMenu is a non static method which contains a switch case and i am calling it
            }
            else
            {
                throw new MyException("Unregistered User ! Sorry... You have to register first to access the Jukebox");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Unregistered User ! Sorry... You have to register first to access the Jukebox");
            System.exit(-1);
        }
        catch (MyException e)
        {
            System.out.println("Unregistered User ! Sorry... You have to register first to access the Jukebox");
        }
        catch (ParseException e)
        {
            System.out.println("Unregistered User ! Sorry... You have to register first to access the Jukebox");
        }
    }

    @Override
    public void userMenu() throws MyException, SQLException, ParseException
    {
        char choice;
        do {
            songs = sObj.retrieveSongs();
            sObj.displaySongs(songs);

            podcasts = pObj.retrievePodcasts();
            pObj.displayPodcasts(podcasts);

            System.out.println("\n1.\tSongs ");
            System.out.println("2.\tPodcast ");
            System.out.println("3.\tPlayList");
            System.out.println("4.\tMusic Player");
            System.out.println("5.\tExit");
            int sChoice = sc.nextInt();
            switch (sChoice) {
                case 1:
                    System.out.println("\n1.\tTo Search Songs");
                    System.out.println("2.\tTo Sort Songs");
                    int mYchoice = sc.nextInt();
                    switch (mYchoice) {
                        case 1:
                            sObj.search(songs);
                            break;
                        case 2:
                            sObj.sortedSongs(songs);
                    }
                    break;
                case 2:
                    System.out.println("\n1.\tTo Search Podcasts with Celebrity Name");
                    System.out.println("2.\tTo Search Podcasts with Released Date");
                    int chce = sc.nextInt();
                    switch (chce) {
                        case 1:
                            pObj.search(podcasts);
                            break;
                        case 2:
                            pObj.searchByPublishedDate(podcasts);
                            break;
                    }
                    break;
                case 3:
                    System.out.println("\n1.\tTo Create PlayList");
                    System.out.println("2.\tTo Insert Songs In PlayList");
                    System.out.println("3.\tTo Insert Podcasts In PlayList");
                    System.out.println("4.\tTo Insert Songs And Podcasts In PlayList");
                    System.out.println("5.\tTo Display PlayList");
                    int pChoice = sc.nextInt();
                    switch (pChoice) {
                        case 1:
                            playlist.createPlaylist();
                            break;
                        case 2:
                            playlistDetails.insertSongInPlaylist();
                            break;
                        case 3:
                            playlistDetails.insertPodcastInPlayList();
                            break;
                        case 4:
                            playlistDetails.insertSongAndPodcastInPlayList();
                            break;
                        case 5:
                            playlistDetails.displayPlayList();
                            break;
                    }
                    break;
                case 4:
                    System.out.println("\n1.\tTo Play Songs");
                    System.out.println("2.\tTo Play Podcasts");
                    int ch = sc.nextInt();
                    switch (ch) {
                        case 1:
                            musicPlayer.playSongs();
                            break;
                        case 2:
                            musicPlayer.playPodcasts();
                            break;
                    }
                    break;
                case 5:
                    System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Thank You For Visiting JukeBox 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter a correct choice !");
            }
            System.out.println("\nWant to continue ? y/n");
            choice = sc.next().charAt(0);
        } while (choice == 'y' || choice == 'Y');
    }

    @Override
    public void close() throws Exception
    {
        try
        {
            con.close();
        }
        catch (SQLException se) {
            System.out.println ("Exception closing Connection: " + se);
        }
    }
}