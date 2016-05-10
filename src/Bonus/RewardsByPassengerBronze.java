package Bonus;


public class RewardsByPassengerBronze implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < BRONZE){
            System.out.println("You need " + (BRONZE - count) + " more passengers to reach Bronze");
        }
        else if (count == BRONZE){
            System.out.println("You get a bronze reward!");
        }
    }
    
    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
