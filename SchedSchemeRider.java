public class SchedSchemeRider extends SchedSchemeTemplate {

	@Override
	void matchScheduleStep() {
		matches = UI.dba.matchSchedules
				(selected,UI.commuter.getID(), true);
	}
	
	@Override
	void carpoolStep() {
		carpool =  new Carpool(matched.getRide(),selected,selected.getTime(), 0, 1);
		UI.dba.createCarpool(carpool);
	}
}
