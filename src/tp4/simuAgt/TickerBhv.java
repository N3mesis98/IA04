package tp4.simuAgt;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import utilities.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mar on 29/03/2016.
 */
public class TickerBhv extends TickerBehaviour {
    private SimuAgt parentAgt;
    private SimuBhv simuBhv = null;
    public boolean modified = true;

    public TickerBhv(SimuAgt parentAgt, long period) {
        super(parentAgt, period);
        this.parentAgt=parentAgt;
    }

    /**
     * Method that is performed after a period.
     * It stops the SimuBhv and see if it is necessary to continue the simulation or not
     */
    @Override
    protected void onTick() {
        System.out.println("tick");
        
        if (simuBhv != null) {
            simuBhv.stop();
        }

        if (parentAgt.sudoku.isComplete().equals("incomplete") && modified) {//if the sudoku is incomplete and was modified then start another iteration of the simulation
            modified = false;
            simuBhv = new SimuBhv(this.parentAgt, this);
            parentAgt.addBehaviour(simuBhv);
        }
        else {//if the simulation finished then send the INFORM response to the EnvAgt, with the status of the sudoku matrix (incomplete, complete or impossible)
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(parentAgt.envAID);
            Map<String, String> map = new HashMap<>();
            map.put("end", parentAgt.sudoku.isComplete());
            message.setContent(JSON.serializeStringMap(map));
            parentAgt.send(message);
            this.stop();
        }
    }
}
