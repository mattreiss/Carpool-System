
public class Location
{
	private int id;
	private String address;
	private int zip;
	private float latitude;
	private float longitude;

	public Location (String address, int zip) 
	{
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
	
}
