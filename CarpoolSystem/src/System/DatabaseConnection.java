package System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection
{

	private static Connection connection = null;
	private static PreparedStatement prepStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	private static final String DB_DVR = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/carpool_database";
	private static final String DB_UID = "root";
	private static final String DB_PWD = "admin";
	private static final DatabaseConnection singletonDatabaseConnection = new DatabaseConnection();


	public static DatabaseConnection getDatabaseConnection()
	{
		return singletonDatabaseConnection;
	}
	
	private DatabaseConnection()
	{
		try
		{
			Class.forName(DB_DVR);
		} catch (ClassNotFoundException e)
		{
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		}
	}

	/**
	 * Method to establish connection with database server.
	 */
	private void establishConnection() throws SQLException
	{
		connection = DriverManager.getConnection(DB_URL, DB_UID, DB_PWD);
	}

	/**
	 * Method to close connection with database server.
	 */
	public void closeConnection()
	{
		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			} catch (SQLException eX)
			{
			}
			resultSet = null;
		}

		if (statement != null)
		{
			try
			{
				statement.close();
			} catch (SQLException eX)
			{
			}
			statement = null;
		}

		if (prepStatement != null)
		{
			try
			{
				prepStatement.close();

			} catch (SQLException eX)
			{
			}
			prepStatement = null;
		}

		try
		{
			if (connection != null) connection.close();
		} catch (SQLException eX)
		{
		}
		connection = null;
	}

	/**
	 * Makes database insert and returns the generated id 
	 * @param sql the insert statement
	 * @return the generated id 
	 */
	public int create(String sql)
	{
		int id = -1;
		try
		{
			establishConnection();
			prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStatement.executeUpdate();
			resultSet = prepStatement.getGeneratedKeys();
			if (resultSet.next())
			{
				id = resultSet.getInt(1);
			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally
		{
			closeConnection();
		}
		return id;
	}
	
	/**
	 * Makes a database query and returns the results.
	 * @param sql the query to execute
	 * @return the resultSet of the query
	 */
	public ResultSet retrieve(String sql)
	{
		try {
			establishConnection();
			prepStatement = connection.prepareStatement(sql);
			resultSet = prepStatement.executeQuery();
			
		} catch (SQLException eX) {
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
			
		}

		return resultSet;
	}
	
	/**
	 * Makes a database update.
	 * @param sql the update/delete statement to execute
	 */
	public void update(String sql)
	{
		try
		{
			establishConnection();
			prepStatement = connection.prepareStatement(sql);
			prepStatement.execute();
		} catch (SQLException eX)
		{
			System.out.println(sql);
			System.out.println("SQLException: " + eX.getMessage());
			System.out.println("SQLState: " + eX.getSQLState());
			System.out.println("VendorError: " + eX.getErrorCode());
		} finally
		{
			closeConnection();
		}
	}
}
