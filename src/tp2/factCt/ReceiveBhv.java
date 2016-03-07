package tp2.factCt;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveBhv extends Behaviour {
	private FactAgt parentAgt;
	
	public ReceiveBhv(FactAgt parentAgt){
		this.parentAgt = parentAgt;
	}
	
	//@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage message = this.parentAgt.receive(mt);
		if (message != null) {
			this.parentAgt.addBehaviour(new FactBhv(this.parentAgt, message));
		}
		else {
			block();
		}
	}
	//@Override
	public boolean done() {
		return false;
	}	
}
