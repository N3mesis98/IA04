package tp6.interfaceAgt;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utilities.JSON;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mar on 25/04/2016.
 */
public class ReceiveBhv extends Behaviour {

    public IfAgt parentAgt;
    public String query;
    public String conversationID;
    private boolean isDone = false;

    public ReceiveBhv(IfAgt parentAgt, String query, String conversationID) {
        this.parentAgt = parentAgt;
        this.query = query;
        this.conversationID = conversationID;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationID));
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            System.out.println("Query : \n"+query +"\nResult : \n"+message.getContent());
            isDone = true;
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return isDone;
    }
}
