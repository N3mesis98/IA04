package tp4.envAgt;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp4.SudokuCell;
import utilities.JSON;

import java.util.Map;

/**
 * Created by Mar on 29/03/2016.
 */
public class ReceiveBhv extends Behaviour {
    private EnvAgt parentAgt;
    private boolean isDone = false;

    public ReceiveBhv(EnvAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = parentAgt.receive(mt);
        if (message != null) {
            Map<String, String> map = JSON.deserializeStringMap(message.getContent());
            if (map.containsKey("data")) {
                SudokuCell cell = SudokuCell.deserializeJSON(map.get("data"));
                parentAgt.sudoku.sudoku[cell.line][cell.row] =  cell;
                System.out.println("cell "+cell.line+", "+cell.row+" to "+cell.value);
            }

            if (map.containsKey("end")) {
                if (map.get("end").equals("complete")) {
                    System.out.println("\nSudoku solved\n");
                }
                else {
                    System.out.println("\nCould not solve sudoku\n");
                }
                parentAgt.sudoku.display();
                isDone = true;
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
}
