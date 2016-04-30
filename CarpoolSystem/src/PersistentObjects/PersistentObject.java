package PersistentObjects;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public interface PersistentObject 
{	
	public int getID();
	public void setID(int id);
    public String getCreateSQL();
    public String getRetrieveSQL();
    public String getUpdateSQL();
    public String getDeleteSQL();
    public void parseResultSet(ResultSet rs);
    public void manage(Scanner in);
	public ArrayList<PersistentObject> getPersistentObjects();
	public boolean isPersistent();
}
