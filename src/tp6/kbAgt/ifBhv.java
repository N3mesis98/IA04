package tp6.kbAgt;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by Mar on 25/04/2016.
 */
public class ifBhv extends Behaviour {
    private KBAgt parentAgt;

    public ifBhv(KBAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            ACLMessage reply = message.createReply();
            reply.setContent(runSelectQuery(message.getContent()));
            reply.setPerformative(ACLMessage.INFORM);
            parentAgt.send(reply);

        }
        else
            block();
    }

    public String runSelectQuery(String queryString) {
        Query query = QueryFactory.create(parentAgt.prefix+" "+queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, parentAgt.model);
        ResultSet r = queryExecution.execSelect();
        String resultQuery = ResultSetFormatter.asText(r);
        queryExecution.close();
        return  resultQuery;
    }


    @Override
    public boolean done() {
        return false;
    }
}
