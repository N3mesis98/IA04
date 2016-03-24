package tp4.envAgt;

import jade.core.Agent;

import java.util.Map;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;

public class EnvAgt extends Agent {
    public SudokuMatrix sudoku = new SudokuMatrix();
    
    protected void setup() {
        sudoku.importFromFile("../res/grille1.txt");
        String str = sudoku.serializeJSON();
        //addBehaviour(new ContactBhv(this));
    }
}
