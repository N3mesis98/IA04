package tp4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuSubSet {
    public SudokuCell[] subSet = new SudokuCell[9];//a subset of the global matrix. It can be either a line, a row or a square

    public SudokuSubSet() {} // dummy constructor

    public SudokuSubSet(SudokuCell[] subSet) {
        this.subSet = subSet;
    }

    /**
     * Method to determine all the values possible of the SubSet.
     * It goes through each Cell and if there is already a value associated to it, it removes it from the possible values
     * @return Set of possible Integer for the SubSet
     */
    public Set<Integer> availableInt(){
        Set<Integer> list = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (SudokuCell i : subSet){
            if (i.value != 0) {
                list.remove(i.value);
            }
        }
        return list;
    }


    public void displayPossibleSubSet(){
        for (SudokuCell i : subSet){
            System.out.print(i.possibleValues()+" ");
        }
        System.out.println();
    }


    /**
     * Method to serialize the SubSet of Cell into JSON format
     * @return JSON String
     */
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

    /**
     * Method to get a Subset of cell from JSON String
     * @param serialized JSON String
     * @return SudokuSubSet
     */
    public static SudokuSubSet deserializeJSON (String serialized) {
        ObjectMapper mapper = new ObjectMapper();
        SudokuSubSet sudoku = null;

        try {
            sudoku = mapper.readValue(serialized, SudokuSubSet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sudoku;
    }
}
