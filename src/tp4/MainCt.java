package tp4;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainCt {
    public static String MAIN_CONF = "tp4/MainCt.conf";

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        try{
            // main container
            Profile mainProfile = null;
            mainProfile = new ProfileImpl(MAIN_CONF);
            AgentContainer mainContainer = rt.createMainContainer(mainProfile);

            //Creation of the Environment Agent
            AgentController agentCc = mainContainer.createNewAgent("EnvAgt", "tp4.envAgt.EnvAgt", null);
            agentCc.start();

            //Creation of the Simulation Agent
            agentCc = mainContainer.createNewAgent("SimuAgt", "tp4.simuAgt.SimuAgt", null);
            agentCc.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
