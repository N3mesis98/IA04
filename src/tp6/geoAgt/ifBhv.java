package tp6.geoAgt;

import com.hp.hpl.jena.query.*;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by Mar on 30/04/2016.
 */
public class ifBhv extends Behaviour {
    private GeoAgt parentAgt;

    public ifBhv(GeoAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            ACLMessage reply = message.createReply();
            reply.setContent(runGeoDataQuery(message.getContent()));
            reply.setPerformative(ACLMessage.INFORM);
            parentAgt.send(reply);

        }
        else
            block();
    }


    public String runGeoDataQuery(String queryString){
        Query query = QueryFactory.create(parentAgt.prefix+ " " +queryString);
        //TODO add this System.setProperty("http.proxyHost","proxyweb.utc.fr");
        //TODO add this System.setProperty("http.proxyPort","3128");
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService( "http://linkedgeodata.org/sparql",query );
        ResultSet r = queryExecution.execSelect();
        String resultQuery = ResultSetFormatter.asText(r);
        queryExecution.close();
        return resultQuery;
    }

    @Override
    public boolean done() {
        return false;
    }
}
