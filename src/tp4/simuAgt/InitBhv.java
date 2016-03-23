package tp4.simuAgt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

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
            DFAgentDescription[] serviceList = null;
        
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Operations");
            sd.setName("AnalyseSudoku");
            template.addServices(sd);
            
            try {
                serviceList = DFService.search(parentAgt, template);
            } catch(FIPAException fe) {
                fe.printStackTrace();
            }
            
            if (serviceList.length>=27) {
                for (int i=0; i<27; i++) {
                    parentAgt.analyseAgtList[i] = serviceList[i].getName();
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
