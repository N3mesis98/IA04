package tp4.analyseCt;

import jade.core.AID;
import jade.core.Agent;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;
import tp4.analyseCt.ReceiveBhv;
import tp4.simuAgt.SimuAgt;
import utilities.Services;

public class AnalyseAgt extends Agent {
    public AID simuAID = null;

    protected void setup() {
        Services.registerService(this, "Operations","AnalyseSudoku");
        addBehaviour(new ReceiveBhv(this));
    }
}
