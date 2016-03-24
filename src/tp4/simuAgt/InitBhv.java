package tp4.simuAgt;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import utilities.Services;

public class InitBhv extends Behaviour {
    private SimuAgt parentAgt;
    private boolean isDone = false;
    
    public InitBhv(SimuAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    public void action() {
        // attente d'une request puis initialisation de la matrice des possibles
        
        // initialisation de la liste des agent d'analyse
        if (parentAgt.analyseAgtList[0]==null) {
            AID[] serviceList = Services.getAgentsByService(parentAgt, "Operations","AnalyseSudoku");
            
            if (serviceList.length>=27) {
                for (int i=0; i<27; i++) {
                    parentAgt.analyseAgtList[i] = serviceList[i];
                }
            }
        }
        
        if (parentAgt.analyseAgtList[0]!=null /*&& matrice des possibles initialisée*/) {
            isDone = true;
            // demarre le nouveau bhv
        }
    }

    public boolean done() {
        return isDone;
    }
}
