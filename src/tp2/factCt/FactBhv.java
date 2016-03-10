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
import tp2.WaitMult;

import java.util.*;


public class FactBhv extends Behaviour {
	private FactAgt parentAgt;

	private int requestValue;
	private boolean isDone = false;
	private String idConversation;


	private List<Integer> factDecomposed;
	private List<WaitMult> waitingListMult;

	public FactBhv(FactAgt parentAgt, ACLMessage request){
		this.parentAgt = parentAgt;
		requestValue = new Integer(request.getContent().trim());
		factDecomposed = new ArrayList<>();
		waitingListMult = new ArrayList<>();
		decomposition();
		idConversation = UUID.randomUUID().toString();
	}






	@Override
	public void action() {
		int sizeFactDecomposed = factDecomposed.size();
		if(sizeFactDecomposed > 1){
			for(int i =0; i < sizeFactDecomposed/2; i++){
				//send messages
				sendMult(factDecomposed.remove(0),factDecomposed.remove(0));
			}


		} else if(sizeFactDecomposed==1 && waitingListMult.isEmpty()){
			endFact(factDecomposed.get(0));
		}




			//receive messages
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
				MessageTemplate.MatchConversationId( idConversation)
		);

		ACLMessage message = this.parentAgt.receive(mt);
		if (message != null && waitingListMult.contains(new WaitMult(message.getSender(),-1,-1))) {
			ComFactMult resultMult = new ComFactMult();
			resultMult.deserialisationJSONComFactMult(message.getContent());
			factDecomposed.add(new Integer(resultMult.getResult()));
			waitingListMult.remove(new WaitMult(message.getSender(),-1,-1));

		}
		else {
			block();
		}
	}




	@Override
	public boolean done() {
		return isDone;
	}




	private void endFact(int result) {
		System.out.println(requestValue+"! = "+result);
		this.isDone=true;
	}





	private void sendMult(int a, int b) {

		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		AID receiverAgt = getReceiver("Operations", "Multiplication");
		message.addReceiver(receiverAgt);

			//serialize message
		ComFactMult askMult = new ComFactMult(a,b);
		message.setContent(askMult.serialisationJSONComFactMult());

			//create unique conversationId
		//String conversationId= ""+new Date().getTime();
		message.setConversationId(idConversation);

			//put in the list of waiting to receive response
		waitingListMult.add(new WaitMult(receiverAgt, a,b));

		System.out.println("Send : "+a+" * "+b );
			//send message
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



	private void decomposition(){
		for(int index = 0; index < requestValue; index++){
			factDecomposed.add(requestValue-index);
		}
	}




	private void testPrint(){
		System.out.println("Size waitingListMult : "+waitingListMult.size());
		for (WaitMult w : waitingListMult){
			System.out.println("waiting : AID "+w.getIdReceiverMult() + " "+w.getTermA()+ " * "+w.getTermB() );
		}
		System.out.println("Size factDecomposed : "+factDecomposed.size());
		for (Integer f : factDecomposed){
			System.out.println("still "+f);
		}
	}
}
