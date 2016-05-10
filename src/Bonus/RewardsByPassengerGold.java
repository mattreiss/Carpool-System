package Bonus;


public class RewardsByPassengerGold implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if (count == GOLD){
            System.out.println("You get a gold reward!");
        }
    }

    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
