package tp6.interfaceAgt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utilities.JSON;
import utilities.Services;

import java.util.Map;

/**
 * Created by Mar on 25/04/2016.
 */
public class ifBhv extends Behaviour{
    public IfAgt parentAgt;

    public ifBhv(IfAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            Map<String,String> map = JSON.deserializeStringMap(message.getContent());
            String query ="";
            switch(map.get("typeQuery")){
                case "1" :
                    query = "SELECT DISTINCT ?p WHERE {?s foaf:firstName \" "+ map.get("name")+" \" . ?s foaf:knows ?p .}";
                    break;

                case "2" :
                    query = "SELECT DISTINCT ?p WHERE {" +
                            "?s foaf:firstName "+map.get("name") +"." +
                            "?s foaf:topic_interest ?c ." +
                            "?c a lgdo:Country ." +
                            "?p foaf:topic_interest ?c ." +
                            "FILTER(?p != ?s)" +
                            "}";
                    break;
                default :
                    break;
            }

            sendMsg(query);
        }

    }


    public void sendMsg(String query){
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(Services.getAgentsByService(parentAgt,"KB","KB").clone()[0]);
        message.setContent(query);
        parentAgt.send(message);
    }
    @Override
    public boolean done() {
        return false;
    }
}
