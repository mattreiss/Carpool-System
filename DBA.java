import java.sql.*;
import java.util.ArrayList;

public class DBA extends CRUD
{
	private static final DBA singletonDBA = new DBA();

	private DBA()
	{
		super();
	}

	public static DBA getDBA()
	{
		return singletonDBA;
	}

	/**
	 * Inserts a new commuter account into the database.
	 * 
	 * @param c
	 *            the commuter to insert.
	 * @return commuterID the ID of a commuter
	 */
	public int createCommuter(Commuter c)
	{
		String table = "commuter (first_name, last_name, email, password)";
		String values = "'" + c.getFirstName() + "', '" + c.getLastName()
				+ "', '" + c.getEmail() + "', '" + c.getPassword() + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		return super.create(sql);
	}

	/**
	 * Selects an existing commuter account from the database.
	 * 
	 * @param email
	 *            the email of the commuter
	 * @param password
	 *            the password of the commuter
	 * @return tmpCommuter a commuter object
	 */
	public Commuter retrieveCommuter(String email, String password)
	{
		Commuter tmpCommuter = null;
		String sql = "SELECT * FROM commuter WHERE email = '" + email + "'";
		ResultSet results = super.retrieve(sql);
		try
		{
			if (results != null && results.next())
			{
				if (password.equals(results.getString("password")))
				{
					tmpCommuter = new Commuter(results.getInt("id"),
							results.getString("first_name"),
							results.getString("last_name"), email, password);
				} else
					System.out.println("Invalid Password.");
			} else
				System.out.println("User not registered.");
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}
		return tmpCommuter;
	}

	/**
	 * Updates an existing commuter account from the database.
	 * 
	 * @param c
	 *            the commuter to update.
	 */
	public void updateCommuter(Commuter c)
	{
		String sql = "UPDATE commuter SET email = '" + c.getEmail()
				+ "', password = '" + c.getPassword() + "', first_name = '"
				+ c.getFirstName() + "', last_name = '" + c.getLastName()
				+ "' WHERE id = '" + c.getID() + "'";
		super.update(sql);
	}

	/**
	 * Deletes an existing commuter account from the database.
	 * 
	 * @param c
	 *            the commuter to delete.
	 */
	public void deleteCommuter(Commuter c)
	{
		String sql = "DELETE FROM commuter WHERE id = '" + c.getID() + "'";
		super.update(sql);
	}

	/**
	 * Creates a new schedule in the database.
	 * 
	 * @param commuterID
	 *            the ID of the commuter
	 * @param s
	 *            the schedule to insert.
	 * @return schedItemID - the ID of the schedule item
	 */
	public int createScheduleItem(int commuterID, ScheduleItem s)
	{
		String table = "schedule (start_date, end_date, weekday, commit_time, commuter_id, from_location_id, to_location_id)";
		String values = "'" + s.getStartDate() + "', '" + s.getEndDate() + "','"
				+ s.getWeekDay() + "', '" + s.getTime() + "', '" + commuterID
				+ "','" + s.getFrom().getID() + "','" + s.getTo().getID() + "'";
		String sql = "INSERT INTO " + table + " VALUES (" + values + ");";
		return super.create(sql);
	}

	/**
	 * Updates an existing schedule from the database.
	 * 
	 * @param s
	 *            the schedule to update.
	 */
	public void updateScheduleItem(ScheduleItem s)
	{
		// to do
	}

	/**
	 * Deletes an existing schedule from the database.
	 * 
	 * @param s
	 *            the schedule to delete.
	 */
	public void deleteScheduleItem(ScheduleItem s)
	{
		String sql = "DELETE FROM commuter WHERE id = '" + s.getID() + "'";
		super.update(sql);
	}

	public int createLocation(Location l)
	{
		String table = "location (address, zip, latitude, longitude)";
		String values = "'" + l.getAddress() + "', '" + l.getZip() + "', '"
				+ l.getLatitude() + "', '" + l.getLongitude() + "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		return super.create(sql);
	}

	public void updateLocation(Location l)
	{
		String sql = "UPDATE location SET address = '" + l.getAddress()
				+ "', zip = '" + l.getZip() + "', latitude = '"
				+ l.getLatitude() + "', longitude = '" + l.getLongitude()
				+ "' WHERE id = '" + l.getID() + "'";
		super.update(sql);
	}

	public Location retrieveLocation(int location_id)
	{
		Location location = null;
		String sql = "SELECT * FROM location WHERE id = '" + location_id + "'";
		ResultSet results = super.retrieve(sql);
		try
		{
			if (results.next())
			{
				location = new Location(results.getString("address"),
						results.getInt("zip"));
				location.setID(results.getInt("id"));
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}

		return location;
	}

	public int createCommuterLocation(int commuterID, int locationID,
			int isHome)
	{
		String table = "commuter_location (home, commuter_id, location_id)";
		String values = "'" + isHome + "', '" + commuterID + "', '" + locationID
				+ "'";
		String sql = "INSERT INTO " + table + "VALUES (" + values + ");";
		return super.create(sql);
	}

	public Location retrieveCommuterLocation(int commuter_id, int isHome)
	{
		Location location = null;
		String sql = "SELECT * FROM commuter_location cl, location l "
				+ "WHERE cl.commuter_id = '" + commuter_id
				+ "' AND cl.location_id = l.id AND cl.home = '" + isHome + "'";
		ResultSet results = super.retrieve(sql);
		try
		{
			if (results.next())
			{
				location = new Location(results.getString("address"),
						results.getInt("zip"));
				location.setID(results.getInt("id"));
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}

		return location;
	}

	public int createVehicle(Vehicle v, int commuterID)
	{
		String sql = "INSERT INTO vehicle (capacity, make, model, year, color, driver_commuter_id) "
				+ "VALUES ('" + v.getSeats() + "', '" + v.getMake() + "', '"
				+ v.getModel() + "', '" + v.getYear() + "', '" + v.getColor()
				+ "', '" + commuterID + "')";
		return create(sql);
	}

	public ArrayList<Vehicle> retrieveVehicles(int commuterID)
	{
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		String sql = "SELECT * FROM vehicle WHERE driver_commuter_id = '"
				+ commuterID + "'";
		ResultSet results = super.retrieve(sql);
		try
		{
			while (results.next())
			{
				Vehicle v = new Vehicle(results.getInt("capacity"),
						results.getString("make"), results.getString("model"),
						results.getInt("year"), results.getString("color"));
				v.setID(results.getInt("id"));
				vehicles.add(v);
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}

		return vehicles;
	}

	public void updateVehicle()
	{
		// to do
	}

	public void deleteVehicle()
	{

	}

	public int createRide(Ride ride)
	{
		String sql = "INSERT INTO ride (vacancy, driver_schedule_id, vehicle_id) "
				+ "VALUES ('" + ride.getVacancy() + "', '"
				+ ride.getSchedule().getID() + "', '"
				+ ride.getVehicle().getID() + "')";
		return create(sql);
	}
	
	public ArrayList<ScheduleItem> matchSchedules(ScheduleItem scheduleToMatch,
			int cID, boolean bRider)
	{
		ArrayList<ScheduleItem> schedules = new ArrayList<>();
		String sql = "SELECT * FROM commuterschedule "
				+ "WHERE ("
					+ "(from_location_id = " + scheduleToMatch.getFrom().getID() + ")"
					+ " OR "
					+ "(to_location_id = " + scheduleToMatch.getTo().getID() + "))"
				+ " AND weekday = " + scheduleToMatch.getWeekDay() 
				+ " AND commuter_id <> " + cID
				+ " AND ride_id " + (bRider ? "is not null " : "is null ")
				+ " AND carpool_ride_id is null " + "ORDER BY commit_time";
		ResultSet results = super.retrieve(sql);
		try
		{
			while (results.next())
			{
				schedules.add(commuterScheduleItem(results));
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}
		return schedules;
	}

	public ArrayList<ScheduleItem> retrieveSchedule(int cid, String sqlCrit) {
		String sql = "SELECT * FROM commuterschedule WHERE commuter_id = " + cid;
		if(!sqlCrit.isEmpty()) {
			sql = sql + " AND " + sqlCrit;
		}
		
		ArrayList<ScheduleItem> tempSched = new ArrayList<>();
		ResultSet results = super.retrieve(sql);
		try
		{
			while (results.next())
			{
				tempSched.add(commuterScheduleItem(results));
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}
		return tempSched;
	}
	
	private ScheduleItem commuterScheduleItem(ResultSet results)
			throws SQLException {
		
		Location l1 = new Location(
				results.getInt("from_location_id"),
				results.getString("from_address"),
				results.getInt("from_zip"));
		Location l2 = new Location(
				results.getInt("to_location_id"),
				results.getString("to_address"),
				results.getInt("to_zip"));
		
		int rideId = results.getInt("ride_id");
		Ride ride = null;
		if (!results.wasNull()) {
			Vehicle v = new Vehicle(
					results.getInt("vehicle_id"),
					results.getInt("capacity"), results.getString("make"),
					results.getString("model"), results.getInt("year"),
					results.getString("color"));
			ride = new Ride(
					rideId,
					results.getInt("vacancy"), v, null);
		}

		ScheduleItem s = new ScheduleItem(
				results.getInt("schedule_id"),
				results.getString("start_date"), 
				results.getString("end_date"),
				results.getInt("weekday"), 
				results.getString("commit_time"), 
				l1, l2, 
				results.getString("name"), ride);
		return s;
	}
	
	public int createCarpool(Carpool carpool)
	{
		String sql = "INSERT INTO carpool (driver_status, passenger_status, meet_time, passenger_schedule_id, ride_id) "
				+ "VALUES ('" + carpool.getDriverStatus() + "', '"
				+ carpool.getPassengerStatus() + "', '" + carpool.getTime()
				+ "', '" + carpool.getSchedule().getID() + "', '"
				+ carpool.getRide().getID() + "')";
		return create(sql);
	}

	public void showRewards(Commuter c)
	{
		Bonus bronzeRew, silverRew, goldRew;

		String sql = "SELECT COUNT(*) FROM notification_detail WHERE driver = '"
				+ c.getFirstName() + " " + c.getLastName() + "'";

		ResultSet results = super.retrieve(sql);
		try
		{
			if (results.next())
			{
				int total = results.getInt("COUNT(*)");
				bronzeRew = new RewardByPassenger(total,
						new RewardsByPassenger_Bronze());
				silverRew = new RewardByPassenger(total,
						new RewardsByPassenger_Silver());
				goldRew = new RewardByPassenger(total,
						new RewardsByPassenger_Gold());

				bronzeRew.give();
				silverRew.give();
				goldRew.give();
			} else
			{
				System.out.println("Failed");
			}

		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}
	}

	public ArrayList<ConfirmedState> retrieveConfirmedStates(int commuterID)
	{
		ArrayList<ConfirmedState> s = new ArrayList<>();
		String sql = "SELECT * FROM notification_detail WHERE driver_id = '"
				+ commuterID + "' OR passenger_id = '" + commuterID + "'";
		ResultSet results = super.retrieve(sql);
		try
		{
			while (results.next())
			{
				int p = results.getInt("passenger_status");
				int d = results.getInt("driver_status");
				if (p == 0 && d == 1)
				{
					s.add(new DriverConfirmedState());
				} else if (p == 1 && d == 0)
				{
					s.add(new PassengerConfirmedState());
				} else if (p == 1 && d == 1)
				{
					s.add(new CarpoolConfirmedState());
				} else
				{
					s.add(new UnconfirmedState());
				}
			}
		} catch (SQLException eX)
		{
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			super.closeConnection();
		}
		return s;
	}

}
