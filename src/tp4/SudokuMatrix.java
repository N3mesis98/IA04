package tp4;


import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuMatrix {

    public SudokuCell[][] sudoku = new SudokuCell[9][9];

    /*public void display(){
        for (int[] i : sudoku){
            for (int j : i){
                System.out.print(j+" ");
            }
            System.out.println("\n");
        }
    }*/


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

    public SudokuSubSet getLine(int nbLine){
        return new SudokuSubSet(this.sudoku[nbLine]);
    }

    public SudokuSubSet getRow(int nbRow){
        SudokuCell[] row = new SudokuCell[9];
        for(int i=0;i<9 ;i++){
            row[i] = this.sudoku[i][nbRow];
        }
        return new SudokuSubSet(row);
    }

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

}
