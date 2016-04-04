package tp4;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuMatrix {

    public SudokuCell[][] sudoku = new SudokuCell[9][9];

    /**
     * Method to import the matrix from a file.
     * @param path Path of the file that contains the matrix
     */
    public void importFromFile(String path){
        try {
            List<String> readSudoku = Files.readAllLines(FileSystems.getDefault().getPath(path));
            String[] splitedLine;
            for (int i=0; i<9; i++){
                splitedLine = readSudoku.get(i).split(" ");
                for (int j=0; j<9; j++) {
                    Integer value = new Integer(splitedLine[j]);
                    sudoku[i][j] = new SudokuCell(value, i, j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get SubSet of the matrix corresponding to a line.
     * @param nbLine index ot the line (from 0 to 8)
     * @return line
     */
    public SudokuSubSet getLine(int nbLine){
        return new SudokuSubSet(this.sudoku[nbLine]);
    }

    /**
     * Method to get SubSet of the matrix corresponding to a row.
     * @param nbRow index ot the row (from 0 to 8)
     * @return row
     */
    public SudokuSubSet getRow(int nbRow){
        SudokuCell[] row = new SudokuCell[9];
        for(int i=0;i<9 ;i++){
            row[i] = this.sudoku[i][nbRow];
        }
        return new SudokuSubSet(row);
    }

    /**
     * Method to get SubSet of the matrix corresponding to a square.
     * @param nbSquare index ot the square (from 0 to 8)
     * @return square
     */
    public SudokuSubSet getSquare(int nbSquare){
        SudokuCell[] square = new SudokuCell[9];
        int firstLine = (nbSquare/3)*3;
        int firstRow = (nbSquare%3)*3;
        int index = 0;
        for(int i=firstLine; i < firstLine + 3 ;i++){
            for(int j=firstRow; j < firstRow + 3 ;j++){
                square[index] = this.sudoku[i][j];
                index++;
            }
        }
        return new SudokuSubSet(square);
    }

    /**
     * Method to determine for the first time the possible values of each Cell.
     * It depends on the possible values of the line, rox and square for each Cell.
     */
    public void initialisePossibilities() {
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                Set<Integer> linep = this.getLine(i).availableInt();
                Set<Integer> rowp = this.getRow(j).availableInt();
                Set<Integer> squarep = this.getSquare((i/3)*3 + j/3).availableInt();
                this.sudoku[i][j].intersection(linep, rowp, squarep);
            }
        }
    }

    public void display(){
        for(int i=0; i < sudoku.length ; i++){
            for(int j=0; j < sudoku[i].length ; j++){
                System.out.print(sudoku[i][j].value+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public void displayPossibleMatrix(){
        for(int i=0; i < sudoku.length ; i++){
            for(int j=0; j < sudoku[i].length ; j++){
                System.out.print(sudoku[i][j].possibleValues()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public String serializeJSON () {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    
    public static SudokuMatrix deserializeJSON (String serialized) {
        ObjectMapper mapper = new ObjectMapper();
        SudokuMatrix sudoku = null;

        try {
            sudoku = mapper.readValue(serialized, SudokuMatrix.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sudoku;
    }

    /**
     * Method to determine if the matrix is incomplete, complete or impossible
     * @return String corresponding to the status of the matrix : incomplete, complete or impossible
     */
    public String isComplete() {
        for (int i=0; i<sudoku.length; i++) {
            for (SudokuCell cell : sudoku[i]) {
                if (cell.value<1 || cell.value>9) {
                    if (cell.possibilities.size() == 0) {
                        return "impossible";
                    }
                    else {
                        return "incomplete";
                    }
                }
            }
        }
        return "complete";
    }

}
