package tp2.factCt;

import jade.core.Agent;

public class FactAgt extends Agent {
	
	public int currentValue = -1;
	public int nextValue;
	public int currentResult;
	
	protected void setup() {
		System.out.println(getLocalName()+" --> OK");
		
		addBehaviour(new ReceiveBhv(this));
		addBehaviour(new FactBhv(this));
	}
}
