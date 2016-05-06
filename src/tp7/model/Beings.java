package tp7.model;

import sim.engine.SimState;
import sim.engine.Stoppable;
//import sim.field.grid.ObjectGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Int2D;
import tp7.Constants;

public class Beings extends SimState {

    public int numInsects = Constants.NUM_INSECT;
    //public ObjectGrid2D yard = new ObjectGrid2D(Constants.GRID_SIZE,Constants.GRID_SIZE);
    public SparseGrid2D yard = new SparseGrid2D(Constants.GRID_SIZE,Constants.GRID_SIZE);
    public Beings(long seed) {
        super(seed);
    }

    public void start() {
        System.out.println("Simulation started");
        super.start();
        yard.clear();
        addInsect();
        addFood();
        
        // specific insects
        addSpecificInsect(0, Constants.CAPACITY, 0, "Gonzalez");
    }
    
    
    private void addSpecificInsect(int charge, int mouvement, int perception, String name) {
        int x = random.nextInt(yard.getWidth());
        int y = random.nextInt(yard.getHeight());
        Insect  insect  =  new  Insect(x, y, charge, mouvement, perception, name);
        yard.setObjectLocation(insect, x,y);
        Stoppable s = schedule.scheduleRepeating(insect);
        insect.stop = s;
    }

    private void addInsect() {
        for(int  i  =  0;  i  <  numInsects;  i++) {
            int x = random.nextInt(yard.getWidth());
            int y = random.nextInt(yard.getHeight());
            int charge = 0;
            int mouvement = 0;
            int perception = 0;
            for (int j = 0; j <Constants.CAPACITY; j++){
                int r = random.nextInt(3);
                if (r ==0){
                    if(charge < Constants.MAX_LOAD)
                        charge++;
                    else
                        j--;
                } else if (r==1){
                    mouvement++;
                } else {
                    perception++;
                }
            }

            Insect  insect  =  new  Insect(x, y, charge, mouvement,perception);
            yard.setObjectLocation(insect, x,y);
            Stoppable s = schedule.scheduleRepeating(insect);
            insect.stop = s;
        }
    }

    private void addFood() {
        for(int  i  =  0;  i  <  Constants.NUM_FOOD_CELL;  i++) {
            int x = random.nextInt(yard.getWidth());
            int y = random.nextInt(yard.getHeight());
            Food food  =  new Food(x,y);
            yard.setObjectLocation(food,x, y);
            schedule.scheduleRepeating(food);
        }
    }

    public int getNumInsects() {
        return numInsects;
    }

    public void setNumInsects(int numInsects) {
        this.numInsects = numInsects;
    }
/*  public boolean free(int x,int y) {
        int xx = yard.stx(x);
        int yy = yard.sty(y);
        return yard.get(xx,yy) == null;
    }*/


    /*private Int2D getFreeLocation() {
      Int2D location = new Int2D(random.nextInt(yard.getWidth()),
               random.nextInt(yard.getHeight()) );
      Object ag;
      while ((ag = yard.get(location.x,location.y)) != null) {
          location = new Int2D(random.nextInt(yard.getWidth()),
                   random.nextInt(yard.getHeight()) );
      }
      return location;
    }*/


  //public  int  getNumStudents()  {  return  Constants.NUM_INSECT;  }
}
