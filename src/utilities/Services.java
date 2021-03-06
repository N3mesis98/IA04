package utilities;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class Services {
    
    public static void registerService (Agent agent, String typeService, String nameSpecificService) {
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(agent.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeService);
        sd.setName(nameSpecificService);

        dfad.addServices(sd);

        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
    
    public static AID[] getAgentsByService(Agent agent, String typeService, String nameSpecificService) {
        DFAgentDescription[] serviceList = null;
        
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typeService);
        sd.setName(nameSpecificService);
        template.addServices(sd);
        
        try {
            serviceList = DFService.search(agent, template);
        } catch(FIPAException fe) {
            fe.printStackTrace();
        }
        
        AID[] AIDList = new AID[serviceList.length];
        for (int i=0; i<serviceList.length; i++) {
            AIDList[i] = serviceList[i].getName();
        }
        
        return AIDList;
    }
}
