package tp6.interfaceAgt;

import jade.core.Agent;

/**
 * Created by Mar on 25/04/2016.
 */
public class IfAgt extends Agent {


    @Override
    protected void setup() {
        System.out.println(getLocalName()+" --> OK");
        addBehaviour(new ifBhv(this));
    }
}
