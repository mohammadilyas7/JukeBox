package operation;

import model.Songs;
import myExceptions.MyException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IUserOperations extends AutoCloseable
{
    //IAutoCloseable Interface will auto close all resources once work is done it comes from java.lang package

    public void signUp() throws MyException;
    public void login() throws MyException;
    public void userMenu() throws MyException, SQLException, ParseException;

}
