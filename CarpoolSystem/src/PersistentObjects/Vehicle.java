package PersistentObjects;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents a vehicle object.
 */
public class Vehicle implements PersistentObject
{
	public int id;
	public int seats;
	public String make;
	public String model;
	public int year;
	public String color;

	public Vehicle() { };

	public Vehicle(int seats, String make, String model, int year, String color)
	{
		this.seats = seats;
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
	}

	public Vehicle(int id, int seats, String make, String model, int year, String color)
	{
		this.id = id;
		this.seats = seats;
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
	}
	
	public void setID(int id) { this.id = id; }
	public void setSeats(int s)	{ seats = s; }
	public void setMake(String m) { make = m; }
	public void setModel(String mod) { model = mod; }
	public void setYear(int y) { year = y; }
	public void setColor(String c) { color = c; }

	public int getID() { return id; }
	public int getSeats() { return seats; }
	public String getMake() { return make; }
	public String getModel() { return model; }
	public int getYear() { return year; }
	public String getColor() { return color; }

    @Override
	public String toString()
	{
		return "" + year + " " + color + " " + make + " " + model + " with " + seats + " seats available";
	}

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
	     * @return an arraylist of all PersistentObjects within a vehicle
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
