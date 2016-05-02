package System;

import Default.Notification;

public class Alert extends Notification
{
	private String alert;
	
	public void setAlert(String a)
	{
		alert = a;
		alert(this);
	}
	
	public String getAlert()
	{
		return alert;
	}
}
