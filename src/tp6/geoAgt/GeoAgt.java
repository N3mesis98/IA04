package tp6.geoAgt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import jade.core.Agent;
import utilities.Services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mar on 30/04/2016.
 */
public class GeoAgt extends Agent {
    public Model model;
    public String prefix;
    protected void setup() {
        System.out.println(getLocalName()+" --> OK");
        Services.registerService(this,"KB","Geo");

        model = ModelFactory.createDefaultModel();
        try {
            model.read(new FileInputStream("res/rdf/td5.n3"),null, "TURTLE");
            Map<String, String> prefixMap = new HashMap<>();
            prefixMap.put("rdf", null);
            prefixMap.put("rdfs", null);
            prefixMap.put("foaf", null);
            prefixMap.put("lgd", "<http://linkedgeodata.org/>");
            prefixMap.put("lgdo", "<http://linkedgeodata.org/ontology/>");

            prefix = buildPrefixString(prefixMap, model);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addBehaviour(new ifBhv(this));
    }

    public static String buildPrefixString(Map<String, String> names, Model model) {
        String str = "";
        String prefixURI;
        for (String key : names.keySet()) {
            if (names.get(key) == null) {
                prefixURI = "<"+model.getNsPrefixURI(key)+">";
            }
            else {
                prefixURI = names.get(key);
            }
            str+= "PREFIX "+key+": "+prefixURI+"\n";
        }
        return str;
    }
}
