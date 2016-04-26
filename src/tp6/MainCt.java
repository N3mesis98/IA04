package tp6;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainCt {
    public static String MAIN_CONF = "tp6/MainCt.conf";

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        try{
            // main container
            Profile mainProfile = null;
            mainProfile = new ProfileImpl(MAIN_CONF);
            AgentContainer mainContainer = rt.createMainContainer(mainProfile);

            //Creation of the Environment Agent
            AgentController ifAgentCc = mainContainer.createNewAgent("IfAgt", "tp6.interfaceAgt.IfAgt", null);
            ifAgentCc.start();

            AgentController kbAgentCc = mainContainer.createNewAgent("KBAgt", "tp6.kbAgt.KBAgt", null);
            kbAgentCc.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
