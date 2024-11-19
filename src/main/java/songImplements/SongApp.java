package main.java.songImplements;
import main.java.SongDAOException.SongException;
import java.sql.SQLException;
import java.util.Scanner;

public class SongApp
{
    public static void main(String[] args) throws SQLException, SongException
    {
        System.out.println("*****  *****  <<<<  WelCome JuckBox   >>>>  *****  *****");

        UserImplement userImplement = new UserImplement();

        Scanner scan = new Scanner(System.in);

        System.out.println("\n<<<<<  *****  Select Your Choice  *****  >>>>>");
        int choice,yes;

          do
          {
              System.out.println("1. Register\n2. Loggin\n3. Exist");
              choice = scan.nextInt();
              switch (choice)
              {
                  case 1: userImplement.register();break;
                  case 2: userImplement.loggin();break;
                  case 3: System.exit(0);break;
                  default:
                      System.out.println("Invalid Choice !");
              }
              System.out.println("\nDo You Login Or Register the Account \n1.  Yes\n.2   No");
              yes = scan.nextInt();
          }while (yes == 1);
    }




}
