interface GiveAPI{
    static final int BRONZE = 10;
    static final int SILVER = 20;
    static final int GOLD = 30;
    static final int MILE_COUNT = 100;
    public void giveRewardByPassenger(int count);
    public void giveRewardByMile(int mile);
}

/* the abstract class */
abstract class Bonus{
    protected GiveAPI giveAPI;

    protected Bonus(GiveAPI giveRewardAPI){
        this.giveAPI = giveRewardAPI;
    }
    
    public abstract void give();
}

/* the concrete class */
class RewardByPassenger extends Bonus{
    private final int count;

    public RewardByPassenger(int count, GiveAPI grAPI){
        super(grAPI);
        this.count = count;
    }

    @Override
    public void give(){
        giveAPI.giveRewardByPassenger(count);
    }
}

class RewardByMile extends Bonus{
    private final int mile;
    
    public RewardByMile(int mile, GiveAPI grAPI){
        super(grAPI);
        this.mile = mile;
    }
    
    @Override
    public void give(){
        giveAPI.giveRewardByMile(mile);
    }
}

/* concrete implementor */
class RewardsByPassenger_Bronze implements GiveAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < BRONZE){
            System.out.println("You need " + (BRONZE - count) + " more passengers to reach Bronze");
        }
        else if (count == BRONZE){
            System.out.println("You get a bronze reward!");
        }
        else 
            System.out.println("Well done, you passed bronze reward by " + (count - BRONZE) + " passengers!");
        
    }
    
    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class RewardsByPassenger_Silver implements GiveAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < SILVER){
            System.out.println("You need " + (SILVER - count) + " more passengers to reach Silver");
        }
        else if (count == SILVER){
            System.out.println("You get a silver reward!");
        }
        else 
            System.out.println("Well done, you passed silver reward by " + (count - SILVER) + " passengers!");
    }

    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}

class RewardsByPassenger_Gold implements GiveAPI{
    @Override
    public void giveRewardByPassenger(int count){
        if(count < GOLD){
            System.out.println("You need " + (GOLD - count) + " more passengers to reach Gold");
        }
        else if (count == GOLD){
            System.out.println("You get a gold reward!");
        }
        else 
            System.out.println("Well done, you passed gold reward by " + (count - GOLD) + " passengers!");
    }

    @Override
    public void giveRewardByMile(int mile) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}


class RewardsByMile implements GiveAPI{

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