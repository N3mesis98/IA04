package tp3.nodeCt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp3.Message;

import java.util.Map;

/**
 * Created by Mar on 14/03/2016.
 */
public class ResponseBhv extends Behaviour {
    private boolean isDone = false;
    private NodeAgt parentAgt;
    private ACLMessage requestMessage;
    private Map list;
    private String leftTree = "";
    private String rightTree = "";

    public ResponseBhv( NodeAgt parentAgt, ACLMessage requestMessage) {
        this.parentAgt = parentAgt;
        this.requestMessage = requestMessage;
        this.list = Message.deserialisationJSON(requestMessage.getContent());
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchConversationId(requestMessage.getConversationId()),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        ACLMessage message = parentAgt.receive(mt);
        if(message!=null){
            if (!list.get(Message.TYPE).equals(Message.TYPE_LIST)) {
                replyRequest(message.getContent());
            }
            else{
                Map responseList = Message.deserialisationJSON(message.getContent());
                if (message.getSender().equals(parentAgt.left)) {
                    leftTree = responseList.get(Message.RETURN).toString();
                }
                else if (message.getSender().equals(parentAgt.right)) {
                    rightTree = responseList.get(Message.RETURN).toString();
                }

                if ((parentAgt.left==null || !leftTree.equals("")) && (parentAgt.right==null || !rightTree.equals(""))) {
                    String istr = "";
                    if (parentAgt.left != null) {
                        istr+="("+leftTree+")";
                    }
                    istr+=""+parentAgt.value;
                    if (parentAgt.right != null) {
                        istr+="("+rightTree+")";
                    }
                    list.put(Message.RETURN, istr);
                    replyRequest(Message.serialisationJSON(list));
                }
            }
        }
        else{
            block();
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    private void replyRequest(String content){

        ACLMessage reply = requestMessage.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setContent(content);
        parentAgt.send(reply);
        isDone = true;

    }

}
