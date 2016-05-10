package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

public class ScheduleItem implements PersistentObject
{
    private int id;
    private String startDate;
    private String endDate;
    private int weekday; 
    private String time; 
    private Location from;
    private Location to;
    private int commuterID;
    private String commuterName;
    private Ride ride;
    
    String[] dow = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public ScheduleItem() { id = -1; } 
    
    public ScheduleItem(int commuterID) 
    { 
    	this.commuterID = commuterID; 
    	this.id = -1; 
    }
    
    public ScheduleItem(int commuterID, int schedId) 
    { 
    	this.commuterID = commuterID; 
    	this.id = schedId;
    	retrieve();
    }
    
    public ScheduleItem (int id, int day, String time, Location from, Location to) 
    {
    	this.id = id;
    	this.weekday = day;
    	this.time = time;
    	this.from = from;
    	this.to = to;
    }
    
    public ScheduleItem (int id, String startDate, String endDate, int day, String time, 
			 Location from, Location to, String name, Ride ride) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekday = day;
		this.time = time;
		this.from = from;
		this.to = to;
		this.commuterName = name;
		this.ride = ride;
    }

	public int getID()				{return id;}
	public String getStartDate()	{return startDate;}
	public String getEndDate()		{return endDate;}
    public int getWeekDay() 		{return weekday;}
    public String getTime() 		{return time;}
    public Location getFrom()		{return from;}
    public Location getTo()			{return to;}
	public int getCommuterID()		{return commuterID;}
	public String getCommuterName()	{return commuterName;}
	public Ride getRide()			{return ride;}
	
    public void setID(int id)					{this.id = id;}
    public void setDay(int day)					{this.weekday = day;}
    public void setTime(String time)			{this.time = time;}
    public void setFrom(Location from)			{this.from = from;}
    public void setTo(Location to)				{this.to = to;}
    public void setCommuterID(int id)			{this.commuterID = id;}
    
    @Override
    public String toString()
    {
    	String result = commuterName + " ";
    	result += dow[weekday-1] + " at ";
    	result += time + " from ";
    	result += from.getAddress() + " to ";
    	result += to.getAddress();
    	
    	return result;
    }

	@Override
	public String create() 
	{
		String table = "schedule (start_date, end_date, weekday, commit_time, commuter_id, from_location_id, to_location_id)";
		String values = "'2016-1-20','2016-5-15','" + weekday + "', '" + time + "', " + commuterID + "," + from.getID() + "," + to.getID();
		String sql = "INSERT INTO " + table + " VALUES (" + values + ");";
		id = db.create(sql);
		
		//alert.setAlert("NEW SCHEDULE ALERT!!!"); // use of observer pattern.
		
		return sql;
	}

	@Override
	public String retrieve() {
		String sql = "SELECT * FROM commuterschedule WHERE schedule_id = " + id;
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
	}

	@Override
	public String update() 
	{
		String sql = "UPDATE schedule SET weekday = " + weekday + ", commit_time = '" + time + "', commuter_id = "
				+ commuterID + ", from_location_id = " + from.getID() +", to_location_id = " + to.getID() + " "
				+ "WHERE id = " + id + ";";
		db.update(sql);
		return sql;
	}

	@Override
	public String delete() 
	{
		String sql = "DELETE FROM schedule WHERE id = " + id;
		db.update(sql);
		return sql;
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
				from = new Location(
						rs.getInt("from_location_id"),
						rs.getString("from_address"),
						rs.getInt("from_zip"));
				to = new Location(
						rs.getInt("to_location_id"),
						rs.getString("to_address"),
						rs.getInt("to_zip"));
				commuterName = rs.getString("name");
				
				int rideId = rs.getInt("ride_id");
				Ride newRide = null;
				if (!rs.wasNull()) {
					Vehicle v = new Vehicle(
							rs.getInt("vehicle_id"),
							rs.getInt("capacity"), rs.getString("make"),
							rs.getString("model"), rs.getInt("year"),
							rs.getString("color"));
					newRide = new Ride(
							rideId,
							rs.getInt("vacancy"), v, this);
				}
				ride = newRide;
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
	}

	public String match(String sqlCrit) {
		String sql = 
				"SELECT * FROM commuterschedule "
			  + "WHERE ("
					+ "(from_location_id = " + from.getID() + ")"
					+ " OR "
					+ "(to_location_id = " + to.getID() + "))"
				+ " AND weekday = " + weekday 
				+ " AND commuter_id <> " + commuterID + " " + sqlCrit;
		return sql;
	}

	@Override
	public void manage(Scanner in) 
	{
		System.out.println("\nNew Schedule:");
		
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
		
		create();
	}

}
