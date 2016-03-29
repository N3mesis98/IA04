package tp4.envAgt;

import jade.core.Agent;
import jade.core.AID;

import tp4.SudokuMatrix;

public class EnvAgt extends Agent {
    public String SUDOKU_FILE = "res/grille1.txt";
    public SudokuMatrix sudoku = null;
    public AID simuAgt = null;
    
    protected void setup() {
        addBehaviour(new InitBhv(this));
    }
}
