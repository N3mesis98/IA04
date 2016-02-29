package tp2.factCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class FactCt {
	public static String CONF_FILE = "tp2/factCt/FactCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// container
			Profile profile = null;
			profile = new ProfileImpl(CONF_FILE);
			AgentContainer ct = rt.createMainContainer(profile);
			
			// agents
			AgentController factCc = ct.createNewAgent("FactAgt", "tp2.factCt.FactAgt", null);
			factCc.start();
		}
		catch(Exception ex){
			
		}
	}
}
