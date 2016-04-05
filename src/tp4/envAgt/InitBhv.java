package tp4.envAgt;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Map;
import java.util.HashMap;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;
import utilities.JSON;
import utilities.Services;

public class InitBhv extends Behaviour {
    private EnvAgt parentAgt;
    private boolean isDone = false;
    
    public InitBhv(EnvAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    public void action() {
		//if there is no matrix of sudoku initialized, a sudoku is read from the specified file
        if (parentAgt.sudoku == null) {
            parentAgt.sudoku = new SudokuMatrix();
            parentAgt.sudoku.importFromFile(parentAgt.SUDOKU_FILE);
        }
        
		//a call is made to the DF until there is a Simulation Agent available.
		//then the sudoku matrix is serialized in JSON, that is added into a map ("data" as a key), then the map is serialized in JSON.
		//Finally the JSON is sent to the first Simulation Agent available, in a REQUEST message
        if (parentAgt.simuAgt == null) {
            AID[] serviceList = Services.getAgentsByService(parentAgt, "Operations", "SimulationSudoku");
            if (serviceList.length > 0) {
                parentAgt.simuAgt = serviceList[0];
                
                String jsonString = parentAgt.sudoku.serializeJSON();
                Map<String, String> map = new HashMap<String, String>();
                map.put("data", jsonString);
                jsonString = JSON.serializeStringMap(map);
                
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(parentAgt.simuAgt);
                message.setContent(jsonString);
                parentAgt.send(message);
            }
        }
        //if there is a matrix initialized and a Simulation Agent that was requested to fulfill the task to resolve the sudoku
		//then a a behaviour is added to the scheduling of the agent to wait for the response and this behaviour is finished
        if (parentAgt.sudoku != null && parentAgt.simuAgt != null) {
            isDone = true;
            parentAgt.addBehaviour(new ReceiveBhv(parentAgt));
        }
    }

    public boolean done() {
        return isDone;
    }

}
