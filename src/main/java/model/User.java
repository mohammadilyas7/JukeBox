package main.java.model;

public class User //extends UserAchivment
{

}




//    static Connection connection = null;
//    Scanner scan = new Scanner(System.in);
//
//    ImplementOperation implementOp = new ImplementOperation();
//
//    private static Connection getconnetion() throws SongException, SQLException
//    {
//        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/song", "root", "Password@123");
//        return connection;
//    }
//
//    // Create an ArrayList to save resulting records
//    public List<Song> songList = new ArrayList<>();
//
//    @Override
//    public void register() throws SQLException, SongException
//    {
//        System.out.println("Enter You User_Name");
//        String user_Name = scan.nextLine();
//
//        System.out.println("Enter Your Email_Id");
//        String email = scan.nextLine();
//
//        System.out.println("Enter Your Mobile Number");
//        String moblie = scan.nextLine();
//
//        System.out.println("Enter Your PassWord");
//        String password = scan.nextLine();
//
//        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
//        //Creating a pattern object
//        Pattern pattern = Pattern.compile(regex);
//        //Creating a Matcher object
//        Matcher matcher = pattern.matcher(email);
//        //Verifying whether given phone number is valid
//        if (matcher.matches())
//        {
//            connection = getconnetion();
//
//            PreparedStatement pre1 = connection.prepareStatement(" insert into sign_up(User_Name,Email_Id,Mobile_No,Pass_Word) values(?,?,?,?)");
//            pre1.setString(1, user_Name);
//            pre1.setString(2, email);
//            pre1.setString(3, moblie);
//            pre1.setString(4, password);
//
//            int rowAffected = pre1.executeUpdate();
//
//            if (rowAffected != 0)
//            {
//                System.out.println("Account Created Successfully");
//            }
//            System.out.println("Given email id is valid");
//        } else
//        {
//            System.out.println("Given email id is not valid");
//        }
//
//    }
//
//    @Override
//    public void searchSongs(List<Song> songList) {
//
//    }
//
//
//    @Override
//    public void loggin() throws SQLException, SongException
//    {
//        System.out.println("Enter Your Email_Id ");
//        String emailId = scan.next();
//
//        System.out.println("Enter Your PassWord ");
//        String password = scan.next();
//
//        connection = getconnetion();
//        PreparedStatement pre2 = connection.prepareStatement("select Email_Id,Pass_Word from sign_up where Email_Id = ? AND Pass_Word = ?");
//        pre2.setString(1, emailId);
//        pre2.setString(2, password);
//
//        ResultSet resultSet = pre2.executeQuery();
//
//       try
//       {
//           while (resultSet.next())
//           {
//               User user = new User();
//               user.retrieveAllSong();
//           }
//       } catch (SQLException e)
//       {
//           throw new SQLException("Your EmailId IS Wrong Try Again");
//       } catch (SongException e)
//       {
//           System.out.println("Your EmailId IS Wrong Try Again");
//       }
//
//    }
//
//
//    public List<Song> retrieveAllSong() throws SQLException, SongException
//    {
//        System.out.println("\n**** ***** ***** <<<<<<<  Welcome to SongList  >>>>>> **** **** ****");
//
//        connection = getconnetion();
//        Statement statement = connection.createStatement();
//        ResultSet resu = statement.executeQuery("select * from songs");
//
//        while (resu.next())
//        {
//            songList.add(new Song(resu.getInt(1),resu.getString(2), resu.getString(3),resu.getString(4), resu.getFloat(5),resu.getDate(6),resu.getString(7), resu.getString(8)));
//        }
//        List<Song> sortedList = (List<Song>) songList.stream().sorted((p1, p2)->p1.getSong_Name().compareTo(p2.getSong_Name())).collect(Collectors.toList());
//        System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\nSong_Id","Song_Name","Genre","Artist","Duration","ReleaseDate","Album","SongSource");
//        sortedList.forEach(i-> System.out.format("%-15s%-15s%-15s%-20s%-15s%-15s%-15s%-15s","\n\n"+i.getSong_Id(),i.getSong_Name(),i.getGenre(),i.getArtist(),i.getDuration(),i.getReleasDate(),i.getAlbum(),i.getSource()));
//
////        User user = new User();
////        user.implementOperat();
////        implementOp.searchSongs(songList);
//        return sortedList;
//    }
//
////    public void implementOperat()
////    {
////        implementOp.searchSongs(songList);
////    }
//}











//    String equal_EmailId = "", equal_passWord = "";
//        System.out.println("email : " + equal_EmailId + "\npassword : " + equal_passWord);
//
//         if (equal_EmailId.equals(emailId) && equal_passWord.equals(password))
//         {
//         connection = getconnetion();
//         PreparedStatement pre3 = connection.prepareStatement("select * from songs");
//         ResultSet resu = pre3.executeQuery();
//
//         if (resu.next())
//         {
//         System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s","\n"+resu.getInt(1),resu.getString(2),resu.getString(3),resu.getString(4),resu.getFloat(5),resu.getDate(6),resu.getString(7),resu.getString(8));
//         }
//
//         }

//      else
//            {
//                if ((equal_EmailId.equals("")) && (equal_passWord.equals("")))
//                {
//                    System.out.println("Check Your PassWord Also Email_Id");
//                } else if (equal_passWord.equals("")) {
//                    System.out.println("Check Your Password");
//                } else if (equal_EmailId.equals("")) {
//                    System.out.println("Check Your Email_Id");
//                }
//            }
