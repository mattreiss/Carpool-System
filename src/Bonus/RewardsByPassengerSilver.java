package Bonus;


public class RewardsByPassengerSilver implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < SILVER){
            System.out.println("You need " + (SILVER - count) + " more passengers to reach Silver");
        }
        else if (count == SILVER){
            System.out.println("You get a silver reward!");
        }
        else 
            System.out.println("You need " + (GOLD - count) + " more passengers to reach Gold");
    }

    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
