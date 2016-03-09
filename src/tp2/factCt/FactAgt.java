package tp2.factCt;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class FactAgt extends Agent {
	
	protected void setup() {
		System.out.println(getLocalName()+" --> OK");
		
		addBehaviour(new ReceiveBhv(this));
	}



}
