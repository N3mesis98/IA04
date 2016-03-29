package tp4.simuAgt;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;
import java.util.HashMap;

import tp4.SudokuMatrix;
import utilities.Services;
import utilities.JSON;

import static java.lang.Thread.sleep;

public class InitBhv extends Behaviour {
    private SimuAgt parentAgt;
    private boolean isDone = false;

    public InitBhv(SimuAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    public void action() {
        // attente d'une requete puis initialisation de la matrice des possibles
        if (parentAgt.sudoku == null) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage message = parentAgt.receive(mt);
            if (message != null) {
                parentAgt.envAID = message.getSender();
                Map<String, String> map = JSON.deserializeStringMap(message.getContent());
                parentAgt.sudoku = SudokuMatrix.deserializeJSON(map.get("data"));
                parentAgt.sudoku.initialisePossibilities();
                System.out.println("Initialisation possible matrix");
                parentAgt.sudoku.displayPossibleMatrix();
            } else {
                block();
            }
        }
        
        // initialisation de la liste des agents d'analyse
        if (parentAgt.analyseAgtList[0]==null) {
            AID[] serviceList = Services.getAgentsByService(parentAgt, "Operations","AnalyseSudoku");

            if (serviceList.length > 0) {
                for (int i=0; i<serviceList.length; i++) {
                    parentAgt.analyseAgtList[i] = serviceList[i%serviceList.length];
                }
            }
            else {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (parentAgt.analyseAgtList[0]!=null && parentAgt.sudoku!=null) {
            isDone = true;
            parentAgt.sudoku.display();
            parentAgt.addBehaviour(new TickerBhv(parentAgt, parentAgt.tickTime));
        }
    }

    public boolean done() {
        return isDone;
    }
}
