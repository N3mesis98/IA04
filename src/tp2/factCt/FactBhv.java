package tp2.factCt;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp2.multCt.MultBhv;

import java.util.Date;


public class FactBhv extends Behaviour {
	private FactAgt parentAgt;

	private ACLMessage request;

	private int requestValue;
	private int nextValue;

	private String conversationId = null;
	private boolean isDone = false;

	public FactBhv(FactAgt parentAgt, ACLMessage request){
		this.parentAgt = parentAgt;
		this.request=request;
		requestValue = new Integer(request.getContent().trim());
	}
	
	//@Override
	public void action() {
		if (conversationId == null) { // initialisation
			if (requestValue <= 1) {
				endFact(1);
			}
			else {
				sendMult(requestValue, requestValue-1);
				nextValue=requestValue-2;
			}
		}
		else {
			MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
				MessageTemplate.and(
					MessageTemplate.MatchConversationId(conversationId),
					MessageTemplate.MatchSender( new AID( "MultAgt", AID.ISLOCALNAME))
				)
			);
			ACLMessage message = this.parentAgt.receive(mt);
			if (message != null) {
				System.out.println(this.parentAgt.getLocalName()+" : "+message.getContent());
				int a = new Integer(message.getContent());
				if (nextValue <= 1 ) {
					endFact(a);
				}
				else {
					int b = nextValue;
					nextValue--;
					sendMult(a, b);
				}
			}
			else {
				block();
			}
		}
	}
	//@Override
	public boolean done() {
		return isDone;
	}

	private void endFact(int result) {
		System.out.println(requestValue+"! = "+result);
		this.isDone=true;
	}

	private void sendMult(int a, int b) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(new AID("MultAgt", AID.ISLOCALNAME));
		message.setContent(a+"*"+b);
		conversationId= ""+new Date().getTime();
		message.setConversationId(conversationId);
		this.parentAgt.send(message);
	}
}
