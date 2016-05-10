package States;

import PersistentObjects.Carpool;
import PersistentObjects.ScheduleItem;

public class CancelledState implements CarpoolState
{
	private String status = "CANCELLED";

	public String toString()
	{
		return status;
	}

	@Override
	public void accept(Carpool carpool)
	{
		System.out.println("Carpool has been Cancelled!");
	}

	@Override
	public void decline(Carpool carpool)
	{
		System.out.println("Carpool status is already Cancelled! This carpool will be deleted now.");
		carpool.delete();
	}

	@Override
	public void track(Carpool carpool, ScheduleItem schedule)
	{
		//do nothing
	}

	@Override
	public void setStatus(Carpool carpool)
	{
		status = carpool.getRide().getSchedule().getCommuterName() + " is CANCELLED for driving " + carpool.getSchedule().toString();
	}
}
