package tp2.factCt;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tp2.ComFactMult;

import java.util.Date;
import java.util.Random;


public class FactBhv extends Behaviour {
	private FactAgt parentAgt;

	private ACLMessage request;

	private int requestValue;
	private int nextValue;

	private String conversationId = null;
	private boolean isDone = false;

	private AID receiverAgt = null;

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
					MessageTemplate.MatchSender( receiverAgt)
				)
			);
			ACLMessage message = this.parentAgt.receive(mt);
			if (message != null) {
				System.out.println(this.parentAgt.getLocalName()+" : "+message.getContent());
				ComFactMult resultMult = new ComFactMult();
				resultMult.deserialisationJSONComFactMult(message.getContent());
				int a = new Integer(resultMult.getResult());
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
		receiverAgt = getReceiver("Operations", "Multiplication");
		message.addReceiver(receiverAgt);
		ComFactMult askMult = new ComFactMult(a,b);
		message.setContent(askMult.serialisationJSONComFactMult());
		conversationId= ""+new Date().getTime();
		message.setConversationId(conversationId);
		this.parentAgt.send(message);
	}





	private AID getReceiver(String typeService, String nameSpecificService) {
		DFAgentDescription[] result = null;
		DFAgentDescription template = new DFAgentDescription();

		ServiceDescription sd = new ServiceDescription();
		sd.setType(typeService);
		sd.setName(nameSpecificService);

		template.addServices(sd);
		try {
			result = DFService.search(parentAgt, template);

		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		return selectRandomReceiver(result);
	}


	private AID selectFirstReceiver(DFAgentDescription[] listPotentialReceiver){
		AID rec = null;
		if (listPotentialReceiver.length > 0)
			rec = listPotentialReceiver[0].getName();
		return rec;
	}

	/**
	 * Method that selects pseudo-randomly an Agent between the list
	 * @param listPotentialReceiver list of Agent from where an Agent has to be picked
	 * @return the AID of the Agent pseudo randomly selected
     */
	private AID selectRandomReceiver(DFAgentDescription[] listPotentialReceiver){
		AID rec = null;
		if (listPotentialReceiver.length > 0){
			Random random = new Random();
			rec = listPotentialReceiver[random.nextInt(listPotentialReceiver.length)].getName();
		}
		return rec;
	}





}
