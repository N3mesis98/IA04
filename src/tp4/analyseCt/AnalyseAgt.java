package tp4.analyseCt;

import jade.core.Agent;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;
import utilities.Services;

public class AnalyseAgt extends Agent {
    protected void setup() {
        Services.registerService(this, "Operations","AnalyseSudoku");
        //addBehaviour(new ContactBhv(this));
    }
}
