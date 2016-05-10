package States;

import PersistentObjects.Carpool;
import PersistentObjects.ScheduleItem;

public interface CarpoolState
{
	public void accept(Carpool carpool);
	public void decline(Carpool carpool);
	void track(Carpool carpool, ScheduleItem schedule);
	void setStatus(Carpool carpool);
}
