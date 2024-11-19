package implementation;

import myExceptions.MyException;

import java.sql.SQLException;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws MyException, SQLException {
        Scanner sc = new Scanner(System.in);
        UserImpl obj = new UserImpl(); // Creating a obj of UserImpl class
        System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 JukeBox 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
        char choice;
        do
        {
            int userChoice;
            System.out.println("\n1.\tSign Up");
            System.out.println("2.\tLogin");
            System.out.println("3.\tExit");
            userChoice = sc.nextInt();
            switch (userChoice) {
                case 1:
                    obj.signUp(); // With UserImpl obj i am calling signUp() method which is a non static method
                    break;
                case 2:
                    obj.login(); // With UserImpl obj i am calling login() method which is a non static method
                    break;
                case 3:
                    System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Thank You For Visiting JukeBox 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter a correct choice !");
            }
            System.out.println("\nDo you want to continue ? y/n");
            choice = sc.next().charAt(0);
        }while (choice == 'y' || choice == 'Y');
        System.out.println("\n🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵 Thank You For Visiting JukeBox 🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵🎵");
    }
}