

/**
 * A class that represents a driver object.
 */
public class Driver extends Commuter
{
	public Vehicle vehicle;

	public Driver(int id, String firstName, String lastName, String email, String password, Vehicle vehicle) 
	{
            super(id, firstName, lastName, email, password);
            this.vehicle = vehicle;
	}
	
	public Vehicle getVehicle() { return vehicle; }
	
	public void setVehicle(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}
}
