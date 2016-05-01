package PersistentObjects;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Location implements PersistentObject
{
	private int id;
	private String address;
	private int zip;
	private float latitude;
	private float longitude;

	public Location() { this.id = -1; }
	
	public Location (String address, int zip) 
	{
		this();
		this.address = address;
		this.zip = zip;
	}

	public Location (int id, String address, int zip) 
	{
		this.id = id;
		this.address = address;
		this.zip = zip;
	}
	
	public int getID()              { return id; }
	public String getAddress() 	{ return address; }
	public int getZip() 		{ return zip; }
	public float getLatitude() 	{ return latitude; }
	public float getLongitude()     { return longitude; }

	public void setID(int id)               {this.id = id;}
	public void setAddress(String address)  {this.address = address;}
	public void setZip(int zip)             {this.zip = zip;}
	public void setLatitude(float lat)      {this.latitude = lat;}
	public void setLongitude(float lon)     {this.longitude = lon;}

	@Override
	public String create() 
	{
		String table = "location (address, zip, latitude, longitude)";
		String values = "'" + address + "', '" + zip + "', '" + latitude + "', '" + longitude + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		id = db.create(sql);
		return sql;
	}

	@Override
	public String retrieve() {
		String sql = "SELECT * FROM location WHERE id = '" + id + "'";
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
	}

	@Override
	public String update() 
	{
		String sql = "UPDATE location SET address = '" + address + "', zip = '" + zip + "', latitude = '"
				+ latitude + "', longitude = '" + longitude + "' WHERE id = '" + id + "'";
		db.update(sql);
		return sql;
	}

	@Override
	public String delete() 
	{
		String sql = "DELETE FROM location WHERE id = " + id;
		db.update(sql);
		return sql;
	}

	@Override
	public void parseResultSet(ResultSet rs) 
	{
		try
		{
			if (rs.next())
			{
				address = rs.getString("address");
				zip = rs.getInt("zip");
				latitude = rs.getInt("latitude");
				longitude = rs.getInt("longitude");
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
		System.out.print("    Address: ");
		if (address != null) System.out.print("\n    " + address + " -> ");
		String a = in.nextLine();
		if (!a.isEmpty()) address = a;
		
		System.out.print("    Zip: ");
		if (zip >= 9999) System.out.print("\n    " + zip + " -> ");
		else zip = 10000;
		int z = 0;
		try { z = Integer.parseInt(in.nextLine()); } catch (Exception e) { };
		if ( z > 0 && z < 8 ) zip = z;
		
		create();
	}
	
	@Override
	public String toString() {
		return "Address:"+address+", Zip:"+zip;
	}
	
}
