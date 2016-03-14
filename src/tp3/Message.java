package tp3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Mar on 14/03/2016.
 */
public class Message {
/* type : insert, search, list
    request : int
    return : int or String
* */

    public final static String TYPE = "type";
    public final static String TYPE_INSERT = "insert";
    public final static String TYPE_SEARCH = "search";
    public final static String TYPE_LIST = "list";
    public final static String REQUEST = "request";//question
    public final static String RETURN = "return";// result value



    public static String serialisationJSON(Map message){
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Map deserialisationJSON(String messageContent){
        ObjectMapper mapper = new ObjectMapper();
        Map temp = null;
        try {
            temp = mapper.readValue(messageContent,Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
