package PersistentObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import States.CancelledState;
import States.CarpoolState;
import States.ConfirmedState;
import States.PendingState;
import States.TrackingState;

/**
 * A class that represents a carpool object.
 */
public class Carpool implements PersistentObject
{
	private int id;
	private Ride ride;
	private ScheduleItem schedule; 
	private String time;
	private int driver_status;
	private int passenger_status;
	public CarpoolState state;
	
	public Carpool() { this.id = -1; }
	
	public Carpool(Ride ride, ScheduleItem schedule, String time, int driver_status, int passenger_status) 
	{
		this();
		this.ride = ride; 
		this.schedule = schedule; 
		this.time = time;
		this.driver_status = driver_status;
		this.passenger_status = passenger_status;
	}
	
	public Carpool(int id, Ride ride, ScheduleItem schedule, String time, int driver_status, int passenger_status) 
	{
		this.id = id;
		this.ride = ride; 
		this.schedule = schedule; 
		this.time = time;
		this.driver_status = driver_status;
		this.passenger_status = passenger_status;
	}

	public int getID() { return id;  }
	public Ride getRide() { return ride;  }
	public ScheduleItem getSchedule() { return schedule;  }
	public String getTime()  { return time;  }
	public int getDriverStatus() { return driver_status; }
	public int getPassengerStatus() { return passenger_status; }
	public CarpoolState getRequestState() { return state; }
	

	public void setID(int id) { this.id = id;  }
	public void setRide(Ride ride)  { this.ride = ride;  }
	public void setSchedule(ScheduleItem schedule)  { this.schedule = schedule;  }
	public void setTime(String time)  { this.time = time; }
	public void setDriverStatus(int status) { driver_status = status; }
	public void setPassengerStatus(int status) { passenger_status = status; }
	public void setState(CarpoolState state) { this.state = state; }

	@Override
	public String create() {
    	String table = "carpool (driver_status, passenger_status, meet_time, passenger_schedule_id, ride_id)";
		String values = driver_status + ", " + passenger_status + ", '" + time + "', " 
						+ schedule.getID() + ", " + ride.getID();
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		id = db.create(sql);
		return sql;
	}

	@Override
	public String retrieve() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update()
	{
		String sql = "UPDATE carpool SET driver_status = " + driver_status + ", "
				   + "passenger_status = " + passenger_status + ", "
				   + "meet_time = '"+ time 
				   + "' WHERE id = " + id;
		db.update(sql);
		return sql;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseResultSet(ResultSet rs) 
	{
		try
		{
			if (rs.next())
			{
				int p = rs.getInt("passenger_status");
				int d = rs.getInt("driver_status");
				if ((p == 0 && d == 1) ||( p == 1 && d == 0))
				{
					state = new PendingState();
				} else if (p == 1 && d == 1)
				{
					state = new ConfirmedState();
				} else if (p+d <= 0)
				{
					state = new CancelledState();
				} else
				{
					state = new TrackingState();
				}
				driver_status = d;
				passenger_status = p;
				id = rs.getInt("carpool_id");
				schedule = new ScheduleItem();
				schedule.setID(rs.getInt("passenger_schedule_id"));
				schedule.retrieve();
				ride = new Ride();
				ride.setID(rs.getInt("ride_id"));
				ride.retrieve();
				time = rs.getString("commit_time");
				state.setStatus(this);
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
		System.out.println("[1] Accept Request");
		System.out.println("[2] Decline Request");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			state.accept(this);
			break;
		case "2":
			state.decline(this);
			break;
		case "0":
		default:
			System.out.println("Cancelling...");
		}
	}

	@Override
	public String toString()
	{
		return state.toString();
	}

	public void track(Scanner in)
	{
		System.out.println("[1] Confirm");
		System.out.println("[2] Cancel");
		System.out.println("[0] Done");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			state.accept(this);
			break;
		case "2":
			state.decline(this);
			break;
		}
	}

	public boolean isCancelled()
	{
		return getDriverStatus() == 0 && getPassengerStatus() == 0;
	}
	public boolean isPending()
	{
		return (getDriverStatus() == 1 && getPassengerStatus() == 0)||(getDriverStatus() == 0 && getPassengerStatus() == 1);
	}
}
