package Scheduling;
import PersistentObjects.Carpool;
import PersistentObjects.Commuter;
import PersistentObjects.PersistentObject;
import States.PendingState;
import System.SystemInterface;

public class SchedSchemeRider extends SchedSchemeTemplate {

	@Override
	void getMatchingSched(Commuter c) {
		matches = c.match(selected, "AND ride_id is not null AND vacancy > 0 AND commit_time ");
	}
	
	@Override
	void saveCarpool() {
		System.out.println("What time would you like to meet the driver?");
		String meetTime = SystemInterface.in.nextLine();
		
		carpool =  new Carpool(matched.getRide(),selected,meetTime, 0, 1);
		carpool.setState(new PendingState());
		carpool.state.setStatus(carpool);
		carpool.create();
		matched.getRide().update();


		//alert commuters matched to carpool
		PersistentObject.alert.setCarpool(carpool);
		PersistentObject.alert.setAlert("\nNew Carpool Request!");
	}
}
