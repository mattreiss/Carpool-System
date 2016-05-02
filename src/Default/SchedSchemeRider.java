package Default;
import PersistentObjects.Carpool;
import System.SystemInterface;

public class SchedSchemeRider extends SchedSchemeTemplate {

	@Override
	void matchScheduleStep() {
		matches = SystemInterface.dba.matchSchedules
				(selected,SystemInterface.commuter.getID(), true);
	}
	
	@Override
	void carpoolStep() {
		carpool =  new Carpool(matched.getRide(),selected,selected.getTime(), 0, 1);
		SystemInterface.dba.createCarpool(carpool);
	}
}
