package Bonus;


/**
 * An interface representing the API for rewarding drivers 
 * 
 */
interface RewardAPI{
    static final int BRONZE = 10;
    static final int SILVER = 20;
    static final int GOLD = 30;
    static final int MILE_BRONZE = 100;
    static final int MILE_SILVER = 200;
    static final int MILE_GOLD = 300;
    public void giveRewardByPassenger(int count);
    public void giveRewardByMile(int mile);
}

/* the abstract class */
public abstract class Bonus{
    protected RewardAPI rewardAPI;

    protected Bonus(RewardAPI rewardAPI){
        this.rewardAPI = rewardAPI;
    }
    
    public abstract void give();
}
