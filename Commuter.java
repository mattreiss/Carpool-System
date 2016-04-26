import java.util.ArrayList;
/**
 * A class that represents a commuter object.
 */
public class Commuter 
{
	private int id;
	private String firstName;
	private String lastName;
	private String email; 
	private String password; 
	private ArrayList<ScheduleItem> schedule = new ArrayList<>();;
	
	public Commuter(int id, String firstName, String lastName, String email, String password) 
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
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName; 
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName; 
	}
	

	public void setEmail(String email) 
	{
		this.email = email; 
	}
	
	public void setPassword(String password) 
	{
		this.password = password; 
	}
	
	public void addSchedule(ScheduleItem schedule)
	{
		this.schedule.add(schedule);
	}
    
    public void setSchedule(ArrayList<ScheduleItem> s) {
        this.schedule = s;
    }

}
