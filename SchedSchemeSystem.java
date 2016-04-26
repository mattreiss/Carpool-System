import java.util.ArrayList;

public class SchedSchemeSystem extends SchedSchemeTemplate {
	Vehicle v = null;
	Ride ride = null;
	
	@Override
	void getSchedule() {
		schedule = UI.viewSchedule("carpool_ride_id is null AND ride_id is null");
	}
	
	@Override
	boolean assignStep() {
		for (ScheduleItem s : matches) {
			boolean keep = false;
			if (s.getTo().getID() == selected.getTo().getID()) {
				
				// Traveling to school, check destination zip code
				if (s.getFrom().getZip() == selected.getFrom().getZip()) {
					keep = true;
				}
				
			} else {
				
				// Traveling from school, check origin zip code
				if (s.getTo().getZip() == selected.getTo().getZip()) {
					keep = true;
				} 
			}
			if (!keep)
				matches.remove(s);
		}
		
		return !matches.isEmpty();

	}
	
	@Override
	void carpoolStep() {
		for (ScheduleItem s : matches) {
			System.out.print("The following passengers were added and will be notified:");
			System.out.println(s.toString());
			carpool =  new Carpool(ride,s,s.getTime(), 1, 0);
			UI.dba.createCarpool(carpool);	
		}
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
			System.out.print("You must have a vehicle to create a ride");
			return false;
		} else {
			System.out.println("Please select the vehicle for the ride:");
			int index = Integer.parseInt(UI.in.nextLine());
			v = vehicles.get(index);
			return true;
		}
	}
}
