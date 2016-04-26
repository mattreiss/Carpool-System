import java.util.ArrayList;

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
		UI.dba.createCarpool(carpool);
	}

	@Override
	void matchScheduleStep() {
		matches = UI.dba.matchSchedules
				(ride.getSchedule(),UI.commuter.getID(), false);
	}
	
	/*
	 * Create new tuple in the ride table
	 */
	@Override
	void rideStep() {
		ride = new Ride(v.getSeats(), v, selected);
		ride.setID(UI.dba.createRide(ride));	
	}
	
	/*
	 * Select vehicle for create ride
	 */
	@Override
	boolean vehicleStep() {
		ArrayList<Vehicle> vehicles = UI.viewVehicles();
		if (vehicles == null) {
			System.out.println("You must have a vehicle to create a ride.");
			return false;
		} else {
			System.out.println("Please select the vehicle for the ride:");
			int index = Integer.parseInt(UI.in.nextLine());
			v = vehicles.get(index);
			return true;
		}
	}

}
