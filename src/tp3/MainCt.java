package tp3;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainCt {
	public static String CONF_FILE = "tp2/MainCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// main container
			Profile mainProfile = null;
			mainProfile = new ProfileImpl(CONF_FILE);
			AgentContainer mainContainer = rt.createMainContainer(mainProfile);
		}
		catch(Exception ex){
			
		}
	}
}