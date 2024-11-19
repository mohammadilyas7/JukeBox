package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection
{
    public Connection getConnection()
    {
        String url = "jdbc:mysql://localhost:3306/jukebox";
        String username = "root";
        String password = "Hamza@7137";
        try {
            // DriverManager is predefined class it contains getConnection() method it is a static and overloaded method, which is used to establish a connection with backend, it returns Connection type of value so we are storing it in Connection Interface obj.
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        }
        catch (SQLException se)
        {
            System.out.println("Error obtaining connection with the database : " +se);
            System.exit(-1);
            return null;
        }
    }
}
