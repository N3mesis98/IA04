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
        if (currentElement<9) {
            SudokuCell cell = subset.subSet[currentElement];
            
            // lorsqu'une  cellule  n'a  plus  qu'une  valeur  possible,
            // celle-ci  en  devient  son contenu et la liste des possibles est vidée
            if (cell.possibilities.size() == 1) {
                cell.value = cell.possibilities.toArray(new Integer[0])[0];
                cell.possibilities.clear();
                sendCellUpdate(cell);
            }
            
            // une valeur ne se trouvant que dans une seule liste de possibles
            // est la valeur de cette cellule
            Set<Integer> set = new HashSet(cell.possibilities);
            for (SudokuCell curCell : subset.subSet) {
                if (curCell != cell) {
                    set.remove(curCell.value);
                    set.removeAll(curCell.possibilities);
                }
            }
            if (set.size() > 0) { // set.size() ne peut jamais être plus grand que 1
                cell.value = set.toArray(new Integer[0])[0];
                cell.possibilities.clear();
                sendCellUpdate(cell);
            }
            
            // si une cellule a un contenu déterminé alors il doit
            // être retiré des possibles de toutes les autres cellules non déterminées
            if (cell.value>=1 && cell.value<=9) {
                for (SudokuCell curCell : subset.subSet) {
                    if (curCell.possibilities.remove(cell.value)) {
                        sendCellUpdate(curCell);
                    }
                }
            }
            
            // si  seulement  deux  cellules  contiennent  les  deux mêmes  valeurs  possibles 
            // alors les possibles des autres cellules ne peuvent contenir ces valeurs
            
            currentElement++;
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
