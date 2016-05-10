package Bonus;

public class RewardByMile extends Bonus{
    private final int mile;
    
    public RewardByMile(int mile, RewardAPI rewardAPI){
        super(rewardAPI);
        this.mile = mile;
    }
    
    @Override
    public void give(){
        rewardAPI.giveRewardByMile(mile);
    }
}