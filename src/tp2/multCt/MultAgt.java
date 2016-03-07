package tp2.multCt;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mar on 07/03/2016.
 */
public class MultAgt extends Agent {
    @Override
    protected void setup() {
        System.out.println(getLocalName()+" --> OK");
        addBehaviour(new ReceiveBhv(this));
    }
}
