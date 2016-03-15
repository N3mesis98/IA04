package tp3;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.domain.introspection.AMSSubscriber;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainCt {
	public static String MAIN_CONF = "tp3/MainCt.conf";
	public static String INTERFACE_CONF = "tp3/interfaceCt/InterfaceCt.conf";
	public static String NODE_CONF = "tp3/nodeCt/NodeCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// main container
			Profile mainProfile = null;
			mainProfile = new ProfileImpl(MAIN_CONF);
			AgentContainer mainContainer = rt.createMainContainer(mainProfile);

			// node container
			Profile profile1 = null;
			profile1 = new ProfileImpl(NODE_CONF);
			ContainerController nodeCt = rt.createAgentContainer(profile1);

			// interface container
			Profile profile2 = null;
			profile2 = new ProfileImpl(INTERFACE_CONF);
			ContainerController interfaceCt = rt.createAgentContainer(profile2);

			Object[] listArgs = {nodeCt};
			AgentController agentCc = interfaceCt.createNewAgent("InterfaceAgt", "tp3.interfaceCt.InterfaceAgt",listArgs);
			agentCc.start();


		}
		catch(Exception ex){
			
		}
	}


}
