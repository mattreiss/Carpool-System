package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * A class that represents a commuter object.
 */
public class Commuter implements PersistentObject
{
	private int id;
	private String firstName;
	private String lastName;
	private String email; 
	private String password; 
	private ArrayList<ScheduleItem> schedule = new ArrayList<>(); // has a Persistent object
	public Vehicle vehicle; // has a Persistent object
	boolean shouldPersist = true;
	
	public Commuter(int id, String firstName, String lastName, String email, String password) 
	{
		update(id,firstName,lastName,email,password);
	}
	
	public void update(int id, String firstName, String lastName, String email, String password) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public int 		getID() 		{return id;}
	public String 	getFirstName() 	{return firstName;}
	public String 	getLastName() 	{return lastName;}
	public String 	getEmail() 		{return email;}
	public String 	getPassword() 	{return password;}
	public ArrayList<ScheduleItem> getSchedule()	{return schedule;}
	public Vehicle getVehicle() { return vehicle; }
	
	public void setID(int id) { this.id = id; }
	public void setFirstName(String firstName)  { this.firstName = firstName; }
	public void setLastName(String lastName)  { this.lastName = lastName; }
	public void setEmail(String email)  { this.email = email;  }
	public void setPassword(String password)  { this.password = password;  }
    public void setSchedule(ArrayList<ScheduleItem> s) { this.schedule = s; }
	public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

	public void addSchedule(ScheduleItem schedule) { this.schedule.add(schedule); }
	
    public String getCreateSQL()
    {
    	String table = "commuter (first_name, last_name, email, password)";
		String values = "'" + firstName + "', '" + lastName
				+ "', '" + email+ "', '" + password + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		return sql;
    }

    public String getRetrieveSQL()
    {
		String sql = "SELECT * FROM commuter WHERE email = '" + email + "'";
		return sql;
    }
    
    public String getUpdateSQL()
    {	
		String sql = "UPDATE commuter SET email = '" + email + "', password = '" + password + "', first_name = '"
				+ firstName + "', last_name = '" + lastName + "' WHERE id = '" + id + "';";
		return sql;
    }
    
    public String getDeleteSQL()
    {
		return "DELETE FROM commuter WHERE id = '" + id + "'";
    }
    
    public String getRetrieveScheduleSQL()
    {
		String sql = "SELECT * FROM commuterschedule WHERE commuter_id  = '" + id + "'";
		return sql;
    }
    
    public void parseScheduleResultSet(ResultSet rs)
    {
    	schedule = new ArrayList<ScheduleItem>();
		try
		{
			while (rs.next())
			{
				rs.previous();
				ScheduleItem s = new ScheduleItem(id);
				s.parseResultSet(rs);
				schedule.add(s);
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }

    public void parseResultSet(ResultSet rs)
    {
    	//try to set variables from result set
    	try
		{
			if (rs != null && rs.next())
			{
				if (password.equals(rs.getString("password")))
				{
					update( rs.getInt("id"),
						    rs.getString("first_name"),
							rs.getString("last_name"), 
							rs.getString("email"),
							rs.getString("password"));
				} else
					System.out.println("Invalid Password.");
			} else
				System.out.println("User not registered.");
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }
    
    public void manage(Scanner in)
    {
    	System.out.println("[1] View Account");
		System.out.println("[2] Edit Account");
		System.out.println("[3] Delete Account");
		System.out.println("[0] Cancel");
		
		String res = in.nextLine();
		switch (res)
		{
		case "1"://View Account
			System.out.println("\n\n*****Account Information*****");
			System.out.println("Name: " + firstName + " " + lastName);
			System.out.println("Email: " + email);
			System.out.println("Password: " + password);
			break;
		case "2"://Edit Account
			boolean done = false;
			while (!done)
			{
				System.out.println("\nEdit Account:");
				System.out.println("[1] First name : " + firstName);
				System.out.println("[2] Last name : " + lastName);
				System.out.println("[3] Email : " + email);
				System.out.println("[4] Password : " + password);
				System.out.println("[0] Done");
				String choice = in.nextLine();
				switch (choice)
				{
				case "1":
					System.out.println("First name:");
					setFirstName(in.nextLine());
					break;
				case "2":
					System.out.println("Last name:");
					setLastName(in.nextLine());
					break;
				case "3":
					System.out.println("Email:");
					setEmail(in.nextLine());
					break;
				case "4":
					System.out.println("Password:");
					setPassword(in.nextLine());
					break;
				case "0":
					done = true;
					break;
				}
			}
			break;
		case "3"://Delete Account
			System.out.println("Are you sure you want to delete your account? [y]es or [n]o:");
			String confirm = in.nextLine();
			if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
			{
				System.out.println("Deleting Account...");
				this.shouldPersist = false;
			}
			break;
		case "0"://Cancel
		default:
			System.out.println("Cancelling...");
		}
    }
    
    public void manageSchedule(Scanner in)
    {
		System.out.println("[1] Add Schedule");
		System.out.println("[2] View Schedule");
		System.out.println("[3] Edit Schedule");
		System.out.println("[4] Delete Schedule");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1": // Add Schedule
			ScheduleItem s = new ScheduleItem(id);
			s.manage(in);
			schedule.add(s);
		case "2": // View Schedule
			System.out.println("\n\n*****My Schedule*****");
			for (int i = 0; i < schedule.size(); i++)
			{
				if (schedule.get(i).shouldPersist)
					System.out.println("[" + (i+1) + "] " + schedule.get(i).toString());
			}
			break;
		case "3": // Edit Schedule
			boolean done = false;
			while (!done)
			{	
				System.out.println("Edit Schedule:");
				
				for (int i = 0; i < schedule.size(); i++)
				{
					if (schedule.get(i).shouldPersist)
						System.out.println("[" + (i+1) + "] " + schedule.get(i).toString());
				}
				System.out.println("[0] Done");
				String n = in.nextLine();
				if (n.contains("0")) { done = true; break; }
				for (int i = 0; i < schedule.size(); i++)
				{
					if ( n.contains(""+ (i+1)) )
					{
						schedule.get(i).manage(in);
						break;
					}
				}
			}
			break;
		case "4": // Delete Schedule
			done = false;
			while (!done)
			{	
				System.out.println("Delete Schedule:");
				for (int i = 0; i < schedule.size(); i++)
				{
					if (schedule.get(i).shouldPersist)
						System.out.println("[" + (i+1) + "] " + schedule.get(i).toString());
				}
				System.out.println("[0] Done");
				String n = in.nextLine();
				if (n.contains("0")) { done = true; break; }
				for (int i = 0; i < schedule.size(); i++)
				{
					if ( n.contains(""+(i+1)) )
					{
						System.out.println("Are you sure you want to delete this schedule? [y]es or [n]o");
						String confirm = in.nextLine();
						if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
						{
							System.out.println("Deleting Schedule...");
							schedule.get(i).shouldPersist = false;
						}
						break;
					}
				}
				
			}
			break;
		case "0": // Cancel
		default:
			System.out.println("Cancelling...");
		}
    }

    /**
     * @return an arraylist of all PersistentObjects within a commuter
     */
	@Override
	public ArrayList<PersistentObject> getPersistentObjects() 
	{
		ArrayList<PersistentObject> objects = new ArrayList<PersistentObject>();
		for (ScheduleItem s: schedule)
		{
			for (PersistentObject p: s.getPersistentObjects())
				objects.add(p);
			objects.add(s);
		}
		if (vehicle != null) objects.add(vehicle);
		return objects;
	}

	@Override
	public boolean isPersistent() 
	{
		return shouldPersist;
	}
}
