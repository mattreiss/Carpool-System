package Default;
interface RewardAPI{
    static final int BRONZE = 10;
    static final int SILVER = 20;
    static final int GOLD = 30;
    static final int MILE_COUNT = 100;
    public void giveRewardByPassenger(int count);
    public void giveRewardByMile(int mile);
}

/* the abstract class */
abstract class Bonus{
    protected RewardAPI rewardAPI;

    protected Bonus(RewardAPI rewardAPI){
        this.rewardAPI = rewardAPI;
    }
    
    public abstract void give();
}

/* the concrete class */
class RewardByPassenger extends Bonus{
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

class RewardByMile extends Bonus{
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

/* concrete implementor */
class RewardsByPassenger_Bronze implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < BRONZE){
            System.out.println("You need " + (BRONZE - count) + " more passengers to reach Bronze");
        }
        else if (count == BRONZE){
            System.out.println("You get a bronze reward!");
        }
        else 
            System.out.println("You need " + (SILVER - count) + " more passengers to reach Silver");
    }
    
    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class RewardsByPassenger_Silver implements RewardAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if (count == SILVER){
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

class RewardsByPassenger_Gold implements RewardAPI{
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


class RewardsByMile implements RewardAPI{

    @Override
    public void giveRewardByPassenger(int count) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void giveRewardByMile(int mile) {
        
        if(mile < MILE_COUNT){
            System.out.println("You need " + (MILE_COUNT - mile) + " more miles to get the reward.");
        }
        else if (mile == MILE_COUNT){
            System.out.println("You get a reward!");
        }
        else 
            System.out.println("Well done, you passed reward by " + (mile - MILE_COUNT) + " miles!");
        
    
    }
    
}