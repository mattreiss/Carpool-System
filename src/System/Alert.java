package System;

import PersistentObjects.Carpool;

public class Alert extends Notification
{
	private String alert;
	private Carpool carpool;
	
	/**
	 * sets an alert message and passes this object to notify observers.
	 * @param a
	 */
	public void setAlert(String a)
	{
		alert = a;
		alert(this);
	}
	
	public String getAlert()
	{
		return alert;
	}

	public Carpool getCarpool()
	{
		return carpool;
	}
	public void setCarpool(Carpool carpool)
	{
		this.carpool = carpool;
	}
}
