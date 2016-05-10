package Scheduling;
import PersistentObjects.*;

public class SchedContext {
	private SchedScheme schedScheme;
	
    public void assignToCarpool(Commuter c, int typeSelection) {
    	setSchedulingScheme(typeSelection);
        schedScheme.assignCarpool(c);
    }

    private void setSchedulingScheme(int typeSelection) {
    	
        switch (typeSelection) {
        case 1:
        	schedScheme = new SchedSchemeSystem();
        	break;
        case 2:
        	schedScheme = new SchedSchemeRider();
        	break;
        case 3:
        	schedScheme = new SchedSchemeDriver();
        }
    }
}
