package tp4.envAgt;

import jade.core.Agent;

import tp4.SudokuCell;
import tp4.SudokuMatrix;
import tp4.SudokuSubSet;

import utilities.JSON;

import java.util.Map;
import java.util.HashMap;

public class EnvAgt extends Agent {
    public SudokuMatrix sudoku = new SudokuMatrix();
    
    protected void setup() {
        sudoku.importFromFile("../res/grille1.txt");
        String str = sudoku.serializeJSON();
        //addBehaviour(new ContactBhv(this));
    }
}
