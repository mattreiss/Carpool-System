package Default;
import java.util.ArrayList;

import PersistentObjects.Carpool;
import PersistentObjects.Ride;
import PersistentObjects.Vehicle;
import System.SystemInterface;

/**
 * Handles implementation for driver chosen scheduling
 * @author LanceB
 *
 */
public class SchedSchemeDriver extends SchedSchemeTemplate {
	Vehicle v = null;
	Ride ride = null;
	
	@Override
	void carpoolStep() {
		carpool =  new Carpool(ride,matched,matched.getTime(), 1, 0);
		SystemInterface.dba.createCarpool(carpool);
	}

	@Override
	void matchScheduleStep() {
		matches = SystemInterface.dba.matchSchedules
				(ride.getSchedule(),SystemInterface.commuter.getID(), false);
	}
	
	/*
	 * Create new tuple in the ride table
	 */
	@Override
	void rideStep() {
		ride = new Ride(v.getSeats(), v, selected);
		ride.setID(SystemInterface.dba.createRide(ride));	
	}
	
	/*
	 * Select vehicle for create ride
	 */
	@Override
	boolean vehicleStep() {
		ArrayList<Vehicle> vehicles = SystemInterface.viewVehicles();
		if (vehicles == null) {
			System.out.println("You must have a vehicle to create a ride.");
			return false;
		} else {
			System.out.println("Please select the vehicle for the ride:");
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			v = vehicles.get(index);
			return true;
		}
	}

}
