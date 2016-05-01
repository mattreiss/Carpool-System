package Default;
import java.util.ArrayList;

import PersistentObjects.Carpool;
import PersistentObjects.ScheduleItem;
import System.SystemInterface;
/**
 * SchedSchemeTemplate
 * @author LanceB
 *
 */
public abstract class SchedSchemeTemplate implements SchedScheme {
	
	ScheduleItem selected = null;
	ScheduleItem matched = null;
	ArrayList<ScheduleItem> matches = null;
	ArrayList<ScheduleItem> schedule = null;
	Carpool carpool = null;
	
	public SchedSchemeTemplate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void assignCarpool() {
		if (vehicleStep()) {
			getSchedule();
			if (scheduleStep()) {
				rideStep();
				matchScheduleStep();
				if (assignStep())
					carpoolStep();
			}
		}		
	}
	
	boolean vehicleStep() { return true; }
	
	void getSchedule() {
		schedule = SystemInterface.viewSchedule("carpool_ride_id is null");
	}
	
	/**
	 * Select specific schedule item from commuter schedule
	 * @return
	 */
	boolean scheduleStep() {		
		if (schedule.size() > 0) {
			System.out.println("Please select the schedule for the ride:");
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			selected = schedule.get(index);
			return true;		
		} else
			System.out.println("No schedules found.");
			return false;		
	}
	
	void rideStep() { /* do nothing */ }
	
	abstract void matchScheduleStep();
	
	boolean assignStep() {
		matched = selectMatch(matches);
		return matched != null;
	}
	
	protected ScheduleItem selectMatch(ArrayList<ScheduleItem> schedule) {
		if (schedule.size() > 0) {
			// list matches
			System.out.println("\n\n*****Matching Schedules*****");
			for (int i = 0; i < schedule.size(); i++) {
				System.out.println("[" + i + "] " + schedule.get(i).toString());
			}
			
			// request commuters for a carpool
			System.out.println("Please select matching schedule:");
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			return schedule.get(index);
		} else {
			System.out.println("No matches found.");
			return null;
		}
	}

	abstract void carpoolStep();
}
