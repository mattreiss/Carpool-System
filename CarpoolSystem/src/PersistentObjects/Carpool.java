package PersistentObjects;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public Carpool() { this.id = -1; }
	
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
	

	public void setID(int id) { this.id = id;  }
	public void setRide(Ride ride)  { this.ride = ride;  }
	public void setSchedule(ScheduleItem schedule)  { this.schedule = schedule;  }
	public void setTime(String time)  { this.time = time; }
	public void setDriverStatus(int status) { driver_status = status; }
	public void setPassengerStatus(int status) { passenger_status = status; }

	@Override
	public String getCreateSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRetrieveSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeleteSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseResultSet(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}


    /**
     * @return an arraylist of all PersistentObjects within a carpool
     */
	@Override
	public ArrayList<PersistentObject> getPersistentObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void manage(Scanner in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
