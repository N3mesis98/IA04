package tp2.multCt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import tp2.ComFactMult;

import java.util.Date;

/**
 * Created by Mar on 07/03/2016.
 */
public class MultBhv extends WakerBehaviour {

    private ACLMessage message;
    private int term1;
    private int term2;

    private ComFactMult mult;

    @Override
    protected void onWake() {
        super.onWake();
        System.out.println("Wake");
        mult = new ComFactMult();
        mult.deserialisationJSONComFactMult(message.getContent());
        mult.setResult(mult.getTermA()*mult.getTermB());

        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setContent(mult.serialisationJSONComFactMult());

        this.myAgent.send(reply);
    }


    public MultBhv(Agent a, long timeout, ACLMessage message){
        super(a, timeout);
        this.message = message;
    }



    private void parseMessage(String messageContent) {
        String[] terms = messageContent.split("\\*");
        term1 = new Integer(terms[0].trim());
        term2 = new Integer(terms[1].trim());
    }
}
