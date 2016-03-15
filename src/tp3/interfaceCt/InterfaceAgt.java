package tp3.interfaceCt;


import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ContainerController;
import tp3.Message;

/**
 * Created by Mar on 14/03/2016.
 */
public class InterfaceAgt extends Agent {

    public AID root;
    public ContainerController nodeContainer;



    @Override
    protected void setup() {
        System.out.println(getLocalName()+" --> OK");

        Object[] listArgs = getArguments();
        nodeContainer = (ContainerController) listArgs[0];

        addBehaviour(new ReceiveBhv(this));

        sendMessage(Message.TYPE_INSERT,"28");
        sendMessage(Message.TYPE_INSERT,"22");
        sendMessage(Message.TYPE_INSERT,"30");
        sendMessage(Message.TYPE_INSERT,"31");
        sendMessage(Message.TYPE_INSERT,"19");
        sendMessage(Message.TYPE_INSERT,"10");
    }

    private void sendMessage( String type, String request) {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent("{\"type\":\""+type+"\",\"request\":\""+request+"\"}");
        message.addReceiver(this.getAID());
        this.send(message);
    }

}
