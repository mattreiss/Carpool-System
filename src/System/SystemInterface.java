package System;
import java.util.Scanner;

import Default.NotificationObserver;
import PersistentObjects.Commuter;


/**
 * A class that interacts with users from a command line interface.
 */
public class SystemInterface implements NotificationObserver
{
	private static Commuter commuter = null;
	
	
	public SystemInterface()
	{
		
	}

	
	public void startMainLoop()
	{
		Scanner in = new Scanner(System.in);
		boolean shouldExit = false;
		while (!shouldExit)
		{
			while (!authenticated(in))
			{
				if (!tryAgain("authentication", in))
				{
					System.out.println("Good bye!");
					shouldExit = true;
					break;
				}
			}
			if (shouldExit) break;
			while (runCarpoolSystem(in));
		}
		in.close();
	}
	/**
	 * Runs the Carpool System from the main menu
	 * @return true unless the commuter is null
	 */
	public static boolean runCarpoolSystem(Scanner in)
	{
		System.out.println("\n\n*****Welcome "+commuter.getFirstName()+"*****");
		System.out.println("[1] Manage Account");
		System.out.println("[2] Manage Schedule");
		System.out.println("[3] Manage Rides");
		//System.out.println("[4] Notifications (" + notification.confirmedStates.size() + ")");
		//System.out.println("[5] Rewards");
		//System.out.println("[6] Carpool Status");
		//System.out.println("[6] Manage Parking");
		System.out.println("[0] Log out");
		
		switch (in.nextLine())
		{
		case "0": //logout
			//notification.reset();
			commuter = null;
			break;
		case "1"://Account
			commuter.manage(in);// the commuter does all the work to modify data
			break;
		case "2"://Schedule
			commuter.manageSchedule(in); // modifies all the schedule data
			break;
		case "3"://Rides
			break;
		case "4"://Notifications
			break;
		}
		return commuter != null;
	}
	

	/**
	 * Authenticates a commuter
	 * @return true if logged in successfully, or else false
	 */
	public static boolean authenticated(Scanner in)
	{
		if ( (commuter = authenticate(in)) != null )
		{	
			if (commuter.getFirstName() == null) // user is logging in
				commuter.retrieve();
			else // user is creating an account
				commuter.create();
			return commuter.getID() >= 0;
		}
		return false;
	}
	
	
	/**
	 * Authenticates a user with a new or existing account.
	 * 
	 * @return true if authenticated or else false.
	 */
	public static Commuter authenticate(Scanner in)
	{
		Commuter commuter = null;
		System.out.println("\n\n*****Authenticate*****");
		System.out.println("[1] Log in");
		System.out.println("[2] Sign up");
		System.out.println("[0] Exit");
		String next = in.nextLine();
		switch (next)
		{
		case "1":
			commuter = logIn(in);
			break;
		case "2":
			commuter = signUp(in);
			break;
		case "0":
			break;
		}
		return commuter;
	}

	
	/**
	 * Returns a new commuter object with provided information.
	 * 
	 * @return a Commuter if sign up is confirmed or else null.
	 */
	public static Commuter signUp(Scanner in)
	{
		System.out.println("First name: ");
		String first = in.nextLine();
		System.out.println("Last name: ");
		String last = in.nextLine();
		System.out.println("Email: ");
		String email = in.nextLine();
		System.out.println("Password: ");
		String password = in.nextLine();

		System.out.println("You entered the following:");
		System.out.println("    First name: " + first);
		System.out.println("    Last name: " + last);
		System.out.println("    Email: " + email);
		System.out.println("    Password: " + password);
		System.out.println("Is this correct? [y]es or [n]o:");

		String confirm = in.nextLine();
		boolean result = (confirm.equalsIgnoreCase("y") || confirm.contains("y"));
		if (result) // if info is correct, create commuter account
		{
			return new Commuter(-1, first, last, email, password);
		}

		return null;
	}

	/**
	 * Log in a user with provided credentials.
	 * 
	 * @return true if log in is correct or else false.
	 */
	public static Commuter logIn(Scanner in)
	{
		System.out.println("Email: ");
		String email = in.nextLine();
		System.out.println("Password: ");
		String password = in.nextLine();

		return new Commuter(-1, null, null, email, password);
	}

	/**
	 * Asks the user if they want to try an operation again.
	 * 
	 * @param operation
	 *            the operation to retry.
	 * @return true if they wish to try again or else false.
	 */
	public static boolean tryAgain(String operation, Scanner in)
	{
		System.out.println(operation + " unsuccessful. Try again? [y]es or [n]o:");
		String confirm = in.nextLine();
		return (confirm.equalsIgnoreCase("y") || confirm.contains("y"));
	}


	@Override
	public void alert(Alert a) {
		// TODO Auto-generated method stub
		System.out.println("\n\n"+a.getAlert());
	}
	
}
