package tp2.multCt;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by Mar on 07/03/2016.
 */
public class MultAgt extends Agent {
    @Override
    protected void setup() {
        System.out.println(getLocalName()+" --> OK");
        signUpDF("Operations","Multiplication");
        addBehaviour(new ReceiveBhv(this));
    }


    /**
     * Method to sign the Agt to the DF with a specific description
     * @param typeService String e.g Operation
     * @param nameSpecificService String e.g Multiplication
     */
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
