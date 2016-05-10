package Bonus;

public class RewardsByMileSilver implements RewardAPI{

    @Override
    public void giveRewardByPassenger(int count) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void giveRewardByMile(int mile) {
        
        if (mile >= MILE_SILVER && mile < MILE_GOLD){
            System.out.println("You get a Silver reward! You need " + (MILE_GOLD - mile)
                    + " more miles to get Gold reward.");
        }
    }
}
