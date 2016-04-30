package Default;
import PersistentObjects.Carpool;
import System.SystemPrompt;

public class SchedSchemeRider extends SchedSchemeTemplate {

	@Override
	void matchScheduleStep() {
		matches = SystemPrompt.dba.matchSchedules
				(selected,SystemPrompt.commuter.getID(), true);
	}
	
	@Override
	void carpoolStep() {
		carpool =  new Carpool(matched.getRide(),selected,selected.getTime(), 0, 1);
		SystemPrompt.dba.createCarpool(carpool);
	}
}
