package tp1.helloCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class HelloCt {
	public static String CONF_FILE = "tp1/helloCt/HelloCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// container
			Profile profile = null;
			profile = new ProfileImpl(CONF_FILE);
			AgentContainer ct = rt.createAgentContainer(profile);
			
			// agents
			AgentController helloCc = ct.createNewAgent("HelloAgt", "tp1.helloCt.HelloAgt", null);
			helloCc.start();
		}
		catch(Exception ex){
			
		}
	}
}
