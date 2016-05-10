package Bonus;

public class RewardByPassenger extends Bonus{
    private final int count;

    public RewardByPassenger(int count, RewardAPI rewardAPI){
        super(rewardAPI);
        this.count = count;
    }

    @Override
    public void give(){
        rewardAPI.giveRewardByPassenger(count);
    }
}