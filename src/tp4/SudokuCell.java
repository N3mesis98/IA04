package tp4;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

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
public class SudokuCell {

    public int value = 0;// value of the Cell
    public Set<Integer> possibilities =  new HashSet<>();//Values possible for the Cell
    public int line;
    public int row;


    public SudokuCell(int value, int line, int row) {
        this.possibilities = new HashSet<>();
        if (value>=1 && value<=9) {
            this.value = value;
        }
        this.line = line;
        this.row = row;
    }
    
    public SudokuCell() {} // dummy constructor

    /**
     * Method to make the intersection between possible values for the line, the row and the square the Cell belongs to.
     * The intersection is done only if the Cell has no value (which corresponds to 0)
     * @param line Set<Integer> values possible of its line
     * @param row Set<Integer> values possible of its row
     * @param square Set<Integer> values possible of its square
     */
    public void intersection(Set<Integer> line, Set<Integer>  row, Set<Integer> square) {
        if (value<1 || value>9) {
            line.retainAll(row);
            line.retainAll(square);
            possibilities = line;
        }
    }

    /**
     * Method to serialize the cell into a String
     * @return
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
     * Method to get a serialized Cell from a String
     * @param serialized
     * @return
     */
    public static SudokuCell deserializeJSON (String serialized) {
        ObjectMapper mapper = new ObjectMapper();
        SudokuCell cell = null;

        try {
            cell = mapper.readValue(serialized, SudokuCell.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cell;
    }

    /**
     * Method to display the possible of a Cell
     * @return String containing the value and its possible values
     */
    public String possibleValues(){
        int nbElement = 9-this.possibilities.size();
        String result = "";
        result = result+""+this.value+" (";
        for(int i : this.possibilities){
            result = result+i;
        }
        for(int i = 0; i< nbElement ;i++){
            result = result+".";
        }
        result = result+")";
        return result;

    }
}
