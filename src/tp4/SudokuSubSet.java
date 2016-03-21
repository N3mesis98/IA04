package tp4;

import java.util.*;

/**
 * Created by Mar on 21/03/2016.
 */
public class SudokuSubSet {

    SudokuCell[] subSet = new SudokuCell[9];


    public SudokuSubSet(SudokuCell[] subSet) {
        this.subSet = subSet;
    }

    /*public void display(){
        for (int i : subSet){
            System.out.print(i+" ");
        }
        System.out.println("\n");
    }*/

    public Set<Integer> availableInt(){
        Set<Integer> list = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (SudokuCell i : subSet){
            if (i.possibilities.size() == 1) {
                list.removeAll(i.possibilities);
            }
        }
        return list;
    }

}
