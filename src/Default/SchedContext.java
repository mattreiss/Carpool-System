package Default;
public class SchedContext {
	protected SchedScheme schedScheme;
    
	public SchedContext() {
		schedScheme = new SchedSchemeRider();
	}
	
    public void assignToCarpool() {
        schedScheme.assignCarpool();
    }

    public void setSchedulingScheme(SchedScheme schemeType) {
        this.schedScheme = schemeType;
    }
   
    
}
