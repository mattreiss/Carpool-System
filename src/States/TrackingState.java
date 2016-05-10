package States;

import PersistentObjects.Carpool;
import PersistentObjects.PersistentObject;
import PersistentObjects.ScheduleItem;

public class TrackingState implements CarpoolState
{
	private String status = "TRACKING";
	private String originialstatus = null;
	
	public String toString()
	{
		if (originialstatus == null) return status;
		return originialstatus + "\n" + status;
	}

	@Override
	public void accept(Carpool carpool)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decline(Carpool carpool)
	{
		//todo: apply penalty for cancelling late
		
		carpool.setDriverStatus(0);
		carpool.setPassengerStatus(0);
		carpool.setState(new CancelledState());
		carpool.state.setStatus(carpool);
		PersistentObject.alert.setCarpool(carpool);
		PersistentObject.alert.setAlert("\nNEW ALERT: A Carpool was Cancelled!");
		carpool.getRide().setVacancy(carpool.getRide().getVacancy()+1);
		carpool.getRide().update();
		carpool.update();
	}

	@Override
	public void track(Carpool carpool, ScheduleItem schedule)
	{
		carpool.setPassengerStatus(schedule.getID());
		setStatus(carpool);
		carpool.update();
	}

	@Override
	public void setStatus(Carpool carpool)
	{
		if (originialstatus == null) setOriginalStatus(carpool);;
		ScheduleItem schedule = new ScheduleItem();
		schedule.setID(carpool.getPassengerStatus());
		schedule.retrieve();
		status = "-------->CURRENTLY " + carpool.getRide().getSchedule().getCommuterName() + " is PICKING UP "  + schedule.getCommuterName() + " at " + schedule.getFrom().toString();
		
	}
	
	public void setOriginalStatus(Carpool carpool)
	{
		originialstatus = carpool.getRide().getSchedule().getCommuterName() + " is CONFIRMED to drive " + carpool.getSchedule().toString();
	}

}
