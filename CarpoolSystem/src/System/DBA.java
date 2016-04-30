package System;
import java.sql.*;
import java.util.ArrayList;

import Default.Bonus;
import Default.CarpoolConfirmedState;
import Default.ConfirmedState;
import Default.DriverConfirmedState;
import Default.PassengerConfirmedState;
import Default.RewardByPassenger;
import Default.RewardsByPassenger_Bronze;
import Default.RewardsByPassenger_Gold;
import Default.RewardsByPassenger_Silver;
import Default.UnconfirmedState;
import PersistentObjects.Carpool;
import PersistentObjects.Commuter;
import PersistentObjects.Location;
import PersistentObjects.Ride;
import PersistentObjects.ScheduleItem;
import PersistentObjects.Vehicle;

public class DBA 
{
	
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
