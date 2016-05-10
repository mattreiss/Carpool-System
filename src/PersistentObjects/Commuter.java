package PersistentObjects;
import Bonus.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Scheduling.SchedContext;
import States.CancelledState;
import System.Alert;
import System.SystemInterface;

/**
 * A class that represents a commuter object.
 */
public class Commuter implements PersistentObject
{
	private int id;
	private String firstName;
	private String lastName;
	private String email; 
	private String password; 
	private ArrayList<ScheduleItem> schedules = new ArrayList<>();
	public ArrayList<Vehicle> vehicles = new ArrayList<>();
	private ArrayList<Ride> rides = new ArrayList<>(); // all of the rides where the commuter is the driver
	private ArrayList<Carpool> carpools = new ArrayList<>(); // all of the carpools where the commuter is a passenger
	private ArrayList<Carpool> carpoolRequests = new ArrayList<>(); // all of the carpools where the commuter is a passenger
	private Location location;
	private SchedContext schedContext = new SchedContext();
	private int passengerCount;
	
	public Commuter(int id, String firstName, String lastName, String email, String password) 
	{
		update(id,firstName,lastName,email,password);
	}
	
	public void update(int id, String firstName, String lastName, String email, String password) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public int 		getID() 		{return id;}
	public String 	getFirstName() 	{return firstName;}
	public String 	getLastName() 	{return lastName;}
	public String 	getEmail() 		{return email;}
	public String 	getPassword() 	{return password;}
	public ArrayList<ScheduleItem> getSchedule()	{return schedules;}
	public ArrayList<Vehicle> getVehicle() { return vehicles; }
	
	public void setID(int id) { this.id = id; }
	public void setFirstName(String firstName)  { this.firstName = firstName; }
	public void setLastName(String lastName)  { this.lastName = lastName; }
	public void setEmail(String email)  { this.email = email;  }
	public void setPassword(String password)  { this.password = password;  }
    public void setSchedule(ArrayList<ScheduleItem> s) { this.schedules = s; }
	public void setVehicle(ArrayList<Vehicle> vehicles) { this.vehicles = vehicles; }

	
    public String create()
    {
    	String table = "commuter (first_name, last_name, email, password)";
		String values = "'" + firstName + "', '" + lastName
				+ "', '" + email+ "', '" + password + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		id = db.create(sql);
		return sql;
    }

    public String retrieve()
    {
		String sql = "SELECT * FROM commuter WHERE email = '" + email + "'";
		parseResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
    }
    
    public String update()
    {	
		String sql = "UPDATE commuter SET email = '" + email + "', password = '" + password + "', first_name = '"
				+ firstName + "', last_name = '" + lastName + "' WHERE id = '" + id + "';";
		db.update(sql);
		return sql;
    }
    
    public String delete()
    {
		String sql = "DELETE FROM commuter WHERE id = '" + id + "'";
		db.update(sql);
		return sql;
    }
    
    public String retrieveSchedules()
    {
		String sql = "SELECT * FROM commuterschedule WHERE commuter_id  = '" + id + "'";
		schedules = parseSchedulesResultSet(db.retrieve(sql));
		db.closeConnection();
		return sql;
    }
    
	public ScheduleItem pickScheduleItem(String sqlCrit, String prompt) {
		String sql = "SELECT * FROM commuterschedule WHERE commuter_id  = " + id + " " + sqlCrit;
		ArrayList<ScheduleItem> s = parseSchedulesResultSet(db.retrieve(sql));
		db.closeConnection();
		
		System.out.println("\n\n*****My Schedule*****");
		if (s.size() == 0) {
			System.out.println("<No relevent records found>");
		} else {
			for (int i = 0; i < s.size(); i++) {
				System.out.println("[" + i + "] " + s.get(i).toString());
				}
			System.out.println(prompt);
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			return s.get(index);
		}
		return null;
	}
	
	public Vehicle pickVehicle() {
		System.out.println("\n\n*****My Vehicles*****");
		if (vehicles.size() == 0) {
			System.out.println("<No relevent records found>");
		} else {
			for (int i = 0; i < vehicles.size(); i++) {
				System.out.println("[" + i + "] " + vehicles.get(i).toString());
				}
			System.out.println("Choose vehicle for the ride:");
			int index = Integer.parseInt(SystemInterface.in.nextLine());
			return vehicles.get(index);
		}
		return null;
	}
	
	public ArrayList<ScheduleItem> match(ScheduleItem s, String sqlCrit) {
		String sql = s.match(sqlCrit);
		ArrayList<ScheduleItem> matching = parseSchedulesResultSet(db.retrieve(sql));
		db.closeConnection();
		return matching;
	}
	
	
    public ArrayList<ScheduleItem> parseSchedulesResultSet(ResultSet rs) {
    	ArrayList<ScheduleItem> newSched = new ArrayList<>();
		try	{
			while (rs.next()) {		
				rs.previous();
				ScheduleItem s = new ScheduleItem(id);
				s.parseResultSet(rs);
				newSched.add(s);
			}
		} catch (SQLException eX) {
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
		return newSched;
    }
	

    public void parseResultSet(ResultSet rs)
    {
    	//try to set variables from result set
    	try
		{
			if (rs != null && rs.next())
			{
				if (password.equals(rs.getString("password")))
				{
					update( rs.getInt("id"),
						    rs.getString("first_name"),
							rs.getString("last_name"), 
							rs.getString("email"),
							rs.getString("password"));
				} else
					System.out.println("Invalid Password.");
			} else
				System.out.println("User not registered.");
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }
    
    public void manage(Scanner in)
    {	
    	System.out.println("[1] View Account");
		System.out.println("[2] Edit Account");
		System.out.println("[3] Delete Account");
		System.out.println("[0] Cancel");
		
		String res = in.nextLine();
		switch (res)
		{
		case "1"://View Account
			System.out.println("\n\n*****Account Information*****");
			System.out.println("Name: " + firstName + " " + lastName);
			System.out.println("Email: " + email);
			System.out.println("Password: " + password);
			break;
		case "2"://Edit Account
			boolean done = false;
			while (!done)
			{
				System.out.println("\nEdit Account:");
				System.out.println("[1] First name : " + firstName);
				System.out.println("[2] Last name : " + lastName);
				System.out.println("[3] Email : " + email);
				System.out.println("[4] Password : " + password);
				System.out.println("[0] Done");
				String choice = in.nextLine();
				switch (choice)
				{
				case "1":
					System.out.println("First name:");
					setFirstName(in.nextLine());
					break;
				case "2":
					System.out.println("Last name:");
					setLastName(in.nextLine());
					break;
				case "3":
					System.out.println("Email:");
					setEmail(in.nextLine());
					break;
				case "4":
					System.out.println("Password:");
					setPassword(in.nextLine());
					break;
				case "0":
					done = true;
					break;
				}
			}
			update();
			break;
		case "3"://Delete Account
			System.out.println("Are you sure you want to delete your account? [y]es or [n]o:");
			String confirm = in.nextLine();
			if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
			{
				System.out.println("Deleting Account...");
				delete();
			}
			break;
		case "0"://Cancel
		default:
			System.out.println("Cancelling...");
		}
    }

    public void manageSchedules(Scanner in)
    {
    	retrieveSchedules();
		System.out.println("[1] Add Schedule");
		System.out.println("[2] View Schedule");
		System.out.println("[3] Edit Schedule");
		System.out.println("[4] Delete Schedule");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1": // Add Schedule
			ScheduleItem s = new ScheduleItem(id);
			s.manage(in);
			schedules.add(s);
		case "2": // View Schedule
			System.out.println("\n\n*****My Schedule*****");
			for (int i = 0; i < schedules.size(); i++)
			{
				System.out.println("[" + (i+1) + "] " + schedules.get(i).toString());
			}
			break;
		case "3": // Edit Schedule
			boolean done = false;
			while (!done)
			{	
				System.out.println("\nEdit Schedule:");
				
				for (int i = 0; i < schedules.size(); i++)
				{
					System.out.println("[" + (i+1) + "] " + schedules.get(i).toString());
				}
				System.out.println("[0] Done");
				String n = in.nextLine();
				if (n.contains("0")) { done = true; break; }
				for (int i = 0; i < schedules.size(); i++)
				{
					if ( n.contains(""+ (i+1)) )
					{
						schedules.get(i).manage(in);
						break;
					}
				}
			}
			break;
		case "4": // Delete Schedule
			done = false;
			while (!done)
			{	
				System.out.println("\nDelete Schedule:");
				for (int i = 0; i < schedules.size(); i++)
				{
					System.out.println("[" + (i+1) + "] " + schedules.get(i).toString());
				}
				System.out.println("[0] Done");
				String n = in.nextLine();
				if (n.contains("0")) { done = true; break; }
				for (int i = 0; i < schedules.size(); i++)
				{
					if ( n.contains(""+(i+1)) )
					{
						System.out.println("Are you sure you want to delete this schedule? [y]es or [n]o");
						String confirm = in.nextLine();
						if (confirm.equalsIgnoreCase("y") || confirm.contains("y"))
						{
							System.out.println("Deleting Schedule...");
							schedules.remove(i).delete();
						}
						break;
					}
				}
				
			}
			break;
		case "0": // Cancel
		default:
			System.out.println("Cancelling...");
		}
    }
 
	private void retrieveVehicles() {
		String sql = "SELECT * FROM vehicle WHERE driver_commuter_id = " + id;
		parseVehicleResultSet(db.retrieve(sql));
		db.closeConnection();
	}
	
	public void parseVehicleResultSet(ResultSet rs) {
    	vehicles = new ArrayList<Vehicle>();
		try {
			while (rs.next()) 
			{
				rs.previous();
				Vehicle v = new Vehicle(id);
				v.parseResultSet(rs);
				vehicles.add(v);
			}
		} catch (SQLException eX) {
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }
	

	private void retrieveRides() {
		String sql = "SELECT ride.* FROM schedule INNER JOIN ride ON schedule.id=ride.driver_schedule_id "
				   + "WHERE commuter_id = " + id;
		parseRidesResultSet(db.retrieve(sql));
		db.closeConnection();
	}
	
    public void parseRidesResultSet(ResultSet rs) {
    	rides = new ArrayList<Ride>();
		try {
			while (rs.next()) 
			{
				rs.previous();
				Ride r = new Ride(id);
				r.parseResultSet(rs);
				rides.add(r);
			}
		} catch (SQLException eX) {
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }  
    
    /**
     * Manages the rides for a commuter
     * @param in
     */
    public void manageRides(Scanner in)
    {
    	retrieveVehicles();
    	retrieveRides();
    	retrieveSchedules();
		System.out.println("\n\n*****Manage Rides Menu*****");
		System.out.println("[1] Create a ride as a driver"); //inputs a ride to db for the commuter
		System.out.println("[2] Request a driver"); //lists drivers from ride table, creates carpool, and sends alert to a driver 
		System.out.println("[3] Request a passenger"); //lists matching passengers for a ride, creates carpool, and sends alert to a passenger
		System.out.println("[4] View ride schedules"); //lists all ride/carpool schedules for commuter
		System.out.println("[5] Notify my passengers"); //lists all ride/carpool schedules for commuter
		System.out.println("[6] Cancel a ride"); //sets a commuter's ride/carpool state to cancelled
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1": // Become a driver
		case "2": // Request a driver
		case "3": // Request a passenger
			schedContext.assignToCarpool(this, Integer.parseInt(res1));
			break;
		case "4":  // View My rides
				retrieveRides();
				retrieveCarpools();
				if (carpools.isEmpty() && rides.isEmpty()) System.out.println("\n*****You have no rides*****");
				else System.out.println("\n*****Select a ride to view the carpool*****");
				if (!rides.isEmpty()) System.out.println("Rides as a Driver:");
				for (int i = 0; i < rides.size(); i++) {
					System.out.println("[d" + (i+1) + "] " + rides.get(i).toString());
				}
				if (!carpools.isEmpty()) System.out.println("Rides as a Passenger:");
				for (int i = 0; i < carpools.size(); i++) {
					System.out.println("[p" + (i+1) + "] " + carpools.get(i).toString());
				}
				System.out.println("[0] Done");
				String choice = in.nextLine();
				int index = - 1;
				if (!choice.equals("0")) 
				{
					try { index = Integer.parseInt(choice.substring(1));}
					catch (NumberFormatException e) { e.printStackTrace(); }
					if (choice.contains("d"))
					{
						try { index = rides.get(index-1).getID();}
						catch (IndexOutOfBoundsException e) { e.printStackTrace();}
					} else if (choice.contains("r"))
					{
						try { index = carpools.get(index-1).getRide().getID();}
						catch (IndexOutOfBoundsException e) {e.printStackTrace();}
					}
					if (index != -1)
					{
						ArrayList<Carpool> carpools = retrieveCarpools(index);
						for (Carpool c : carpools)
						{
							System.out.println(c.toString());
						}
					}
				}
			break;
		case "5": // Notify passengers
			retrieveRides();
			if (rides.isEmpty()) System.out.println("\n*****You have no rides*****");
			else System.out.println("\n*****Select a ride*****");
			if (!rides.isEmpty()) System.out.println("Rides as a Driver:");
			for (int i = 0; i < rides.size(); i++) {
				System.out.println("[" + (i+1) + "] " + rides.get(i).toString());
			}
			System.out.println("[0] Done");
			choice = in.nextLine();
			index = - 1;
			if (!choice.equals("0"))
			{
				try { index = Integer.parseInt(choice);}
				catch (NumberFormatException e) { e.printStackTrace(); }
				try { index = rides.get(index-1).getID();}
				catch (IndexOutOfBoundsException e) { e.printStackTrace();}
				if (index != -1)
				{
					ArrayList<Carpool> carpools = retrieveCarpools(index);
					if (!carpools.isEmpty())
					{
						System.out.println("Who do you want to notify to come out?");
						for (int i = 0; i < carpools.size(); i++)
						{
							ScheduleItem s = carpools.get(i).getSchedule();
							System.out.println("["+(i+1)+"] "+s.getCommuterName() + " at " + s.getFrom().toString());
						}
						index = -1;
						choice = in.nextLine();
						try { index = Integer.parseInt(choice);}
						catch (NumberFormatException e) { e.printStackTrace(); }
						if (index != -1 && index <= carpools.size())
						{
							ScheduleItem s = carpools.get(index-1).getSchedule();
							for (int i = 0; i < carpools.size(); i++)
							{
								Carpool c = carpools.get(i);
								c.state.track(c, s);
							}
						}
					}
				}
			}
			break;
		case "6":  // cancel My rides
				retrieveRides();
				retrieveCarpools();
				if (carpools.isEmpty() && rides.isEmpty()) System.out.println("\n*****You have no rides*****");
				else System.out.println("\n*****Select a ride to cancel*****");
				if (!rides.isEmpty()) System.out.println("Rides as a Driver:");
				for (int i = 0; i < rides.size(); i++) {
					System.out.println("[" + (i+1) + "] " + rides.get(i).toString());
				}
				System.out.println("[0] Done");
				choice = in.nextLine();
				index = - 1;
				if (!choice.equals("0")) 
				{
					try { index = Integer.parseInt(choice);}
					catch (NumberFormatException e) { e.printStackTrace(); }
					try { index = rides.remove(index-1).getID();}
					catch (IndexOutOfBoundsException e) { e.printStackTrace();}
					if (index != -1)
					{
						ArrayList<Carpool> carpools = retrieveCarpools(index);
						for (Carpool c : carpools)
						{
							c.setState(new CancelledState());
							c.state.setStatus(c);
							c.update();
						}
						Ride r = new Ride();
						r.setID(index);
						r.delete(); //delete the ride from the database
					}
				}
			break;
		case "0": // Cancel
		default:
			System.out.println("Cancelling...");
		}
    }


    public ArrayList<Carpool> retrieveCarpools(int rideID) 
	{
    	ArrayList<Carpool> tempcarpools = new ArrayList<>();
		String names = "s.id, driver_status, passenger_status, meet_time, passenger_schedule_id, ride_id, c.id AS carpool_id, start_date, end_date, weekday, commit_time, commuter_id, from_location_id, to_location_id";
		String sql = "SELECT "+names+" FROM carpool c, schedule s WHERE c.passenger_schedule_id = s.id AND ride_id = " + rideID;
		ResultSet rs = db.retrieve(sql);
		try
		{
			while (rs.next()) 
			{
				rs.previous();
				Carpool c = new Carpool();
				c.parseResultSet(rs);
				if (!c.isPending() && !c.isCancelled()) tempcarpools.add(c);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeConnection();
		return tempcarpools;
	}


    public void retrieveCarpoolRequests() 
	{
		carpoolRequests = new ArrayList<>();
		carpools = new ArrayList<>();
		String sql = "SELECT * FROM notification_detail WHERE  (passenger_id = " + id + " OR driver_id = " + id + ") ";
		parseCarpoolResultSet(db.retrieve(sql));
		db.closeConnection();
	}
    public void retrieveCarpools() 
	{
		carpools = new ArrayList<>();
		carpools = new ArrayList<>();
		String names = "s.id, driver_status, passenger_status, meet_time, passenger_schedule_id, ride_id, c.id AS carpool_id, start_date, end_date, weekday, commit_time, commuter_id, from_location_id, to_location_id";
		String sql = "SELECT "+names+" FROM carpool c, schedule s WHERE  s.commuter_id = " + id + " AND c.passenger_schedule_id = s.id ";

		parseCarpoolResultSet(db.retrieve(sql));
		db.closeConnection();
	}
	
    public void parseCarpoolResultSet(ResultSet rs) 
    {
		try {
			while (rs.next()) 
			{
				rs.previous();
				Carpool c = new Carpool();
				c.parseResultSet(rs);
				if (c.isPending()) carpoolRequests.add(c);
				else carpools.add(c);
			}
		} catch (SQLException eX) {
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		}
    }
    
    /**
     * Manages the rides for a commuter
     * @param in
     */
    public void manageNotifications(Scanner in)
    {
    	retrieveCarpoolRequests();
    	retrieveCarpools();
		System.out.println("\n\n*****Manage Notification Menu*****");
		System.out.println("[1] Carpool Requests (" + carpoolRequests.size() + ")");
		System.out.println("[2] Carpool Status Updates(" + carpools.size() + ")");
		System.out.println("[0] Cancel");
		String res1 = in.nextLine();
		switch (res1)
		{
		case "1":
			System.out.println("\n*****Manage Carpool Requests*****");
			for (int i = 0; i < carpoolRequests.size(); i++)
			{
				System.out.println("["+(i+1)+"] " + carpoolRequests.get(i).toString());
			}
			System.out.println("[0] Done");
			try { int res = Integer.parseInt(in.nextLine()); 
				if (res > 0 && res <= carpoolRequests.size()) carpoolRequests.get(res-1).manage(in);
			} catch (NumberFormatException e) {};
			break;
		case "2":
			System.out.println("\n*****Track Carpool Status*****");
			for (int i = 0; i < carpools.size(); i++)
			{
				System.out.println("["+(i+1)+"] " + carpools.get(i).toString());
			}
			System.out.println("[0] Done");
			try { int res = Integer.parseInt(in.nextLine()); 
				if (res > 0 && res <= carpools.size()) carpools.get(res-1).track(in);
			} catch (NumberFormatException e) {};
			break;
		}

    }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void ProcessNotification(Carpool carpool)
	{
		if (carpool.getID() > 0)
		{
			carpoolRequests.add(carpool);
		}
		
	}

	public ArrayList<Carpool> getNewCarpools()
	{
		return carpoolRequests;
	}

	public void setNewCarpools(ArrayList<Carpool> newCarpools)
	{
		this.carpoolRequests = newCarpools;
	}

	public ArrayList<Carpool> getCarpools()
	{
		return carpools;
	}

	public void setCarpools(ArrayList<Carpool> carpools)
	{
		this.carpools = carpools;
	}
        
        public String retrieveNumPassenger() {
        String sql = "SELECT COUNT(*) FROM notification_detail WHERE driver = '"
                + getFirstName() + " " + getLastName() + "'";
        parseRewardResultSet(db.retrieve(sql));
        db.closeConnection();
        return sql;
    }

    public void parseRewardResultSet(ResultSet rs) {
        try
        {
            if (rs.next())
            {
                passengerCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException eX){
            System.out.println("SQLException: " + eX.getMessage());
            System.out.println("SQLState: " + eX.getSQLState());
            System.out.println("VendorError: " + eX.getErrorCode());
        }
    }

    public void manageRewards(Scanner input){
        System.out.println("[1] Show reward by number of passengers");
        System.out.println("[2] Show reward by mileages");
        System.out.println("[0] Cancel");
        String res = input.nextLine();
        
        
        switch(res){
            case "1": // Show by number of passengers taken
                Bonus passengerBronze, passengerSilver, passengerGold;
                retrieveNumPassenger();
                passengerBronze = new RewardByPassenger(passengerCount, new RewardsByPassengerBronze());
                passengerSilver = new RewardByPassenger(passengerCount, new RewardsByPassengerSilver());
                passengerGold = new RewardByPassenger(passengerCount, new RewardsByPassengerGold());

                passengerBronze.give();
                passengerSilver.give();
                passengerGold.give();
                break;
            case "2": // Show by number of miles traveled 
                System.out.print("Enter your current milages: ");
                int mile = input.nextInt();
                Bonus milageBronze, milageSilver, milageGold;
                milageBronze = new RewardByMile(mile, new RewardsByMileBronze());
                milageSilver = new RewardByMile(mile, new RewardsByMileSilver());
                milageGold = new RewardByMile(mile, new RewardsByMileGold());
                
                milageBronze.give();
                milageSilver.give();
                milageGold.give();
                break;
            case "0":
            default:
                System.out.println("Cancelling...");
        }
    }
}
