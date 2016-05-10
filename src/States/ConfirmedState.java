package States;

import PersistentObjects.Carpool;
import PersistentObjects.PersistentObject;
import PersistentObjects.ScheduleItem;

public class ConfirmedState implements CarpoolState
{
	private String status = "CONFIRMED";

	public String toString()
	{
		return status;
	}

	@Override
	public void accept(Carpool carpool)
	{
		System.out.println("Status is already Confirmed");
	}

	@Override
	public void decline(Carpool carpool)
	{	
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
		carpool.setState(new TrackingState());
		carpool.state.track(carpool, schedule);
	}

	@Override
	public void setStatus(Carpool carpool)
	{
		status = carpool.getRide().getSchedule().getCommuterName() + " is CONFIRMED to drive " + carpool.getSchedule().toString();
	}
}
