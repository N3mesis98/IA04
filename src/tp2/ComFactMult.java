package tp2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Mar on 08/03/2016.
 */
public class ComFactMult {
    private int termA;
    private int termB;
    private int result;

    public ComFactMult(int termA, int termB) {
        this.termA = termA;
        this.termB = termB;
        this.result = -1;
    }

    public ComFactMult(int result) {
        this.result = result;
        this.termA = -1;
        this.termB = -1;
    }

    public ComFactMult(int termA, int termB, int result) {
        this.termA = termA;
        this.termB = termB;
        this.result = result;
    }

    public ComFactMult() {
    }

    public String serialisationJSONComFactMult(){
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void deserialisationJSONComFactMult(String messageContent){
        ObjectMapper mapper = new ObjectMapper();

        try {
            ComFactMult temp = mapper.readValue(messageContent,ComFactMult.class);
            this.termA = temp.termA;
            this.termB = temp.termB;
            this.result = temp.result;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    ///GETTER AND SETTER
    public int getTermA() {return termA;}

    public void setTermA(int termA) {this.termA = termA;}

    public int getTermB() {return termB;}

    public void setTermB(int termB) {this.termB = termB;}

    public int getResult() {return result;}

    public void setResult(int result) {this.result = result;}



}
