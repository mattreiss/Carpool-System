package PersistentObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Parking implements PersistentObject{
    
    private final Scanner in = new Scanner(System.in);
    
    private int dayOfWeek;
    private String driverName;
    private String startTime;
    private String endTime;
    private int spotID;
    
    public Parking(){}
       
    public String retrieveParking(Commuter commuter) {
        
        String sql = "SELECT name, weekday, arrTime, depTime, spot_id " 
                    + "FROM parking join driver_schedule " 
                    + "WHERE driverID = " + commuter.getID() + " and parking.dayofweek = driver_schedule.weekday " 
                    + "GROUP BY spot_id, availability;";
        
        parseResultSet(db.retrieve(sql));
        db.closeConnection();
        return sql;
    }

    
    @Override
    public void parseResultSet(ResultSet rs) {
        try{
            System.out.println("**** Available parking spaces ****");
            System.out.format("%15s |%15s |%15s |%15s |%15s\n", "driver", "day of week", "start time", "end time", "spot ID");
            
            while(rs.next() && rs !=null){
                driverName = rs.getString("name");
                startTime = rs.getString("arrTime");
                endTime = rs.getString("depTime");
                spotID = rs.getInt("spot_id");
                dayOfWeek = rs.getInt("weekday");
                
                System.out.format("%15s |%15s |%15s |%15s |%15s\n", driverName, dayOfWeek, startTime, endTime, spotID);
                System.out.println();
            }
        }
        catch(SQLException eX){
            System.out.println("SQLException: " + eX.getMessage());
            System.out.println("SQLState: " + eX.getSQLState());
            System.out.println("VendorError: " + eX.getErrorCode());
        }
    }
    
    
    public void manage(Commuter c) {
        System.out.println("Select parking options:");
        System.out.println("[1] Reserve next available");
        System.out.println("[2] Let me choose my own");
        System.out.println("[3] View my parking schedule");
        switch(in.nextLine()){
            case "1":
                autoReserve(c);
                break;
            case "2":
                retrieveParking(c);
                manualReserve(c);
                break;
            case "3": 
                viewParkingSchedule(c);
                break;
            default:
                break;
        }
    }

    @Override
    public int getID() {
        return spotID;
    }

    @Override
    public void setID(int id) {
        
    }

    @Override
    public String create() {
        return null;
    }

    @Override
    public String retrieve() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void manage(Scanner in) {
        
    }

    private String autoReserve(Commuter commuter) {
        String sql = " ";
        db.update(sql);
        return sql;
    }

    private String manualReserve(Commuter commuter) {
        System.out.println("Select weekday: ");
        int dow = in.nextInt();
        
        System.out.println("Select parking spot: ");
        int res = in.nextInt();
        
        String sql = "UPDATE parking INNER JOIN (select driverID, startTime, endTime, arrTime, depTime, weekday "
                + "FROM parking join driver_schedule) q1 "
                + "ON arrTime <= parking.startTime AND parking.endTime <= depTime "
                + "AND parking.startTime < depTime AND q1.weekday = " + dow 
                + " SET availability = 0, driver_id = " + commuter.getID() + " WHERE spot_id = " + res;
        db.update(sql);
        return sql;
    }

    private void viewParkingSchedule(Commuter c) {
        
    }
}
