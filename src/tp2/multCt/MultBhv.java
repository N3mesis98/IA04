package tp2.multCt;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tp2.ComFactMult;

/**
 * Created by Mar on 07/03/2016.
 */
public class MultBhv extends Behaviour {
    private MultAgt parentAgt;
    private ACLMessage message;
    private int term1;
    private int term2;

    private ComFactMult mult;

    public MultBhv(MultAgt parentAgt, ACLMessage message){
        this.parentAgt = parentAgt;
        this.message = message;
    }

    //@Override
    public void action() {
        //parseMessage(message.getContent());
        //Integer result = term1 * term2;
        mult = new ComFactMult();
        mult.deserialisationJSONComFactMult(message.getContent());
        mult.setResult(mult.getTermA()*mult.getTermB());

        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setContent(mult.serialisationJSONComFactMult());

        this.parentAgt.send(reply);
    }
    //@Override
    public boolean done() {
        return true;
    }

    private void parseMessage(String messageContent) {
        String[] terms = messageContent.split("\\*");
        term1 = new Integer(terms[0].trim());
        term2 = new Integer(terms[1].trim());
    }
}
