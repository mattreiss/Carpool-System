package Default;
import java.util.ArrayList;

public class Confirmation extends Notification
{
	public Confirmation()
	{
	}
	
	public void setConfirmedStates(ArrayList<ConfirmedState> s)
	{
		confirmedStates = s;
		notifyUpdate();
	}
}
