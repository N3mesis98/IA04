package tp4.analyseCt;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tp4.SudokuCell;
import tp4.SudokuSubSet;
import utilities.JSON;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mar on 29/03/2016.
 */
public class AnalyseBhv extends Behaviour {
    private SudokuSubSet subset;
    private AnalyseAgt parentAgt;
    private boolean isDone = false;
    private int currentElement = 0;

    public AnalyseBhv(AnalyseAgt parentAgt, SudokuSubSet subset) {
        this.subset = subset;
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {
        SudokuCell cell = subset.subSet[currentElement];

        if (cell.possibilities.size() == 1) {
            cell.value = cell.possibilities.toArray(new Integer[0])[0];
            for (SudokuCell curCell : subset.subSet) {
                if (curCell.possibilities.remove(cell.value)) {
                    sendCellUpdate(curCell);
                }
            }
        }

        Map<Integer, SudokuCell> map = new HashMap<>();
        for (Integer i : cell.possibilities) {
            map.put(i, null);
        }
        for (SudokuCell curCell : subset.subSet) {
            if (curCell != cell) {
                for (Integer i : curCell.possibilities) {
                    if (map.containsKey(i)) {
                        if (map.get(i) == null) {
                            map.put(i, curCell);
                        }
                        else {
                            map.remove(i);
                        }
                    }
                }
            }
        }

        for (int i : map.keySet()) {
            if (map.get(i) == null) {
                cell.value = cell.possibilities.toArray(new Integer[0])[0];
                for (SudokuCell curCell : subset.subSet) {
                    if (curCell.possibilities.remove(cell.value)) {
                        sendCellUpdate(curCell);
                    }
                }
            }
            //else {
            //
            //}
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    private void sendCellUpdate(SudokuCell cell) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(parentAgt.simuAID);
        Map<String, String> map = new HashMap<>();
        map.put("data", cell.serializeJSON());
        message.setContent(JSON.serializeStringMap(map));
        parentAgt.send(message);
    }
}
