import java.util.ArrayList;

public class CarpoolSystem 
{

	private static Commuter commuter = null;
	private static UI ui = new UI();
	private static DBA dba = DBA.getDBA();
	
	/**
	 * Main method to begin the program interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Confirmation c = new Confirmation();
		
		c.add(ui);
		
		while (true)
		{
			while (!authenticated())
			{
				if (!ui.tryAgain("authentication")) ui.exit();
			}
			initializeCommuter();
			do c.setConfirmedStates(dba.retrieveConfirmedStates(commuter.getID()));
			while (runCarpoolSystem());
		}
	}
	
	private static void initializeCommuter() 
	{
		commuter.setSchedule(dba.retrieveSchedule(commuter.getID(), ""));
	}

	/**
	 * Authenticates a commuter
	 * @return true if logged in successfully, or else false
	 */
	public static boolean authenticated()
	{
		if ( (commuter = ui.authenticate()) != null )
		{	
			if (commuter.getFirstName() == null) // user is logging in
			{
				commuter = dba.retrieveCommuter(commuter.getEmail(), commuter.getPassword());
			} else // user is creating an account
			{
				commuter.setID(dba.createCommuter(commuter));
			}
			return commuter.getID() != -1;
		}
		return false;
	}
	
	/**
	 * Runs the Carpool System from the main menu
	 * @return true unless the commuter is null
	 */
	public static boolean runCarpoolSystem()
	{
		switch (ui.displayMenu())
		{
		case "0": //logout
			//notification.reset();
			commuter = null;
		case "1"://Account
			Commuter c = ui.manageCommuter(commuter);
			if (c == null)
			{
				dba.deleteCommuter(commuter);
				commuter = null;
			} else
			{
				commuter = c;
				dba.updateCommuter(commuter);
			}
			break;
		case "2"://Schedules
			ArrayList<ScheduleItem> schedule = ui.manageSchedule(commuter.getSchedule());
			
			//delete all schedule items
			for (ScheduleItem s: commuter.getSchedule())
			{
				dba.deleteScheduleItem(s);
			}
			commuter.setSchedule(new ArrayList<ScheduleItem>());
			if (schedule != null)
			{
				//add updated schedule items
				for (int i = 0; i < schedule.size(); i ++)
				{
					if (schedule.get(i) != null)
					{
						dba.createScheduleItem(commuter.getID(), schedule.get(i));
						commuter.addSchedule(schedule.get(i));
					}
				}
			}
			break;
		case "3"://Carpools
			ui.manageCarpool();
			break;
		case "4"://Notifications
			//System.out.println(notification.toString());
			break;
		}
		return commuter != null;
	}
}
