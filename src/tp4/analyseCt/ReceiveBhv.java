package tp4.analyseCt;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp4.SudokuSubSet;
import utilities.JSON;

import java.util.Map;

/**
 * Created by Mar on 29/03/2016.
 */
public class ReceiveBhv extends CyclicBehaviour{

    private AnalyseAgt parentAgt;
    private AnalyseBhv analyseBhv = null;

    public ReceiveBhv(AnalyseAgt parentAgt) {
        this.parentAgt = parentAgt;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
        ACLMessage message = parentAgt.receive(mt);
        if (message != null) {
            if(message.getPerformative()==ACLMessage.REQUEST){
                parentAgt.simuAID = message.getSender();
                Map<String, String> map = JSON.deserializeStringMap(message.getContent());
                analyseBhv = new AnalyseBhv(parentAgt, SudokuSubSet.deserializeJSON(map.get("data")));
                parentAgt.addBehaviour(analyseBhv);
            }
            else if (analyseBhv != null) {
                parentAgt.removeBehaviour(analyseBhv);
            }
        }
        else{
            block();
        }
    }



}
