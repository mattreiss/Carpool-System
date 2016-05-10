package Scheduling;
import java.util.ArrayList;
import PersistentObjects.Commuter;
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
	public void assignCarpool(Commuter c) {
		if (chooseVehicle(c)) {
			if (selectSchedItem(c)) {
				createRide();
				getMatchingSched(c);
				if (chooseMatch())
					saveCarpool();
			}
		}		
	}
	
	boolean chooseVehicle(Commuter c) { return true; }
	
	boolean selectSchedItem(Commuter c) {
		selected = c.pickScheduleItem( 
					"AND ride_id is null AND carpool_ride_id is null",
					"Please select the schedule for the ride:");
		return selected != null;
	}
	
	void createRide() { /* do nothing */ }
	
	void getMatchingSched(Commuter c) {
		matches = c.match(selected, "AND ride_id is null and carpool_ride_id is null AND "
				+ "commit_time <= '" + selected.getTime() + "'");
	}
	
	boolean chooseMatch() {
		matched = selectMatch();
		return matched != null;
	}
	
	private ScheduleItem selectMatch() {
		System.out.println("\n\n*****Matching Schedules*****");
		if (matches.size() > 0)  {
			// list matches
			for (int i = 0; i < matches.size(); i++) {
				System.out.println("[" + i + "] " + matches.get(i).toString());
			}
			
			// request commuters for a carpool
			System.out.println("Please select matching schedule:");
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			return matches.get(index);
		} else {
			System.out.println("<No matches found>");
			return null;
		}
	}

	abstract void saveCarpool();
}
