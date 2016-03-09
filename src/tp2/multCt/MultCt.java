package tp2.multCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.event.ContainerAdapter;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by Mar on 07/03/2016.
 */
public class MultCt {

    public static String CONF_FILE = "tp2/multCt/MultCt.conf";

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        try{
            // container
            Profile profile = null;
            profile = new ProfileImpl(CONF_FILE);
            ContainerController ct = rt.createAgentContainer(profile);

            // agents
            AgentController agentCc = ct.createNewAgent("MultAgt", "tp2.multCt.MultAgt", null);
            AgentController agentCc2 = ct.createNewAgent("MultAgt2", "tp2.multCt.MultAgt", null);
            agentCc.start();
            agentCc2.start();
        }
        catch(Exception ex){

        }
    }
}
