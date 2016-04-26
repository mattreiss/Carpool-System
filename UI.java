import java.util.ArrayList;
import java.util.Scanner;


/**
 * A class that interacts with users from a command line interface.
 */
public class UI implements NotificationObserver
{
	protected final static Scanner in = new Scanner(System.in);
	protected SchedContext schedContext = new SchedContext();
	
	private Notification notification = new Confirmation();
	
	public UI()
	{
		
	}
	

	/**
	 * Displays a menu of options for authenticated users.
	 */
	public String displayMenu()
	{	
		System.out.println("\n\n*****Main Menu*****");
		System.out.println("[1] Account");
		System.out.println("[2] Schedules");
		System.out.println("[3] Carpools");
		System.out.println("[4] Notifications (" + notification.confirmedStates.size() + ")");
		System.out.println("[0] Log out");

		return in.nextLine();
	}

	public void manageCarpool()
	{
		System.out.println("[1] Request a ride");
		System.out.println("[2] Create a ride");
		System.out.println("[3] Find passengers for my ride");
		System.out.println("[4] View my rides");
		System.out.println("[5] Cancel a ride");
		System.out.println("[6] See rewards");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			scheduleRide(new SchedSchemeRider());
			break;
		case "2":
			scheduleRide(new SchedSchemeSystem());
			break;
		case "3":
			scheduleRide(new SchedSchemeDriver());
			break;
		case "4":
			//viewRides(null);
			break;
		case "5":
			cancelRide();
			break;
		case "6":
			seeRewards();
			break;
		case "0":
		default:
			System.out.println("Cancelling...");
		}
	}

	private void scheduleRide(SchedScheme ss) {
		schedContext.setSchedulingScheme(ss);
		schedContext.assignToCarpool();		
	}

	private  void viewRides(ArrayList<ScheduleItem> s)
	{
		 //= dba.retrieveSchedule(commuter, "ride_id is not null");
		System.out.println("\n\n*****My Rides*****");
		for (int i = 0; i < s.size(); i++)
		{
			System.out.println("[" + i + "] " + s.get(i).toString());
		}
		
		System.out.println();
		System.out.println("Press <enter> to continue..");
		in.nextLine();

	}

	private  void cancelRide()
	{
		// create new tuple in the ride table
		// match ride schedule with commuter schedules
		// list matches
		// request commuters for ride
	}

	public void seeRewards()
	{
		// dba.showRewards(commuter);
		Bonus mileRew;
		System.out.println("[1] Show Rewards By Number of Passengers");
		System.out.println("[2] Show Rewards By Number of Miles");
		String res = in.nextLine();
		switch (res)
		{
		case "1":
			//dba.showRewards(commuter);
			break;
		case "2":
			System.out.println("Enter number of miles you have traveled: ");
			int mile = Integer.parseInt(in.nextLine());

			mileRew = new RewardByMile(mile, new RewardsByMile());
			mileRew.give();
		default:
			System.out.println("Invalid choice");
		}
	}

	public ArrayList<ScheduleItem> manageSchedule(ArrayList<ScheduleItem> schedule)
	{
		System.out.println("[1] Add to Schedule");
		System.out.println("[2] View Schedule");
		System.out.println("[3] Edit Schedule");
		System.out.println("[4] Delete Schedule");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			schedule = addSchedule(schedule);
			break;
		case "2":
			viewSchedule(schedule);
			break;
		case "3":
			schedule = editSchedule(schedule);
			break;
		case "4":
			if (deleteSchedule()) schedule = null;
			break;
		case "0":
		default:
			System.out.println("Cancelling...");
		}
		return schedule;
	}

	public ArrayList<ScheduleItem> addSchedule(ArrayList<ScheduleItem> schedule)
	{
		System.out.println("New Schedule:");
		System.out.print("  Start Date ('yyyy-mm-dd'): ");
		String startDt = in.nextLine();
		System.out.print("  End Date ('yyyy-mm-dd'): ");
		String endDt = in.nextLine();
		System.out.print("  Day of week -");
		System.out.print("  [1]Sun [2]Mon [3]Tue [4]Wed [5]Thu [6]Fri [7]Sat: ");
		int dow = Integer.parseInt(in.nextLine());
		System.out.print("  Arrival time at school (HH:MM): ");
		String arriveTime = in.nextLine();
		System.out.print("  Leave time from school (HH:MM): ");
		String leaveTime = in.nextLine();

		ScheduleItem sTo = new ScheduleItem(startDt, endDt, dow, arriveTime,
				null,
				new Location("1 Washington Square", 95112));

		ScheduleItem sFrom = new ScheduleItem(startDt, endDt, dow, leaveTime,
				new Location("1 Washington Square", 95112),
				null);

		schedule.add(sTo);
		schedule.add(sFrom);
		
		return schedule;
	}

	public static void viewSchedule(ArrayList<ScheduleItem> schedule)
	{
		System.out.println("\n\n*****My Schedule*****");
		for (int i = 0; i < schedule.size(); i++)
		{
			System.out.println("[" + i + "] " + schedule.get(i).toString());
		}
	}
	
	public ArrayList<ScheduleItem> editSchedule(ArrayList<ScheduleItem> schedule)
	{
		viewSchedule(schedule);
		System.out.println("Please enter the schedule to edit:");
		String next = in.nextLine();
		for (int i = 0; i < schedule.size(); i++)
		{
			if (next.contains(""+i))
			{
				schedule.set(i, null);
				break;
			}
		}
		return addSchedule(schedule);
	}

	public boolean deleteSchedule()
	{
		System.out.println("Are you sure you want to delete your schedule? [y]es or [n]o:");
		String confirm = in.nextLine();
		if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
		{
			System.out.println("Deleting Account...");
			return true;
		}
		return false;
	}

	public Commuter manageCommuter(Commuter commuter)
	{
		System.out.println("[1] View Account");
		System.out.println("[2] Edit Account");
		System.out.println("[3] Delete Account");
		System.out.println("[0] Cancel");

		String res = in.nextLine();
		switch (res)
		{
		case "1":
			return viewCommuter(commuter);
		case "2":
			return editCommuter(commuter);
		case "3":
			if (deleteCommuter()) return null;
			break;
		case "0":
		default:
			System.out.println("Cancelling...");
		}
		return commuter;
	}

	public Commuter viewCommuter(Commuter commuter)
	{
		// print out the information
		System.out.println("\n\n*****Account Information*****");
		System.out.println("name: " + commuter.getFirstName() + " "
				+ commuter.getLastName());
		System.out.println("email: " + commuter.getEmail());
		return commuter;
	}

	public Commuter editCommuter(Commuter commuter)
	{
		boolean done = false;
		while (!done)
		{
			System.out.println("\nSelect what to edit:");
			System.out.println("[1] first name");
			System.out.println("[2] last name");
			System.out.println("[3] email");
			System.out.println("[4] password");
			System.out.println("[0] done");
			String choice = in.nextLine();
			switch (choice)
			{
			case "1":
				System.out.println("Please enter your first name:");
				commuter.setFirstName(in.nextLine());
				break;
			case "2":
				System.out.println("Please enter your last name:");
				commuter.setLastName(in.nextLine());
				break;
			case "3":
				System.out.println("Please enter an email:");
				commuter.setEmail(in.nextLine());
				break;
			case "4":
				System.out.println("Please enter a password:");
				commuter.setPassword(in.nextLine());
				break;
			case "0":
				done = true;
				break;
			}
		}
		return commuter;
	}

	public boolean deleteCommuter()
	{
		System.out.println("Are you sure you want to delete your account? [y]es or [n]o:");
		String confirm = in.nextLine();
		if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
		{
			System.out.println("Deleting Account...");
			return true;
		}
		return false;
	}

	public static void viewVehicles(ArrayList<Vehicle> vehicles)
	{
		//ArrayList<Vehicle> vehicles = dba.retriveVehicles(commuter.getID());
		if (vehicles.isEmpty())
		{
			System.out.println(
					"No vehicles found. Do you have a vehicle? [y]es or [n]o:");
			String n = in.nextLine();
			if (n.equalsIgnoreCase("y") || n.contains("y"))
			{
				addVehicle();
			} 
		} else
		{
			System.out.println("\n\n*****My Vehicles*****");
			for (int i = 0; i < vehicles.size(); i++)
			{
				System.out.println("[" + i + "]" + vehicles.get(i).toString());
			}
		}
	}

	private static Vehicle addVehicle()
	{
		System.out.println("Enter the number of seats: ");
		int seats = Integer.parseInt(in.nextLine());
		System.out.println("Enter the make: ");
		String make = in.nextLine();
		System.out.println("Enter the model: ");
		String model = in.nextLine();
		System.out.println("Enter the year: ");
		int year = Integer.parseInt(in.nextLine());
		System.out.println("Enter the color: ");
		String color = in.nextLine();

		Vehicle vehicle = new Vehicle(seats, make, model, year, color);
		System.out.println("    vehicle info: ");
		System.out.println("        seats: " + vehicle.getSeats());
		System.out.println("        make: " + vehicle.getMake());
		System.out.println("        model: " + vehicle.getModel());
		System.out.println("        year: " + vehicle.getYear());

		System.out.println("Is this correct? [y]es or [n]o:");
		String confirm = in.nextLine();
		if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
			return vehicle;
		
		return null;
	}

	private  void removeVehicle()
	{
		// TODO Auto-generated method stub

	}

	private  void editVehicle()
	{
		// TODO Auto-generated method stub

	}

	public Location addLocation()
	{
		System.out.println("Please enter an address: ");
		String address = in.nextLine();
		System.out.println("Please enter a zip: ");
		int zip = Integer.parseInt(in.nextLine());
		
		return new Location(address, zip);
	}

	/**
	 * Authenticates a user with a new or existing account.
	 * 
	 * @return true if authenticated or else false.
	 */
	public Commuter authenticate()
	{
		Commuter commuter = null;
		System.out.println("\n\n*****WELCOME*****");
		System.out.println("[1] Log in");
		System.out.println("[2] Sign up");
		System.out.println("[0] Exit");
		String next = in.nextLine();
		switch (next)
		{
		case "1":
			commuter = logIn();
			break;
		case "2":
			commuter = signUp();
			break;
		case "0":
			exit();
			break;
		}
		return commuter;
	}

	
	/**
	 * Returns a new commuter object with provided information.
	 * 
	 * @return a Commuter if sign up is confirmed or else null.
	 */
	public Commuter signUp()
	{
		Commuter commuter;
		System.out.println("Please enter your first name: ");
		String first = in.nextLine();
		System.out.println("Please enter your last name: ");
		String last = in.nextLine();
		System.out.println("Please enter an email: ");
		String email = in.nextLine();
		System.out.println("Please enter a password: ");
		String password = in.nextLine();

		System.out.println("You entered the following:");
		System.out.println("    first: " + first);
		System.out.println("    last: " + last);
		System.out.println("    email: " + email);
		System.out.println("    password: " + password);
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
	public Commuter logIn()
	{
		System.out.println("Please enter your email: ");
		String email = in.nextLine();
		System.out.println("Please enter your password: ");
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
	public boolean tryAgain(String operation)
	{
		System.out.println(
				operation + " unsuccessful. Try again? [y]es or [n]o:");
		String confirm = in.nextLine();
		return (confirm.equalsIgnoreCase("y") || confirm.contains("y"));
	}

	public void exit()
	{
		System.out.println("Good bye!");
		System.exit(0);
	}
	

	@Override
	public void update(Notification n)
	{
		notification = n;
	}
}
