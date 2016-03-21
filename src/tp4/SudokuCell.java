package tp4;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuCell {

    public Set<Integer> possibilities =  new HashSet<>();
    public int line;
    public int row;

    public SudokuCell(int value, int line, int row) {
        this.possibilities = new HashSet<>();
        if (value>=1 && value<=9) {
            this.possibilities.add(value);
        }
        this.line = line;
        this.row = row;
    }

    public void intersection(Set<Integer> line, Set<Integer>  row, Set<Integer> square){
        line.retainAll(row);
        line.retainAll(square);
        possibilities = line;
    }
}
