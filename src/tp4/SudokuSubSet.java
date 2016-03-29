package tp4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuSubSet {
    public SudokuCell[] subSet = new SudokuCell[9];

    public SudokuSubSet() {} // dummy constructor

    public SudokuSubSet(SudokuCell[] subSet) {
        this.subSet = subSet;
    }

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
