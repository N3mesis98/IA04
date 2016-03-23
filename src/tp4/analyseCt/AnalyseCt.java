package tp4.analyseCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by Mar on 23/03/2016.
 */
public class AnalyseCt {
    public static String CONF_FILE = "tp4/analyseCt/AnalyseCt.conf";

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        try{
            // container
            Profile profile = null;
            profile = new ProfileImpl(CONF_FILE);
            ContainerController ct = rt.createAgentContainer(profile);

            // create n agents
            int nbagt = 1;
            try {
                nbagt = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer");
                System.exit(1);
            }

            for (int i=0; i<nbagt; i++) {
                AgentController agentCc = ct.createNewAgent("AnalyseAgt", "tp4.analyseCt.AnalyseAgt", null);
                agentCc.start();
            }
        }
        catch(Exception ex){

        }
    }
}
