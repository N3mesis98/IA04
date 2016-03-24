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

    public int value = 0;
    public Set<Integer> possibilities =  new HashSet<>();
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

    public void intersection(Set<Integer> line, Set<Integer>  row, Set<Integer> square) {
        line.retainAll(row);
        line.retainAll(square);
        possibilities = line;
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
}
