package tp4.simuAgt;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp4.SudokuCell;
import utilities.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mar on 29/03/2016.
 */
public class SimuBhv extends Behaviour{
    private SimuAgt parentAgt;
    private TickerBhv tickerBhv;
    private boolean isDone = false;
    private boolean started = false;

    public SimuBhv(SimuAgt parentAgt, TickerBhv tickerBhv) {
        this.parentAgt = parentAgt;
        this.tickerBhv = tickerBhv;
    }

    @Override
    public void action() {
        //for the first time need to send the subset of the matrix (line, row and square)to the different AnalyseAgt
        if (!started) {
            for (int i=0; i<9; i++) {//line
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(parentAgt.analyseAgtList[i]);
                Map<String, String> map = new HashMap<>();
                map.put("data", parentAgt.sudoku.getLine(i).serializeJSON());
                message.setContent(JSON.serializeStringMap(map));
                parentAgt.send(message);
            }

            for (int i=0; i<9; i++) {//row
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(parentAgt.analyseAgtList[i+9]);
                Map<String, String> map = new HashMap<>();
                map.put("data", parentAgt.sudoku.getRow(i).serializeJSON());
                message.setContent(JSON.serializeStringMap(map));
                parentAgt.send(message);
            }

            for (int i=0; i<9; i++) {//square
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(parentAgt.analyseAgtList[i+18]);
                Map<String, String> map = new HashMap<>();
                map.put("data", parentAgt.sudoku.getSquare(i).serializeJSON());
                message.setContent(JSON.serializeStringMap(map));
                parentAgt.send(message);
            }

            started = true;
        }

        //each time a response INFORM is received , if it contains a SudokuCell update the sudoku matrix is updated and an INFORM is sent to the EnvAgt
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = parentAgt.receive(mt);
        if (message != null) {
            Map<String, String> map = JSON.deserializeStringMap(message.getContent());
            if (map.containsKey("data")) {
                SudokuCell cell = SudokuCell.deserializeJSON(map.get("data"));
                // if the value changed then update the value
                if (cell.value>=1 && cell.value<=9) {
                    parentAgt.sudoku.sudoku[cell.line][cell.row].value = cell.value;
                }
                //update the list od possible for the Cell (intersection)
                parentAgt.sudoku.sudoku[cell.line][cell.row].possibilities.retainAll(cell.possibilities);


                tickerBhv.modified = true;
                //the message is sent to the EnvAgt only if the value of the Cell is updated (not the list of possibilities)
                if (cell.value>=1 && cell.value<=9) {
                    ACLMessage messageToEnv = new ACLMessage(ACLMessage.INFORM);
                    messageToEnv.addReceiver(parentAgt.envAID);
                    messageToEnv.setContent(message.getContent());
                    parentAgt.send(messageToEnv);
                }
            }
        }
        else{
            block();
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    /**
     * Method to stop the simulation's iteration
     * Send a CANCEL to every AnalyseAgt that is performing an operation
     */
    public void stop(){
        ACLMessage message = new ACLMessage(ACLMessage.CANCEL);
        for (AID i : parentAgt.analyseAgtList) {
            message.addReceiver(i);
        }
        parentAgt.send(message);
        isDone = true;
    }
}
