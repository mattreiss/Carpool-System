package PersistentObjects;
import java.sql.ResultSet;
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

	public Vehicle() { id = -1;};

	public Vehicle(int seats, String make, String model, int year, String color)
	{
		this();
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

			System.out.println("Enter the number of seats: ");
			int seats = Integer.parseInt(in.nextLine());
			System.out.println("Enter the make: ");
			String make = in.nextLine();
			System.out.println("Enter the model: ");
			String model = in.nextLine();
			System.out.println("Enter the year: ");
			int year = Integer.parseInt(in.nextLine());
			System.out.println("Enter the color: ");
			String color = in.nextLine();

			Vehicle vehicle = new Vehicle(seats, make, model, year, color);
			System.out.println("    vehicle info: ");
			System.out.println("        seats: " + vehicle.getSeats());
			System.out.println("        make: " + vehicle.getMake());
			System.out.println("        model: " + vehicle.getModel());
			System.out.println("        year: " + vehicle.getYear());

			System.out.println("Is this correct? [y]es or [n]o:");
			String confirm = in.nextLine();
			if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
				return;
			
		}

}
