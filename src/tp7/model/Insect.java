package tp7.model;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Int2D;
import tp7.Constants;

public class Insect implements Steppable {
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
		System.out.println("init : "+mouvement);
	}

	@Override
	public void step(SimState state) {
		Beings beings = (Beings) state;

		for (int mvt=0; mvt<mouvement && currEnergy>0; mvt++) {
			move(beings, beings.random.nextInt(3)-1, beings.random.nextInt(3)-1);
		}


		/*int f = friendsNum(beings) ;
		if (f * 3 < 8) {
			if (!move2(beings))
				move2(beings);
		}*/
//		if (f * 3 < 8) {
//			tryMove(beings,f);
//		}
	}

	public void move(Beings beings, int xx, int yy) {
		x = mod((x+xx),beings.yard.getWidth());
		y = mod((y+yy),beings.yard.getHeight());
		currEnergy--;

		beings.yard.remove(this);

		// prendre en compte la charge
		if (currEnergy > 0) {
			beings.yard.setObjectLocation(this, x, y);
		}
		else {
			stop.stop();
		}
	}

	public int mod(int a, int b){
		return (((a % b) + b) % b);
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
