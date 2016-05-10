package Bonus;

public class RewardsByMileGold implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        
    }
    
    @Override
    public void giveRewardByMile(int mile){
        if (mile >= MILE_GOLD){
            System.out.println("You get a Gold reward!");
        }
    }
}