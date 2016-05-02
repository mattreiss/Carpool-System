package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public int commuterId;

	public Vehicle() { id = -1;};
	public Vehicle(int commuterId) { this.commuterId = commuterId;};

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
		public String create() 
		{
			String sql = "INSERT INTO vehicle (capacity, make, model, year, color, driver_commuter_id) "
					+ "VALUES ('" + getSeats() + "', '" + getMake() + "', '"
					+ getModel() + "', '" + getYear() + "', '" + getColor()
					+ "', '" + commuterId + "')";
			setID(db.create(sql));
			return sql;
		}

		@Override
		public String retrieve() 
		{
			if (commuterId <= 0) return null;
			ArrayList<Vehicle> vehicles = new ArrayList<>();
			String sql = "SELECT * FROM vehicle WHERE driver_commuter_id = '" + commuterId + "'";
			parseResultSet(db.retrieve(sql));
			db.closeConnection();
			return sql;
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
		public void parseResultSet(ResultSet rs) 
		{
			try
			{
				if (rs.next())
				{
					seats = rs.getInt("capacity");
					make = rs.getString("make");
					model = rs.getString("model");
					year = rs.getInt("year");
					color = rs.getString("color");
					id = rs.getInt("id");
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
			System.out.println("\nAdd Vehicle: ");
			System.out.println("Make: ");
			make = in.nextLine();
			System.out.println("Model: ");
			model = in.nextLine();
			System.out.println("Year: ");
			year = Integer.parseInt(in.nextLine());
			System.out.println("Color: ");
			color = in.nextLine();
			System.out.println("Seat capacity: ");
			seats = Integer.parseInt(in.nextLine());
		}

}
