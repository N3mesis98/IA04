package tp2.multCt;

/**
 * Created by Mar on 07/03/2016.
 */

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveBhv extends Behaviour {
    private MultAgt parentAgt;
    private int i = 0;

    public ReceiveBhv(MultAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    //@Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            //System.out.println(this.parentAgt.getLocalName()+" : "+message.getContent());
            this.parentAgt.addBehaviour(new MultBhv(this.parentAgt, message));
        }
        else {
            block();
        }
    }
    //@Override
    public boolean done() {
        return false;
    }
}
