package implementation;

import connection.GetConnection;
import model.Songs;
import myExceptions.MyException;
import operation.ISongsOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SongsImpl implements ISongsOperations
{
    Scanner sc = new Scanner(System.in); GetConnection obj = new GetConnection(); Connection con = null;
    Scanner scan = new Scanner(System.in);

    @Override
    public List<Songs> retrieveSongs() throws MyException
    {
        // Creating an ArrayList to save resulting records
        List<Songs> songs = new ArrayList<>();
        con = obj.getConnection();
        try (Statement stmt = con.createStatement())
        {
            String query = "SELECT * FROM Songs";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                songs.add( new Songs ( rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getFloat(5),
                        rs.getDate(6), rs.getString(7), rs.getString(8) ));
            }
            if(songs.isEmpty()) {
                System.out.println("Unable to fetch all Songs details... try Again.");
            }
        }
        catch (SQLException e)
        {
            e.getMessage();
        }
        return songs;
    }

    @Override
    public void displaySongs(List<Songs> songs) throws MyException
    {
        if (songs.isEmpty())
        {
            System.out.println("Unable to display Songs at the moment... try Again.");
        }
        else {
            System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Songs 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
            System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
            System.out.println("\n");
            songs.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
        }
    }

    @Override
    public void search(List<Songs> songs) throws MyException
    {
        System.out.println("\n1.\tSearch Songs Based on Genre");
        System.out.println("2.\tSearch Songs Based on Artist");
        System.out.println("3.\tSearch Songs Based on Album");
        int coice = scan.nextInt();
        System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Searched Songs 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
        switch (coice)
        {
            case 1:
                System.out.println("\nEnter Genre :");
                String genre = sc.next();
                List<Songs> searchedSongs = songs.stream().filter(p -> p.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
                if(searchedSongs.isEmpty())
                {
                    System.out.println("Sorry... Songs with the Given Genre is Not Available.");
                }
                else {
                    System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
                    System.out.println("\n");
                    searchedSongs.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
                }
                break;
            case 2:
                System.out.println("Enter Artist Name :");
                String artistName = sc.nextLine();
                List<Songs> searchSongs = songs.stream().filter(p -> p.getArtist().equalsIgnoreCase(artistName)).collect(Collectors.toList());
                if(searchSongs.isEmpty())
                {
                    System.out.println("Sorry... Songs with the Given Artist is Not Available.");
                }
                else
                {
                    System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
                    System.out.println("\n");
                    searchSongs.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
                }
                break;
            case 3:
                System.out.println("Enter Album :");
                String album = sc.nextLine();
                List<Songs> result = songs.stream().filter(p -> p.getAlbum().equalsIgnoreCase(album)).collect(Collectors.toList());
                if(result.isEmpty())
                {
                    System.out.println("Sorry... Songs with the Given Album is Not Available.");
                }
                else
                {
                    System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
                    System.out.println("\n");
                    result.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
                }
                break;
            default:
                System.out.println("\nEnter A Proper Choice.");
        }
    }


    @Override
    public void sortedSongs(List<Songs> songs)
    {
        System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Sorted Songs 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
        List<Songs> sortedSongs = songs.stream().sorted().collect(Collectors.toList());
        if (sortedSongs.isEmpty())
        {
            System.out.println("Can't Sort Songs Because Songs List IS Empty");
        }
        else
        {
            System.out.format("\n%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s", "SongId", "SongName", "Genre", "Artist", "Duration", "ReleaseDate", "Album", "SongLocation");
            System.out.println("\n");
            sortedSongs.forEach(i -> System.out.format("%-15s %-25s %-15s %-25s %-15s %-15s %-30s %-15s\n", i.getSongId(), i.getSongName(), i.getGenre(), i.getArtist(), i.getDuration(), i.getReleaseDate(), i.getAlbum(), i.getSongLocation()));
        }
    }

    @Override
    public void close() throws Exception
    {
        try {
            con.close();
        }
        catch (SQLException se)
        {
            System.out.println ("Exception closing Connection: " + se);
        }
    }
}