package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScheduleItem implements PersistentObject
{
    private int id;
    private int weekday; 
    private String time; 
    private Location from;
    private Location to;
    private int commuterID;
	boolean shouldPersist = true;
    
    String[] dow = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public ScheduleItem(int commuterID) 
    { 
    	this.commuterID = commuterID; 
    	this.id = -1; 
    }
    
    public ScheduleItem (int id, int day, String time, Location from, Location to) 
    {
    	this.id = id;
    	this.weekday = day;
    	this.time = time;
    	this.from = from;
    	this.to = to;
    }
    

	public int getID()			{return id;}
    public int getWeekDay() 	{return weekday;}
    public String getTime() 	{return time;}
    public Location getFrom()	{return from;}
    public Location getTo()		{return to;}
	public int getCommuterID()	{return commuterID;}
    
    public void setID(int id)					{this.id = id;}
    public void setDay(int day)					{this.weekday = day;}
    public void setTime(String time)			{this.time = time;}
    public void setFrom(Location from)			{this.from = from;}
    public void setTo(Location to)				{this.to = to;}
    public void setCommuterID(int id)			{this.commuterID = id;}
    
    @Override
    public String toString()
    {
    	String result = "";
    	result += dow[weekday-1] + " at ";
    	result += time + " from ";
    	result += from.getAddress() + " to ";
    	result += to.getAddress();
    	
    	return result;
    }

	@Override
	public String getCreateSQL() 
	{
		String table = "schedule (start_date, end_date, weekday, commit_time, commuter_id, from_location_id, to_location_id)";
		String values = "'2016-1-20','2016-5-15','" + weekday + "', '" + time + "', " + commuterID + "," + from.getID() + "," + to.getID();
		String sql = "INSERT INTO " + table + " VALUES (" + values + ");";
		return sql;
	}

	@Override
	public String getRetrieveSQL() {
		String sql = "SELECT * FROM schedule WHERE id = " + id;
		return sql;
	}

	@Override
	public String getUpdateSQL() 
	{
		String sql = "UPDATE schedule SET weekday = '" + weekday + "', commit_time = '" + time + "', commuter_id = '"
				+ commuterID + "', from_location_id = '" + from.getID() +"', to_location_id = '" + to.getID() + "' WHERE id = '" + id + "';";
		return sql;
	}

	@Override
	public String getDeleteSQL() 
	{
		return "DELETE FROM schedule WHERE id = " + id;
	}

	@Override
	public void parseResultSet(ResultSet rs) 
	{
		try
		{
			if (rs.next())
			{
				id = rs.getInt("schedule_id");
				weekday = rs.getInt("weekday");
				time = rs.getString("commit_time"); 
				from = new Location( rs.getInt("from_location_id"), rs.getString("from_address"), rs.getInt("from_zip"));
				to = new Location( rs.getInt("to_location_id"), rs.getString("to_address"), rs.getInt("to_zip"));
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
	}

	@Override
	public void manage(Scanner in) 
	{
		System.out.println("New Schedule:");
		
		System.out.println("Day [1]Sun [2]Mon [3]Tue [4]Wed [5]Thu [6]Fri [7]Sat:");
		if (weekday > 0 ) System.out.print("["+(weekday)+"]"+dow[weekday-1] + " -> ");
		else weekday = 1;	
		int w = 0; 	
		try { w = Integer.parseInt(in.nextLine()); } catch (Exception e) { };
		if (w > 0 && w < 8) weekday = w;
		
		System.out.println("Time (HH:MM):");
		if (time != null) System.out.print(time + " " + " -> ");
		String t = in.nextLine();
		if (!t.isEmpty()) time = t;
		
		System.out.println("Pick up location:");
		if (from == null) from = new Location();
		from.manage(in);
		
		System.out.println("Drop off location:");
		if (to == null) to = new Location();
		to.manage(in);
		
	}

    /**
     * @return an arraylist of all PersistentObjects within a scheduleItem
     */
	@Override
	public ArrayList<PersistentObject> getPersistentObjects() {
		// TODO Auto-generated method stub
		ArrayList<PersistentObject> objects =  new ArrayList<PersistentObject>();
		objects.add(to);
		objects.add(from);
		return objects;
	}

	@Override
	public boolean isPersistent() 
	{
		return shouldPersist;
	}
}
