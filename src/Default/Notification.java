package Default;
import java.util.ArrayList;

import System.Alert;


public abstract class Notification
{
	private ArrayList<NotificationObserver> observers = new ArrayList<NotificationObserver>();
	
	public void add(NotificationObserver no)
	{
		observers.add(no);
	}
	
	public void alert(Alert a)
	{
		for (NotificationObserver o: observers)
		{
			o.alert(a);
		}
	}
}
