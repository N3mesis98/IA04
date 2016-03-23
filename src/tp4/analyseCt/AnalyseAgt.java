package tp4.analyseCt;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;

public class AnalyseAgt extends Agent {
    protected void setup() {
        signUpDF("Operations","AnalyseSudoku");
        //addBehaviour(new ContactBhv(this));
    }
    
    private void signUpDF(String typeService, String nameSpecificService){
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(this.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeService);
        sd.setName(nameSpecificService);

        dfad.addServices(sd);

        try {
            DFService.register(this, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
