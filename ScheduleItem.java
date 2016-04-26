public class ScheduleItem
{
    private int id;
    private String startDate;
    private String endDate;
    private int weekday; 
    private String commit_time; 
    private Location from;
    private Location to;
    private String commuterName = "";
    private Ride ride = null;
    
    String[] dow = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    
    public ScheduleItem (String startDate, String endDate, int day, String time, Location from, Location to) 
    {
    	this.id = -1;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.weekday = day;
    	this.commit_time = time;
    	this.from = from;
    	this.to = to;
    }
 
    public ScheduleItem (int id, String startDate, String endDate, int day, String time, 
    					 Location from, Location to, String name, Ride ride) {
    	this.id = id;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.weekday = day;
    	this.commit_time = time;
    	this.from = from;
    	this.to = to;
    	this.commuterName = name;
    	this.ride = ride;
    }
    
    public int getID()			{return id;}
    public String getStartDate() 	{return startDate;}
    public String getEndDate() 	{return endDate;}
    public int getWeekDay() 	{return weekday;}
    public String getTime() 	 	{return commit_time;}
    public Location getFrom()	{return from;}
    public Location getTo()		{return to;}
    public String getCommuterName() {return commuterName;}
    public Ride getRide() {return ride;}
    
    public void setId(int id)					{this.id = id;}
    public void setStartDate(String date)		{this.startDate = date;}
    public void setEndDate(String date)			{this.endDate = date;}
    public void setDay(int day)					{this.weekday = day;}
    public void setTime(String time)			{this.commit_time = time;}
    public void setFrom(Location from)			{this.from = from;}
    public void setTo(Location to)				{this.to = to;}
    public void setCommuterName(String name)	{this.commuterName = name;}
    public void setRide(Ride ride)				{this.ride = ride;}
    
    @Override
    public String toString()
    {
    	String result = commuterName + " ";
    	result += dow[weekday-1] + " at ";
    	result += commit_time + " from ";
    	result += from.getAddress() + " to ";
    	result += to.getAddress();
    	
    	return result;
    }
}
