package tp4.simuAgt;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;
import java.util.HashMap;

import tp4.SudokuMatrix;
import utilities.Services;
import utilities.JSON;

public class InitBhv extends Behaviour {
    private SimuAgt parentAgt;
    private boolean isDone = false;
    
    public InitBhv(SimuAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    public void action() {
        // attente d'une requete puis initialisation de la matrice des possibles
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = parentAgt.receive(mt);
        if (message != null) {
            Map<String, String> map = JSON.deserializeStringMap(message.getContent());
            parentAgt.sudoku = SudokuMatrix.deserializeJSON(map.get("data"));
            
            // TODO: calcul de la matrice des possibles
        }
        else {
            block();
        }
        
        // initialisation de la liste des agents d'analyse
        if (parentAgt.analyseAgtList[0]==null) {
            AID[] serviceList = Services.getAgentsByService(parentAgt, "Operations","AnalyseSudoku");
            
            if (serviceList.length>=27) {
                for (int i=0; i<27; i++) {
                    parentAgt.analyseAgtList[i] = serviceList[i];
                }
            }
        }
        
        if (parentAgt.analyseAgtList[0]!=null && parentAgt.sudoku!=null) {
            isDone = true;
            // TODO: demarre le nouveau bhv
        }
    }

    public boolean done() {
        return isDone;
    }
}
