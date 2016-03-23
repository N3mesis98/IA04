package tp4.simuAgt;

import jade.core.Agent;
import jade.core.AID;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;

public class SimuAgt extends Agent {
    public AID[] analyseAgtList = new AID[27];
    
    protected void setup() {
        addBehaviour(new InitBhv(this));
    }
}
