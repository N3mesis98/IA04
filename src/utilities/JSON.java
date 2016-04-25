package utilities;

import java.util.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class JSON {
    
    public static String serializeStringMap(Map<String, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        
        try {
            jsonString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    
    public static Map<String, String> deserializeStringMap (String serialized) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;

        try {
            map = mapper.readValue(serialized, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }



    public static String serializeStringArrayMap(Map<String, ArrayList<String> > map) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static Map<String, ArrayList<String> > deserializeStringArrayMap (String serialized) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, ArrayList<String> > map = null;

        try {
            map = mapper.readValue(serialized, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
