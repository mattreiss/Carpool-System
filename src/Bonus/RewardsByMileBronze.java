package Bonus;

public class RewardsByMileBronze implements RewardAPI{

    @Override
    public void giveRewardByPassenger(int count) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void giveRewardByMile(int mile) {
        
        if(mile < MILE_BRONZE){
            System.out.println("You need " + (MILE_BRONZE - mile) + " more miles to get Bronze reward.");
        }
        else if (mile >= MILE_BRONZE && mile < MILE_SILVER){
            System.out.println("You get a Bronze reward! You need " + (MILE_SILVER - mile) 
                    + " more miles to get Silver reward.");
        }
    }
}
