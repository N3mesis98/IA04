package tp4;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainCt {
	public static String MAIN_CONF = "tp4/MainCt.conf";
	public static String INTERFACE_CONF = "tp4/interfaceCt/InterfaceCt.conf";
	public static String NODE_CONF = "tp4/nodeCt/NodeCt.conf";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try{
			// main container
			Profile mainProfile = null;
			mainProfile = new ProfileImpl(MAIN_CONF);
			AgentContainer mainContainer = rt.createMainContainer(mainProfile);



			SudokuMatrix sud = new SudokuMatrix();
			sud.importFromFile("res/grille1.txt");
			sud.display();

		}
		catch(Exception ex){
			
		}
	}


}
