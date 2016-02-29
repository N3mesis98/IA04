package tp2.factCt;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class FactBhv extends Behaviour {
	private Agent parentAgt;
	
	public FactBhv(Agent parentAgt){
		this.parentAgt = parentAgt;
	}
	
	//@Override
	public void action() {
		
	}
	//@Override
	public boolean done() {
		return false;
	}	
}
