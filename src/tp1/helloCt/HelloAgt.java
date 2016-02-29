package tp1.helloCt;

import jade.core.Agent;

public class HelloAgt extends Agent {
	protected void setup() {
		System.out.println(getLocalName()+" --> OK");
		
		addBehaviour(new ContactBhv(this));
	}
}
