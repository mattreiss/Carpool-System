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
	public Vehicle(int commuterId) { this(); this.commuterId = commuterId; };
	
	public Vehicle(int commuterId, int vehId) {
		this.commuterId = commuterId;
		this.id = vehId;
		retrieve();
	}
	
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
		if (id <= 0) return null;
		String sql = "SELECT * FROM vehicle WHERE id = '" + id + "'";
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
	}
	
	public String retrieveCommuterVehicle() 
	{
		String sql = "SELECT * FROM vehicle WHERE driver_commuter_id = '" + commuterId + "'";
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
	}
	
		@Override
		public String update() 
		{
			String sql = "UPDATE vehicle SET capacity = '" + seats + "', make = '" + make + "', model = '"+ model 
					+ "', year = '" + year + "', color = '" + color + "', driver_commuter_id = '" + commuterId + "';";
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
			try	{
				if (rs.next()) {
					id = rs.getInt("id");
					seats = rs.getInt("capacity");
					make = rs.getString("make");
					model = rs.getString("model");
					year = rs.getInt("year");
					color = rs.getString("color");
				}
			} catch (SQLException eX) {
				System.out.println("SQLException: " + eX.getMessage());
				System.out.println("SQLState: " + eX.getSQLState());
				System.out.println("VendorError: " + eX.getErrorCode());
			}
		}



		@Override
		public void manage(Scanner in) 
		{
			System.out.println("**** Add Vehicle ****");
			System.out.println("Available seats: ");
			seats = Integer.parseInt(in.nextLine());
			System.out.println("Make: ");
			make = in.nextLine();
			System.out.println("Model: ");
			model = in.nextLine();
			System.out.println("Year: ");
			year = Integer.parseInt(in.nextLine());
			System.out.println("Color: ");
			color = in.nextLine();
		}

}
