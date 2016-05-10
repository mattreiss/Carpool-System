package Scheduling;

import PersistentObjects.Vehicle;
import States.PendingState;
import PersistentObjects.Commuter;
import PersistentObjects.PersistentObject;
import System.SystemInterface;
import PersistentObjects.Ride;
import PersistentObjects.ScheduleItem;
import PersistentObjects.Carpool;

public class SchedSchemeSystem extends SchedSchemeTemplate {
	Vehicle v = null;
	Ride ride = null;
	
	@Override
	boolean chooseMatch() {
		for (int i=0; i < matches.size(); i++)  {
			boolean keep = false;
	// TODO:  Implement intelligent routing based on GPS coordinates
			if (matches.get(i).getTo().getID() == selected.getTo().getID()) {
				// Traveling to school, check destination zip code
				if (matches.get(i).getFrom().getZip() == selected.getFrom().getZip()) {
					keep = true;
				}
			} else {
				// Traveling from school, check origin zip code
				if (matches.get(i).getTo().getZip() == selected.getTo().getZip()) {
					keep = true;
				} 
			}
			if (!keep) {
				matches.remove(i--);
			}
		}
		return !matches.isEmpty();
	}
	
	@Override
	void saveCarpool() {
		String meetTime = "";
		
		System.out.println("\n Ride Created! You will be notified if any matches are found.");
		for (ScheduleItem s : matches) {
			if (ride.getVacancy() > 0) {
	// TODO:  Implement intelligent routing based on GPS coordinates
				try {
				if (ride.getVacancy() == ride.getVehicle().getSeats()) {
					meetTime = timeSubtract(s.getTime(), 15);
				} else {
					meetTime = timeSubtract(meetTime, 5);
				}
				} catch (NumberFormatException e) { meetTime = s.getTime();}
				carpool =  new Carpool(ride, s, meetTime, 1, 0);
				carpool.setState(new PendingState());
				carpool.state.setStatus(carpool);
				carpool.create();
				ride.update();
				

				//alert commuters matched to carpool
				PersistentObject.alert.setCarpool(carpool);
				ride.alert.setAlert("\nNew Carpool Match!");
				
			}
		}
	}
	
	private String timeSubtract(String time, int minuteDiff) {
		int hr = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3, 5));
		int newMin = min - minuteDiff;
		int newHr = hr;
		if (newMin < 0) {
			newMin += 60;
			newHr--;
		}
		return newHr + ":" + newMin;
	}	
	
	/*
	 * Create new tuple in the ride table
	 */
	@Override
	void createRide() {
		ride = new Ride(v.getSeats(), v, selected);
		ride.create();	
	}
	
	/*
	 * Select vehicle for create ride
	 */
	@Override
	boolean chooseVehicle(Commuter c) {
		if (c.getVehicle().size() == 0) {
			System.out.println("You need to add a vehicle to drive. Add a vehicle? [y]es or [n]o");
			if (SystemInterface.in.nextLine().equalsIgnoreCase("y") 
					|| SystemInterface.in.nextLine().contains("y")) {
				v = new Vehicle(c.getID());
				v.manage(SystemInterface.in);
				v.create();
				c.vehicles.add(v);
				return true;
			} else {
				return false;
			}
		} else {
			v = c.pickVehicle();
			return true;
		}		
	}
}
