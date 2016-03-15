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
                int requestedValue = new Integer( list.get(Message.REQUEST).toString());
                if (requestedValue == parentAgt.value) {
                    list.put(Message.RETURN, "0");
                    replyMsg(message, list);
                }
                else {
                    if (requestedValue < parentAgt.value) {
                        // left side
                        if (parentAgt.left == null) {
                            // empty left side
                            AgentController agentCc = null;
                            try {
                                String nickname = "NodeAgt"+list.get(Message.REQUEST);
                                Object[] listArgs = {new Integer(list.get(Message.REQUEST).toString())};

                                agentCc = parentAgt.getContainerController().createNewAgent(nickname, "tp3.nodeCt.NodeAgt", listArgs);
                                agentCc.start();

                                parentAgt.left = new AID(nickname, AID.ISLOCALNAME);
                                list.put(Message.RETURN, "1");
                                replyMsg(message, list);
                            } catch (StaleProxyException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // not empty left side
                            ACLMessage newMessage = (ACLMessage) message.clone();
                            newMessage.setSender(this.parentAgt.getAID());
                            newMessage.clearAllReceiver();
                            newMessage.addReceiver(parentAgt.left);
                            parentAgt.send(newMessage);
                            parentAgt.addBehaviour(new ResponseBhv(parentAgt, message));

                        }
                    }
                    else {
                        // right side
                        if (parentAgt.right == null) {
                            // empty right side
                            AgentController agentCc = null;
                            try {
                                String nickname = "NodeAgt"+list.get(Message.REQUEST);
                                Object[] listArgs = {new Integer(list.get(Message.REQUEST).toString())};

                                agentCc = parentAgt.getContainerController().createNewAgent(nickname, "tp3.nodeCt.NodeAgt", listArgs);
                                agentCc.start();

                                parentAgt.right = new AID(nickname, AID.ISLOCALNAME);
                                list.put(Message.RETURN, "1");
                                replyMsg(message, list);
                            } catch (StaleProxyException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // not empty right side
                            ACLMessage newMessage = (ACLMessage) message.clone();
                            newMessage.setSender(this.parentAgt.getAID());
                            newMessage.clearAllReceiver();
                            newMessage.addReceiver(parentAgt.right);
                            parentAgt.send(newMessage);
                            parentAgt.addBehaviour(new ResponseBhv(parentAgt, message));

                        }
                    }
                }
            }
            else if (list.get(Message.TYPE).equals(Message.TYPE_SEARCH)) {
                //search
                int requestedValue = new Integer( list.get(Message.REQUEST).toString());
                if (requestedValue == parentAgt.value) {
                    list.put(Message.RETURN, "1");
                    replyMsg(message, list);
                }
                else {
                    if (requestedValue < parentAgt.value) {
                        // left side
                        if (parentAgt.left == null) {
                            // empty left side
                            list.put(Message.RETURN, "0");
                            replyMsg(message, list);
                        } else {
                            // not empty left side
                            ACLMessage newMessage = (ACLMessage) message.clone();
                            newMessage.setSender(this.parentAgt.getAID());
                            newMessage.clearAllReceiver();
                            newMessage.addReceiver(parentAgt.left);
                            parentAgt.send(newMessage);
                            parentAgt.addBehaviour(new ResponseBhv(parentAgt, message));
                        }
                    } else {
                        // right side
                        if (parentAgt.right == null) {
                            // empty right side
                            list.put(Message.RETURN, "0");
                            replyMsg(message, list);
                        } else {
                            // not empty right side
                            ACLMessage newMessage = (ACLMessage) message.clone();
                            newMessage.setSender(this.parentAgt.getAID());
                            newMessage.clearAllReceiver();
                            newMessage.addReceiver(parentAgt.right);
                            parentAgt.send(newMessage);
                            parentAgt.addBehaviour(new ResponseBhv(parentAgt, message));
                        }
                    }
                }
            }
            else {
                //list
                if(parentAgt.left==null && parentAgt.right==null){ //leaf
                    list.put(Message.RETURN, ""+parentAgt.value);
                    replyMsg(message, list);
                }
                else {
                    if (parentAgt.left!=null){
                        ACLMessage newMessage = (ACLMessage) message.clone();
                        newMessage.setSender(this.parentAgt.getAID());
                        newMessage.clearAllReceiver();
                        newMessage.addReceiver(parentAgt.left);
                        parentAgt.send(newMessage);
                    }
                    if (parentAgt.right!=null){
                        ACLMessage newMessage = (ACLMessage) message.clone();
                        newMessage.setSender(this.parentAgt.getAID());
                        newMessage.clearAllReceiver();
                        newMessage.addReceiver(parentAgt.right);
                        parentAgt.send(newMessage);
                    }
                    parentAgt.addBehaviour(new ResponseBhv(parentAgt, message));
                }
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


    private void replyMsg(ACLMessage message, Map listContent){

        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setContent(Message.serialisationJSON(listContent));
        parentAgt.send(reply);

    }
}
