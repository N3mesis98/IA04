package tp3.interfaceCt;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import tp3.Message;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Mar on 14/03/2016.
 */
public class ReceiveBhv extends Behaviour {
    InterfaceAgt parentAgt;

    public ReceiveBhv(InterfaceAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            Map list = Message.deserialisationJSON(message.getContent());

            if(parentAgt.root == null) {
                if (list.get(Message.TYPE).equals(Message.TYPE_INSERT)) { // insertion
                    AgentController agentCc = null;
                    try {
                        String nickname = "NodeAgt"+list.get(Message.REQUEST);
                        Object[] listArgs = {new Integer(list.get(Message.REQUEST).toString())};
                        agentCc = parentAgt.nodeContainer.createNewAgent(nickname, "tp3.nodeCt.NodeAgt", listArgs);
                        agentCc.start();
                        parentAgt.root = new AID(nickname, AID.ISLOCALNAME);
                        System.out.println("Insert : inserted node "+list.get(Message.REQUEST).toString());
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (list.get(Message.TYPE).equals(Message.TYPE_LIST)) {
                        System.out.println("Tree : ");
                    }
                    else if (list.get(Message.TYPE).equals(Message.TYPE_SEARCH)) {
                        System.out.println("Search : node "+list.get(Message.REQUEST).toString()+" not found");
                    }
                }
            }
            else {
                String conversationId = UUID.randomUUID().toString();
                ACLMessage newMessage = new ACLMessage(ACLMessage.REQUEST);
                newMessage.setSender(this.parentAgt.getAID());
                newMessage.clearAllReceiver();
                newMessage.addReceiver(parentAgt.root);
                newMessage.setContent(message.getContent());
                newMessage.setConversationId(conversationId);
                parentAgt.send(newMessage);

                parentAgt.addBehaviour(new ResponseBhv(parentAgt, conversationId));
            }
        }
        else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
