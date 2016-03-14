package tp3.nodeCt;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ReceiverBehaviour;

/**
 * Created by Mar on 14/03/2016.
 */
public class NodeAgt extends Agent {

    public AID left = null;
    public AID right = null;
    public int value ;

    @Override
    protected void setup() {
        Object[] listArgs = getArguments();
        value = (int) listArgs[0];
        addBehaviour(new ReceiveBhv(this));
    }
}
