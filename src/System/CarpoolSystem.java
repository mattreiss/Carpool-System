package System;
import PersistentObjects.PersistentObject;

public class CarpoolSystem 
{
	/**
	 * Main method to control the program interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		SystemInterface system = new SystemInterface();
		PersistentObject.alert.add(system);
		system.startMainLoop();
	}
	
}
