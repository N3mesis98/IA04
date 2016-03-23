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

			// import matrix from file
			SudokuMatrix sudoku = new SudokuMatrix();
			sudoku.importFromFile("res/grille1.txt");
		}
		catch(Exception ex){
			
		}
	}


}
