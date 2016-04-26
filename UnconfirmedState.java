
public class UnconfirmedState implements ConfirmedState
{
	private Confirmation c;
	
	public UnconfirmedState()
	{
	}

	@Override
	public void confirm()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString()
	{
		return "Passenger and Driver are not Confirmed.";
	}
}
