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
		//wait for a message INFORM, that contains a JSON message
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = parentAgt.receive(mt);
        if (message != null) {
            Map<String, String> map = JSON.deserializeStringMap(message.getContent());
            if (map.containsKey("data")) {//if there is the key data in it then this message is aimed to update the matrix
                SudokuCell cell = SudokuCell.deserializeJSON(map.get("data"));
                if (parentAgt.sudoku.sudoku[cell.line][cell.row].value != cell.value) {
                    parentAgt.sudoku.sudoku[cell.line][cell.row] = cell;
                    System.out.println("("+cell.line+", "+cell.row+") = "+cell.value);
                }
            }

            if (map.containsKey("end")) {//if there is the key end in it then this message is aimed to display the sudoku and display a message to know if it was solved or not
                if (map.get("end").equals("complete")) {
                    System.out.println("\nSudoku solved\n");
                }
                else {
                    System.out.println("\nCould not solve sudoku\n");
                }
                parentAgt.sudoku.display();
                isDone = true;//in this case the behaviour is stopped because the simulation ended
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
