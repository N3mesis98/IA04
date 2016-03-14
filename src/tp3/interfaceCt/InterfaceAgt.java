package tp3.interfaceCt;


import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.ContainerController;

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
    }

}
