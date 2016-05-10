package States;

import PersistentObjects.Carpool;
import PersistentObjects.PersistentObject;
import PersistentObjects.ScheduleItem;

public class PendingState implements CarpoolState
{
	private String status = "PENDING";
	
	public String toString()
	{
		return status;
	}

	@Override
	public void accept(Carpool carpool)
	{
		if (carpool.getRide().getVacancy() > 0)
		{
			carpool.setDriverStatus(1);
			carpool.setPassengerStatus(1);
			carpool.setState(new ConfirmedState());
			carpool.state.setStatus(carpool);
			

			PersistentObject.alert.setCarpool(carpool);
			PersistentObject.alert.setAlert("\nNEW ALERT: A Carpool Request was Accepted!");
	
			carpool.getRide().setVacancy(carpool.getRide().getVacancy()-1);
			carpool.getRide().update();
			carpool.update();
		} else
		{
			System.out.println("Car is currently at Capacity");
		}
	}

	@Override
	public void decline(Carpool carpool)
	{
		carpool.setDriverStatus(0);
		carpool.setPassengerStatus(0);
		carpool.setState(new CancelledState());
		carpool.state.setStatus(carpool);
		PersistentObject.alert.setCarpool(carpool);
		PersistentObject.alert.setAlert("\nNEW ALERT: A Carpool Request was Declined!");
		carpool.update();
	}

	@Override
	public void track(Carpool carpool, ScheduleItem schedule)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(Carpool carpool)
	{
		status = carpool.getRide().getSchedule().getCommuterName() + " is PENDING to drive " + carpool.getSchedule().toString();
	}
}
