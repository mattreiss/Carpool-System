package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *  A class that represents a ride of a driver
 *
 */
public class Ride implements PersistentObject
{
	private int id;
	private int vacancy;
	private Vehicle vehicle;
	private ScheduleItem driver_schedule;
	private int commuterID;
	
	public Ride() { id = -1; }
	public Ride(int vacancy, Vehicle vehicle, ScheduleItem schedule)
	{
		this.id = -1;
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.driver_schedule = schedule;
		this.commuterID = schedule.getCommuterID();
	}

	public Ride(int id, int vacancy, Vehicle vehicle, ScheduleItem schedule) {
		this.id = id;
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.driver_schedule = schedule;
	}
	
	public Ride(int commuterID) {
		this.commuterID = commuterID;
	}

	public int getID() {return id;}
	public int getVacancy() {return vacancy;}
	public Vehicle getVehicle() {return vehicle;}
	public ScheduleItem getSchedule() {return driver_schedule;}
	

	public void setID(int id) {this.id = id;}
	public void setVacancy(int v) {this.vacancy = v;}
	public void setVehicle(Vehicle v) {this.vehicle = v;}
	public void setSchedule(ScheduleItem s) {this.driver_schedule = s;}

	@Override
	public String toString()
	{
		if (driver_schedule == null || vehicle == null) return "ride is cancelled";
		return driver_schedule.toString() + " in " + vehicle.toString() + " with " + vacancy + " seats left";
	}
	
	@Override
	public String create() 
	{
    	String table = "ride (vacancy, driver_schedule_id, vehicle_id)";
		String values = "'" + vacancy + "', '" + driver_schedule.getID() + "', '" + vehicle.getID() + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		id = db.create(sql);
		return sql;
	}

	@Override
	public String retrieve() 
	{
		String sql = "SELECT * FROM ride WHERE id = '" + id + "'";
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
	}

	@Override
	public String update() 
	{
		String sql = "UPDATE ride SET vacancy = " + vacancy + ", "
				   + "driver_schedule_id = " + driver_schedule.getID() + ", "
				   + "vehicle_id = "+ vehicle.getID() 
				   + " WHERE id = " + id;
		db.update(sql);
		return sql;
	}

	@Override
	public String delete() 
	{
		String sql = "DELETE FROM ride WHERE id = '" + id + "'";
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
				id = rs.getInt("id");
				vacancy = rs.getInt("vacancy");
				vehicle = new Vehicle(commuterID, rs.getInt("vehicle_id"));
				driver_schedule = new ScheduleItem(commuterID, rs.getInt("driver_schedule_id"));
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
		vehicle = new Vehicle(commuterID);
		vehicle.retrieveCommuterVehicle();
		if (vehicle.getID() <= 0) //if no vehicle exists
		{
			System.out.println("You need to add vehicle to drive. Add a vehicle? [y]es or [n]o");
			if (in.nextLine().equalsIgnoreCase("y") || in.nextLine().contains("y"))
			{
				vehicle.manage(in);
				vehicle.create();
			} else 
			{
				vehicle = null;
				return;
			}
		}
		vacancy = vehicle.getSeats();
	}
	
}
