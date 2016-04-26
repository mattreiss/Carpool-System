import java.util.ArrayList;


public abstract class Notification
{
	private ArrayList<NotificationObserver> observers = new ArrayList<NotificationObserver>();
	protected ArrayList<ConfirmedState> confirmedStates = new ArrayList<ConfirmedState>();
	
	
	public void add(NotificationObserver no)
	{
		observers.add(no);
	}
	
	public void notifyUpdate()
	{
		for (NotificationObserver o: observers)
		{
			o.update(this);
		}
	}
}
