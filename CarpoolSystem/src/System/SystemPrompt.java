package System;
import java.util.ArrayList;
import java.util.Scanner;

import Default.Confirmation;
import Default.Notification;
import Default.NotificationObserver;
import Default.SchedContext;
import Default.SchedScheme;
import Default.SchedSchemeDriver;
import Default.SchedSchemeRider;
import Default.SchedSchemeSystem;
import PersistentObjects.Commuter;
import PersistentObjects.Location;
import PersistentObjects.ScheduleItem;
import PersistentObjects.Vehicle;


/**
 * A class that interacts with users from a command line interface.
 */
public class SystemPrompt implements NotificationObserver
{
	protected final static Scanner in = new Scanner(System.in);
	protected SchedContext schedContext = new SchedContext();
	
	private Notification notification = new Confirmation();
	
	public SystemPrompt()
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
			//seeRewards();
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
/*
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
*/

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

	

	@Override
	public void update(Notification n)
	{
		notification = n;
	}
}
