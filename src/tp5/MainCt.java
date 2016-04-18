package tp5;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jdk.management.resource.ResourceContext;

import java.io.InputStream;

public class MainCt {
    public static String MAIN_CONF = "tp5/MainCt.conf";

    public static void main(String[] args) {
        /*Runtime rt = Runtime.instance();
        try{
            // main container
            Profile mainProfile = null;
            mainProfile = new ProfileImpl(MAIN_CONF);
            AgentContainer mainContainer = rt.createMainContainer(mainProfile);

            //Creation of the Environment Agent
            // AgentController agentCc = mainContainer.createNewAgent("EnvAgt", "tp4.envAgt.EnvAgt", null);
            // agentCc.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }*/

        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open("res/rdf/td5.n3");
        model.read(in, null, "TURTLE");

        //searchPersonById(model, "jean");
        //searchPersonByName(model, "Marouane");
        searchKnowsPersonByPersonId(model, "marouane");
    }



    private static void searchPersonById(Model model, String idPerson){
        String nstd5 = model.getNsPrefixURI("td5");
        Resource h = model.getResource(nstd5 + "" +idPerson );

        Selector selectTypes = new SimpleSelector(h,null,(Resource)null);
        StmtIterator iterator = model.listStatements(selectTypes) ;
        while (iterator.hasNext()) {
            Statement st = iterator.nextStatement();

            System.out.println(st.toString());
        }
    }

    private static void searchPersonByName(Model model, String name) {
        String nsfoaf = model.getNsPrefixURI("foaf");
        Property p = model.getProperty(nsfoaf + "firstName");
        Selector selectTypes = new SimpleSelector(null, p, name);
        StmtIterator iterator = model.listStatements(selectTypes) ;
        while (iterator.hasNext()) {
            Statement st = iterator.nextStatement();
            System.out.println("ID : "+st.getSubject().getLocalName());
            searchPersonById(model, st.getSubject().getLocalName());
            System.out.println();
        }
    }


    private static void searchKnowsPersonByPersonId(Model model, String idPerson) {
        //Subject
        String nstd5 = model.getNsPrefixURI("td5");
        Resource h = model.getResource(nstd5 + "" +idPerson );
        //Predicat
        String nsfoaf = model.getNsPrefixURI("foaf");
        Property p = model.getProperty(nsfoaf + "knows");

        Selector selectTypes = new SimpleSelector(h, p, (Resource)null);
        StmtIterator iterator = model.listStatements(selectTypes) ;
        while (iterator.hasNext()) {
            Statement st = iterator.nextStatement();
            System.out.println(st.getObject().toString());
        }
    }
}
