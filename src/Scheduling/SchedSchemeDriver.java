package Scheduling;

import PersistentObjects.Commuter;
import PersistentObjects.PersistentObject;
import States.PendingState;
import System.SystemInterface;
import PersistentObjects.Carpool;

/**
 * Handles implementation for driver chosen scheduling
 * @author LanceB
 *
 */
public class SchedSchemeDriver extends SchedSchemeTemplate {
	
	@Override
	boolean selectSchedItem(Commuter c) {
		selected = c.pickScheduleItem( 
					"AND ride_id is not null and vacancy > 0",
					"Choose ride to find passengers for:");
		return selected != null;
	}
	
	@Override
	void saveCarpool() {
		System.out.println("What time would you like to meet the passenger?");
		String meetTime = SystemInterface.in.nextLine();
		carpool =  new Carpool(selected.getRide(), matched, meetTime, 1, 0);
		carpool.setState(new PendingState());
		carpool.state.setStatus(carpool);
		carpool.create();
		selected.getRide().update();
		

		//alert commuters matched to carpool
		PersistentObject.alert.setCarpool(carpool);
		PersistentObject.alert.setAlert("\nNew Carpool Request!");
	}
	

}
