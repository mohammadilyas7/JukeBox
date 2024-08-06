package songImplements;

import SongDAOException.SongException;
import model.PodCast;
import model.Song;
import songConnection.SongConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SwitchCase
{
   Scanner scan = new Scanner(System.in);
   PlayListImpl playList = new PlayListImpl();
   PodcastImpl podcast = new PodcastImpl();
   PlayLIstDetailImpl playLIstDetail = new PlayLIstDetailImpl();

   public void Search() throws SongException, SQLException
   {
      UserImplement userImplement = new UserImplement();
      List<Song> songList = new ArrayList<>();
      songList = userImplement.retrieveAllSong();

      ImplementOperation imploperation = new ImplementOperation();
      List<PodCast> podCastList = new ArrayList<>();
      podCastList = imploperation.podcast();

      SwitchCase switchCase = new SwitchCase();
        int user_Id = switchCase.userid();

        if (user_Id == 0)
        {
           System.out.println("User Id Does't Exist");
        }
        else
        {
           int yes,choice;
           do
           {
              System.out.println("\n>>> Enter Your Choice \n1.   Songlist\n2.   Podcast\n3.   Display Plalist\n4.   CreatePlayList\n5.   insert Songs In PlayList");
              System.out.println("6.   Insert Podcast In Plalist\n7.   Display Songs\n8.   Display Podcast \n9.   Display Both");
              choice = scan.nextInt();
              switch (choice)
              {
                 case 1:userImplement.searchSongs(songList); break;      //songplay
                 case 2:imploperation.searchPodcast(podCastList);
                 case 3:
//                    playList.display_Plalist(user_Id);
                    playList.displayPlayList(user_Id);
                    break;
                 case 4: playList.createPlaylist(user_Id);  break;
                 case 5: playLIstDetail.insertOnlySongsInPlayList(user_Id);   break;
                 case 6: playLIstDetail.inserytOnlyPodcastInPlayList(user_Id);   break;
                 case 7: podcast.displaySong(user_Id);   break;           //songplay
                 case 8 :podcast.displaypodCastList(user_Id);  break;
                 case 9: podcast.displayBothSongPodcast(user_Id); break;
                 default:
                    System.out.println("Invalid Choice...?");
              }
              System.out.println("\n You Are Going to Men Menu Yes Press 1, No Press Any Key ");
              yes = scan.nextInt();
           }while (yes == 1 );
        }
   }



   public int userid() throws SQLException, SongException
   {
      System.out.println("\n<<<<<<  Enter Your user Id  >>>>>>>>");
      int id = scan.nextInt();
      int same_Id = 0;

      PreparedStatement pp = SongConnection.getconnetion().prepareStatement("SELECT User_Id FROM sign_up WHERE User_Id = ?");
      pp.setInt(1,id);
      ResultSet resultSet = pp.executeQuery();

      if (resultSet.next())
      {
         same_Id = resultSet.getInt(1);
      }
      return same_Id;
   }

}
