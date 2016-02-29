package tp1.helloCt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ContactBhv extends Behaviour {
	private Agent helloAgt;
	
	public ContactBhv(Agent helloAgt){
		this.helloAgt = helloAgt;
	}
	
	//@Override
	public void action() {
		ACLMessage message = this.helloAgt.receive();
		if (message != null) {
			System.out.println(this.helloAgt.getLocalName()+" : "+message.getContent());
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
