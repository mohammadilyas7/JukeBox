package implementation;

import connection.GetConnection;
import myExceptions.MyException;
import operation.IPlayListOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PlaylistImpl implements IPlayListOperations
{
    Scanner sc = new Scanner(System.in); GetConnection obj = new GetConnection(); Connection con = null; String playlist;

    @Override
    public void createPlaylist() throws MyException
    {
        con = obj.getConnection();

        try
        {
            System.out.println("\nEnter Your UserId ");
            int id = sc.nextInt();
            PreparedStatement p1 = con.prepareStatement("SELECT userId FROM SignUp WHERE userId = ?");
            p1.setInt(1,id);
            ResultSet rs = p1.executeQuery();
            if (rs.next())
            {
                PreparedStatement p2 = con.prepareStatement("INSERT into playlist(userId) values(?)");
                p2.setInt(1,id);
                System.out.println("Enter PlayList Name Which You Want To Create");
                playlist = sc.next();
                PreparedStatement p3 = con.prepareStatement("SELECT playlistname FROM playlist WHERE playlistname = ? and userId = ?");
                p3.setString(1,playlist);
                p3.setInt(2,id);
                ResultSet rst = p3.executeQuery();
                if (rst.next())
                {
                    String pName = rst.getString(1);
                    if (pName.equalsIgnoreCase(playlist))
                    {
                        System.out.println("PlayList Already Exists with this Name...");
                        System.exit(0);
                    }
                }
                else
                {
                    PreparedStatement p4 = con.prepareStatement("INSERT into playlist(playlistname,userId) values(?,?)");
                    p4.setString(1,playlist);
                    p4.setInt(2,id);
                    int rowsAffected = p4.executeUpdate();
                    if (rowsAffected > 0)
                    {
                        System.out.println("\nPlayList Created SuccessFully...");

                        PreparedStatement pd = con.prepareStatement("Select playlistId,playlistname from playlist where playlistname = ?");
                        pd.setString(1,playlist);
                        ResultSet rh = pd.executeQuery();
                        while (rh.next()) {
                            if (rh.getString(2).equalsIgnoreCase(playlist)) {
                                System.out.format("\n%-15s %-15s\n", "PlayList Id", "PlayList Name");
                                System.out.format("%-15s %-15s\n", rh.getInt(1), rh.getString(2));
                            }
                        }
                    }
                }
            }
            else
            {
                System.out.println("User Id doesn't Exists");
                System.exit(0);
            }
        }
        catch (SQLException e) {
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