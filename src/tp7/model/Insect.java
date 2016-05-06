package tp7.model;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Int2D;
import sim.util.Bag;

import tp7.Constants;

public class Insect implements Steppable {
    public static int count = 1;
    public String name;
    
    public int x, y;
    public int charge = 1;
    public int mouvement = 1;
    public int perception = 1;
    public int currEnergy = Constants.MAX_ENERGY;
    public int currCharge = 0;
    public Stoppable stop;

    public Insect(int x, int y, int charge, int mouvement, int perception) {
        this.x = x;
        this.y = y;
        this.charge += charge;
        this.mouvement += mouvement;
        this.perception += perception;
        this.name = ""+count++;
    }
    
    public Insect(int x, int y, int charge, int mouvement, int perception, String name) {
        this.x = x;
        this.y = y;
        this.charge += charge;
        this.mouvement += mouvement;
        this.perception += perception;
        this.name = name;
    }

    @Override
    public void step(SimState state) {
        Beings beings = (Beings) state;
        
        Food localFood = getFoodAtLocation(beings, x, y);
        if (localFood != null) {
            // there is food here
            System.out.println(name+" : food here !");
        }
        else {
            Int2D distantFoodLocation = perception(beings);
            if (distantFoodLocation != null) {
                moveInDirectionOf(beings, distantFoodLocation.x, distantFoodLocation.y);
            }
            else {
                // random mouvement value (!= 0)
                int xx = beings.random.nextInt(mouvement)+1;
                int yy = beings.random.nextInt(mouvement)+1;
                
                // random sign change
                if (beings.random.nextBoolean()) xx = -xx;
                if (beings.random.nextBoolean()) yy = -yy;
                
                move(beings, xx, yy);
            }
        }
        
        if (currEnergy <= 0) {
            if (Constants.DEBUG) System.out.println(name+" : dies at ("+x+", "+y+")");
            
            beings.setNumInsects(beings.getNumInsects()-1); // when insect dies decrement the number of insect important for the inspector
            beings.yard.remove(this);
            stop.stop();
        }
    }

    public void move(Beings beings, int xx, int yy) {
        if (Constants.DEBUG) System.out.println(name+" : move ("+xx+", "+yy+") from ("+x+", "+y+")");
        
        x = mod((x+xx),beings.yard.getWidth());
        y = mod((y+yy),beings.yard.getHeight());
        currEnergy--;

        beings.yard.remove(this);
        beings.yard.setObjectLocation(this, x, y);
    }
    
    // move at maximum speed in direction of the specified delta location
    public void moveInDirectionOf(Beings beings, int xx, int yy) {
        if (Math.abs(xx) > mouvement) {
            if (xx > 0) {
                xx = mouvement;
            }
            else {
                xx = -mouvement;
            }
        }
        
        if (Math.abs(yy) > mouvement) {
            if (yy > 0) {
                yy = mouvement;
            }
            else {
                yy = -mouvement;
            }
        }
        
        move(beings, xx, yy);
    }

    // modulus
    public int mod(int a, int b){
        return (((a % b) + b) % b);
    }

    /**
     * Method to charge food. The insect must be on the same case as the food
     * @param food food to take food for the charge
     */
    public void chargerFood(Food food){
        if (Constants.DEBUG) System.out.println(name+" : charge at ("+x+", "+y+")");
        
        if (currCharge < charge && food!=null){//maybe error because food never get null, need to test
            currCharge += food.getFood(Constants.NB_UNIT_FOOD_PER_TURN);
        }
    }

    /**
     * Method to eat to a case next to the insect or from its charge
     * @param food either a food or null, if null then try to eat from the charge
     */
    public void eat(Food food){
        if(currEnergy < Constants.MAX_ENERGY){
            int lunch = 0;
            if(food!=null){
                lunch = food.getFood(Constants.NB_UNIT_FOOD_PER_TURN);
            }
            
            if(lunch > 0){
                lunch = (lunch * Constants.FOOD_ENERGY);

            }
            else if (currCharge > 0) {
                int n = Math.min(Constants.NB_UNIT_FOOD_PER_TURN, currCharge);
                currCharge -= n;
                lunch = (n * Constants.FOOD_ENERGY);
            }
            
            if (Constants.DEBUG) System.out.println(name+" : eat "+lunch+" at ("+x+", "+y+")");
            
            if(lunch > 0) {
                currEnergy = Math.min(currEnergy+lunch, Constants.MAX_ENERGY);
            }
        }
    }
    
    // return delta location of closest food or null
    public Int2D perception(Beings beings) {
        for (int i=1; i<=perception; i++) {
            for (int xx=-i; xx<=i; xx++) {
                if (xx==i || xx==-i) {
                    for (int yy=-i; yy<=i; yy++) {
                        Food food = getFoodAtLocation(beings, x+xx, y+yy);
                        if (food != null) {
                            if (Constants.DEBUG) System.out.println(name+" : found food at ("+(x+xx)+", "+(y+yy)+") from ("+x+", "+y+")");
                            return new Int2D(xx, yy);
                        }
                    }
                }
                else {
                    Food food = getFoodAtLocation(beings, x+xx, y+i);
                    if (food != null) {
                        if (Constants.DEBUG) System.out.println(name+" : found food at ("+(x+xx)+", "+(y+i)+") from ("+x+", "+y+")");
                        return new Int2D(xx, i);
                    }
                        
                    food = getFoodAtLocation(beings, x+xx, y-i);
                    if (food != null) {
                        if (Constants.DEBUG) System.out.println(name+" : found food at ("+(x+xx)+", "+(y-i)+") from ("+x+", "+y+")");
                        return new Int2D(xx, -i);
                    }
                }
            }
        }
        return null; // nothing in range
    }
    
    // return Food if there is one at the specified absolute location or null
    public Food getFoodAtLocation(Beings beings, int xx, int yy) {
        Bag agents = beings.yard.getObjectsAtLocation(xx, yy);
        if (agents != null) {
            for (int i=0; i<agents.size(); i++) {
                Object agt = agents.get(i);
                if (agt!=null && agt.getClass()==Food.class) {
                    return (Food) agt;
                }
            }
        }
        return null; // no food in current position
    }


  /*protected int friendsNum(Beings beings) {
    return friendsNum(beings,x,y);
 }
  protected int friendsNum(Beings beings,int l,int c) {
        int nb = 0;
        for (int i = -1 ; i <= 1 ; i++) {
        for (int j = -1 ; j <= 1 ; j++) {
          if (i != 0 || j != 0) {
              Int2D flocation = new Int2D(beings.yard.stx(l + i),beings.yard.sty(c + j));
              Object ag = beings.yard.get(flocation.x,flocation.y);
              if (ag != null) {
                  if (ag.getClass() == this.getClass())
                      nb++;
              }
          }
        }
      }
      return nb;
     }
  
  public boolean move(Beings beings) {
    boolean done = false;
    int n = beings.random.nextInt(Beings.NB_DIRECTIONS);
    switch(n) {
    case 0: 
        if (beings.free(x-1, y) 
             && friendsNum(beings,x-1,y) >= LEVEL) {
         beings.yard.set(x, y, null);
         beings.yard.set(beings.yard.stx(x-1), y, this);
         x = beings.yard.stx(x-1);
         done = true;
        }
        break;
    case 1:
        if (beings.free(x+1, y) && friendsNum(beings,x+1,y) >= LEVEL) {
         beings.yard.set(x, y, null);
         beings.yard.set(beings.yard.stx(x+1), y, this);
         x = beings.yard.stx(x+1);
         done = true;
        }
        break;
    case 2:
        if (beings.free(x, y-1) && friendsNum(beings,x,y-1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(x, beings.yard.sty(y-1), this);
            y = beings.yard.sty(y-1);
            done = true;
        }
        break;
    case 3: 
        if (beings.free(x, y+1) && friendsNum(beings,x,y+1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(x, beings.yard.sty(y+1), this);
            y = beings.yard.sty(y+1);
            done = true;
        }
        break;
    case 4:
        if (beings.free(x-1, y-1) && friendsNum(beings,x-1,y-1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y-1), this);
            x = beings.yard.stx(x-1);
            y = beings.yard.sty(y-1);
            done = true;
        }
        break;
    case 5:
        if (beings.free(x+1, y-1) && friendsNum(beings,x+1,y-1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y-1), this);
            x = beings.yard.stx(x+1);
            y = beings.yard.sty(y-1);
            done = true;
        }
        break;
    case 6:
        if (beings.free(x+1, y+1) && friendsNum(beings,x+1,y+1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y+1), this);
            x = beings.yard.stx(x+1);
            y = beings.yard.sty(y+1);
            done = true;
        }
        break;
    case 7:
        if (beings.free(x-1, y+1) && friendsNum(beings,x-1,y+1) >= LEVEL) {
            beings.yard.set(x, y, null);
            beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y+1), this);
            x = beings.yard.stx(x-1);
            y = beings.yard.sty(y+1);
            done = true;
        }
        break;
    }
    return done;
 }
  public boolean move2(Beings beings) {
        boolean done = false;
            int n = beings.random.nextInt(Beings.NB_DIRECTIONS);
            switch(n) {
            case 0: 
                if (beings.free(x-1, y)) {
                 beings.yard.set(x, y, null);
                 beings.yard.set(beings.yard.stx(x-1), y, this);
                 x = beings.yard.stx(x-1);
                 done = true;
                }
                break;
            case 1:
                if (beings.free(x+1, y)) {
                 beings.yard.set(x, y, null);
                 beings.yard.set(beings.yard.stx(x+1), y, this);
                 x = beings.yard.stx(x+1);
                 done = true;
                }
                break;
            case 2:
                if (beings.free(x, y-1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(x, beings.yard.sty(y-1), this);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 3: 
                if (beings.free(x, y+1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(x, beings.yard.sty(y+1), this);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            case 4:
                if (beings.free(x-1, y-1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y-1), this);
                    x = beings.yard.stx(x-1);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 5:
                if (beings.free(x+1, y-1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y-1), this);
                    x = beings.yard.stx(x+1);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 6:
                if (beings.free(x+1, y+1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y+1), this);
                    x = beings.yard.stx(x+1);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            case 7:
                if (beings.free(x-1, y+1)) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y+1), this);
                    x = beings.yard.stx(x-1);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            }
        return done;
     }
  public boolean tryMove(Beings beings,int f) {
        boolean done = false;
            int n = beings.random.nextInt(Beings.NB_DIRECTIONS);
            switch(n) {
            case 0: 
                if (beings.free(x-1, y) && friendsNum(beings,x-1,y) > f) {
                 beings.yard.set(x, y, null);
                 beings.yard.set(beings.yard.stx(x-1), y, this);
                 x = beings.yard.stx(x-1);
                 done = true;
                }
                break;
            case 1:
                if (beings.free(x+1, y) && friendsNum(beings,x+1,y) > f) {
                 beings.yard.set(x, y, null);
                 beings.yard.set(beings.yard.stx(x+1), y, this);
                 x = beings.yard.stx(x+1);
                 done = true;
                }
                break;
            case 2:
                if (beings.free(x, y-1)  && friendsNum(beings,x,y-1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(x, beings.yard.sty(y-1), this);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 3: 
                if (beings.free(x, y+1)  && friendsNum(beings,x,y+1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(x, beings.yard.sty(y+1), this);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            case 4:
                if (beings.free(x-1, y-1)  && friendsNum(beings,x-1,y-1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y-1), this);
                    x = beings.yard.stx(x-1);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 5:
                if (beings.free(x+1, y-1)  && friendsNum(beings,x+1,y-1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y-1), this);
                    x = beings.yard.stx(x+1);
                    y = beings.yard.sty(y-1);
                    done = true;
                }
                break;
            case 6:
                if (beings.free(x+1, y+1)  && friendsNum(beings,x+1,y+1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x+1), beings.yard.sty(y+1), this);
                    x = beings.yard.stx(x+1);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            case 7:
                if (beings.free(x-1, y+1) && friendsNum(beings,x-1,y+1) > f) {
                    beings.yard.set(x, y, null);
                    beings.yard.set(beings.yard.stx(x-1), beings.yard.sty(y+1), this);
                    x = beings.yard.stx(x-1);
                    y = beings.yard.sty(y+1);
                    done = true;
                }
                break;
            }
        return done;
     }*/
}
