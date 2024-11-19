package main.java.songConnection;

import SongDAOException.SongException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SongConnection
{
    Connection conn =null;
    public static Connection getconnetion() throws SongException, SQLException
    {
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/song", "root",
                "Password@123");
        return conn;
    }
}
