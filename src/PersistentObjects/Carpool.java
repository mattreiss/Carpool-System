package PersistentObjects;

import java.sql.ResultSet;
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
	public String create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String retrieve() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseResultSet(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manage(Scanner in) 
	{
		System.out.println("[1] Request a ride");
		System.out.println("[2] Create a ride");
		System.out.println("[3] Find passengers for my ride");
		System.out.println("[4] View my rides");
		System.out.println("[5] Cancel a ride");
		System.out.println("[6] See rewards");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			//scheduleRide(new SchedSchemeRider());
			break;
		case "2":
			//scheduleRide(new SchedSchemeSystem());
			break;
		case "3":
			//scheduleRide(new SchedSchemeDriver());
			break;
		case "4":
			//viewRides(null);
			break;
		case "5":
			//cancelRide();
			break;
		case "6":
			//seeRewards();
			break;
		case "0":
		default:
			System.out.println("Cancelling...");
		}
	}

}
