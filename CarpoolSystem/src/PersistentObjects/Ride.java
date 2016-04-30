package PersistentObjects;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Ride implements PersistentObject
{
	private int id;
	private int vacancy;
	private Vehicle vehicle;
	private ScheduleItem schedule;
	
	public Ride(int vacancy, Vehicle vehicle, ScheduleItem schedule)
	{
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.schedule = schedule;
	}

	public Ride(int id, int vacancy, Vehicle vehicle, ScheduleItem schedule)
	{
		this.id = id;
		this.vacancy = vacancy;
		this.vehicle = vehicle;
		this.schedule = schedule;
	}
	
	public int getID() {return id;}
	public int getVacancy() {return vacancy;}
	public Vehicle getVehicle() {return vehicle;}
	public ScheduleItem getSchedule() {return schedule;}
	

	public void setID(int id) {this.id = id;}
	public void setVacancy(int v) {this.vacancy = v;}
	public void setVehicle(Vehicle v) {this.vehicle = v;}
	public void setSchedule(ScheduleItem s) {this.schedule = s;}

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

	@Override
	public void manage(Scanner in) {
		// TODO Auto-generated method stub
		
	}

    /**
     * @return an arraylist of all PersistentObjects within a ride
     */
	@Override
	public ArrayList<PersistentObject> getPersistentObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
