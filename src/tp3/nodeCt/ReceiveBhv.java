package tp3.nodeCt;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import tp3.Message;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Mar on 14/03/2016.
 */
public class ReceiveBhv extends Behaviour {
    NodeAgt parentAgt;

    public ReceiveBhv(NodeAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            Map list = Message.deserialisationJSON(message.getContent());
            if (list.get(Message.TYPE).equals(Message.TYPE_INSERT)) {
                // insertion
                int requestedValue = (int) list.get(Message.REQUEST);
                if (requestedValue == parentAgt.value) {

                    // TODO : faire une fonction replyMessage prenant un map en argument
                    ACLMessage reply = message.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    list.put(Message.RETURN, 0);
                    reply.setContent(Message.serialisationJSON(list));
                    parentAgt.send(reply);
                }
                else {
                    if (requestedValue < parentAgt.value) {
                        // left side
                        if (parentAgt.left == null) {
                            // empty left side
                            String nickname = "NodeAgt"+list.get(Message.REQUEST);
                            Object[] listArgs = {new Integer(list.get(Message.REQUEST).toString())};

                            AgentController agentCc = null;
                            try {
                                agentCc = parentAgt.getContainerController().createNewAgent(nickname, "tp3.nodeCt.NodeAgt", listArgs);
                                agentCc.start();
                                parentAgt.left = new AID(nickname, AID.ISLOCALNAME);
                            } catch (StaleProxyException e) {
                                e.printStackTrace();
                            }

                            ACLMessage reply = message.createReply();
                            reply.setPerformative(ACLMessage.INFORM);
                            list.put(Message.RETURN, 1);
                            reply.setContent(Message.serialisationJSON(list));
                            parentAgt.send(reply);
                        }
                        else {
                            // not empty left side

                        }
                    }
                    else {
                        // right side
                    }
                }
            }
            else if (list.get(Message.TYPE).equals(Message.TYPE_SEARCH)) {

            }
            else {

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
