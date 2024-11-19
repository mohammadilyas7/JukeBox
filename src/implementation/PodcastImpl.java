package implementation;

import connection.GetConnection;
import model.Podcast;
import myExceptions.MyException;
import operation.IPodcastOperations;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PodcastImpl implements IPodcastOperations
{
    Scanner scan = new Scanner(System.in); GetConnection obj = new GetConnection(); Connection con = null;

    @Override
    public List<Podcast> retrievePodcasts()
    {
        List<Podcast> podcasts = new ArrayList<>();
        con = obj.getConnection();
        try (Statement stmt = con.createStatement())
        {
            String query = "SELECT * FROM PODCAST";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                podcasts.add( new Podcast ( rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getDate(7), rs.getString(8) ));
            }
            if (podcasts.isEmpty()) {
                throw new MyException("Unable to fetch all Podcast details... try Again.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Unable to fetch all Podcast details... try Again.");
        } catch (MyException e) {
            System.out.println("Unable to fetch all Podcast details... try Again.");
        }
        return podcasts;
    }


    @Override
    public void displayPodcasts(List<Podcast> podcasts) {
        try {
            if (podcasts.isEmpty()) {
                System.out.println("Podcast is Empty :");
            }
            else {
                System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Podcasts 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                System.out.println("\n");
                podcasts.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodId(), i.getEpId(), i.getPodName(), i.getEpName(), i.getCelebrity(), i.getDuration(), i.getPublishedDate(), i.getPodSource()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(List<Podcast> podcasts)
    {
        try {
            System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Searched Podcasts 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
            System.out.println("\nEnter Celebrity Name :");
            String celebName = scan.nextLine();
            List<Podcast> searchedPodcasts = podcasts.stream().filter(p -> p.getCelebrity().equalsIgnoreCase(celebName)).collect(Collectors.toList());
            if (searchedPodcasts.isEmpty())
            {
                System.out.println("Sorry... Podcasts with this Celebrity Name is Not Present in the Database.");
            }
            else {
                System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                System.out.println("\n");
                searchedPodcasts.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodId(), i.getEpId(), i.getPodName(), i.getEpName(), i.getCelebrity(), i.getDuration(), i.getPublishedDate(), i.getPodSource()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void searchByPublishedDate(List<Podcast> podcasts) throws  ParseException
    {
        try {
            System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Searched Podcasts 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
            System.out.println("\nEnter Published/Released Date in this format yyyy-MM-dd: ");
            String date = scan.next();
            LocalDate dt = LocalDate.parse(date);
            java.sql.Date dateData = Date.valueOf(dt);
            List<Podcast> searchedPodcasts = podcasts.stream().filter(p -> p.getPublishedDate().equals(dateData)).collect(Collectors.toList());
            if (searchedPodcasts.isEmpty()) {
                System.out.println("Sorry... Podcasts with this Released Date is Not in the Database :");
            }
            else {
                System.out.format("\n%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s", "PodcastId", "EpisodeId", "PodcastName", "EpisodeName", "Celebrity", "Duration", "PublishedDate", "PodcastSource");
                System.out.println("\n");
                searchedPodcasts.forEach(i -> System.out.format("%-15s %-15s %-30s %-30s %-15s %-15s %-15s %-15s\n", i.getPodId(), i.getEpId(), i.getPodName(), i.getEpName(), i.getCelebrity(), i.getDuration(), i.getPublishedDate(), i.getPodSource()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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