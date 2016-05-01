package PersistentObjects;
import java.sql.ResultSet;
import java.util.Scanner;

import System.Alert;
import System.DatabaseConnection;


public interface PersistentObject
{	
	public static final Alert alert = new Alert();
	public static final DatabaseConnection db = DatabaseConnection.getDatabaseConnection();;
	
	public int getID();
	public void setID(int id);
    public String create();
    public String retrieve();
    public String update();
    public String delete();
    public void parseResultSet(ResultSet rs);
    public void manage(Scanner in);
}
