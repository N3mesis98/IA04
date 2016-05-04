package tp7.model;

import sim.engine.SimState;
import sim.engine.Steppable;
import tp7.Constants;


/**
 * Created by Mar on 04/05/2016.
 */
public class Food implements Steppable {
    public int x, y;
    public int quantity = Constants.MAX_FOOD;
    private Beings beings;
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getFood(int nbFood){
        if(nbFood >= quantity){
            int pquantity = quantity;
            quantity = Constants.MAX_FOOD;
            x = beings.random.nextInt(beings.yard.getWidth());
            y = beings.random.nextInt(beings.yard.getHeight());
            beings.yard.remove(this);
            beings.yard.setObjectLocation(this, x,y);
            return pquantity;
        }
        else {
            quantity-=nbFood;
            return nbFood;
        }
    }
    @Override
    public void step(SimState state) {
        beings = (Beings) state;
    }
}
