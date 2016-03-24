package tp4.analyseCt;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.util.UUID;

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
            int nbagt;
            if (args.length > 0) {
                nbagt = new Integer(args[0]);
                if (nbagt < 1) {
                    nbagt = 1;
                }
            }
            else {
                nbagt = 1;
            }
            
            for (int i=0; i<nbagt; i++) {
                AgentController agentCc = ct.createNewAgent("AnalyseAgt-"+UUID.randomUUID().toString(), "tp4.analyseCt.AnalyseAgt", null);
                agentCc.start();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
}
