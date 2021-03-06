package tp2.factCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class FactCt {
	public static String CONF_FILE = "tp2/factCt/FactCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// container
			Profile profile = null;
			profile = new ProfileImpl(CONF_FILE);
			ContainerController ct = rt.createAgentContainer(profile);
			
			// agents
			AgentController agentCc = ct.createNewAgent("FactAgt", "tp2.factCt.FactAgt", null);
			agentCc.start();
		}
		catch(Exception ex){
			
		}
	}
}
