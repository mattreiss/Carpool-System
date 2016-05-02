package PersistentObjects;
import java.sql.ResultSet;
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
	public String create() {
		String sql = "INSERT INTO ride (vacancy, driver_schedule_id, vehicle_id) "
				+ "VALUES ('" + getVacancy() + "', '"
				+ getSchedule().getID() + "', '"
				+ getVehicle().getID() + "')";
                return sql;
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
	public void manage(Scanner in) {
		// TODO Auto-generated method stub
		
	}
	
}
