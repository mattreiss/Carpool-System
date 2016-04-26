
/**
 * A class that represents a carpool object.
 */
public class Carpool 
{
	
	private Ride ride;
	private ScheduleItem schedule; 
	private String time;
	private int driver_status;
	private int passenger_status;
	
	public Carpool(Ride ride, ScheduleItem schedule, String time, int driver_status, int passenger_status) 
	{
		this.ride = ride; 
		this.schedule = schedule; 
		this.time = time;
		this.driver_status = driver_status;
		this.passenger_status = passenger_status;
	}
	
	public Ride getRide() 
	{
		return ride; 
	}
	
	public ScheduleItem getSchedule() 
	{
		return schedule; 
	}
	
	public String getTime() 
	{
		return time; 
	}
	
	public int getDriverStatus() { return driver_status; }
	public int getPassengerStatus() { return passenger_status; }
	
	
	public void setRide(Ride ride) 
	{
		this.ride = ride; 
	}
	
	public void setSchedule(ScheduleItem schedule) 
	{
		this.schedule = schedule; 
	}
	
	public void setTime(String time) 
	{
		this.time = time;
	}
	
	public void setDriverStatus(int status) { driver_status = status; }
	public void setPassengerStatus(int status) { passenger_status = status; }
	
	
	

}
