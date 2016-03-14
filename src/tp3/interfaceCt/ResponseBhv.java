package tp3.interfaceCt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp3.Message;

import java.util.Map;

/**
 * Created by Mar on 14/03/2016.
 */
public class ResponseBhv extends Behaviour{

    InterfaceAgt parentAgt;
    String conversationId;
    boolean isDone = false;

    public ResponseBhv(InterfaceAgt parentAgt, String conversationId) {
        this.parentAgt = parentAgt;
        this.conversationId = conversationId;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationId));
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            Map list = Message.deserialisationJSON(message.getContent());
            if (list.get(Message.TYPE).equals(Message.TYPE_LIST)) {
                System.out.println("Tree : "+list.get(Message.RETURN));
            }
            else if (list.get(Message.TYPE).equals(Message.TYPE_INSERT)) {
                if (list.get(Message.RETURN).equals("0")) {
                    System.out.println("Insert : failed to insert node " + list.get(Message.REQUEST));
                }
                else {
                    System.out.println("Insert : inserted node " + list.get(Message.REQUEST));
                }
            }
            else {
                if (list.get(Message.RETURN).equals("1")) {
                    System.out.println("Search : found node " + list.get(Message.REQUEST));
                }
                else {
                    System.out.println("Search : node " + list.get(Message.REQUEST) + " not found");
                }
            }
            isDone = true;
        }
        else {
            block();
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }
}
