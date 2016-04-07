package tp4.simuAgt;

import jade.core.Agent;
import jade.core.AID;

import tp4.SudokuMatrix;
import utilities.Services;

public class SimuAgt extends Agent {
    public long tickTime = 1000;
    public AID envAID = null;
    public AID[] analyseAgtList = new AID[27];
    public SudokuMatrix sudoku = null;
    
    protected void setup() {
        Services.registerService(this, "Sudoku", "SimulationSudoku");
        addBehaviour(new InitBhv(this));
    }
}
