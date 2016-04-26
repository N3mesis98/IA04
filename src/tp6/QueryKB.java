package tp6;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mar on 26/04/2016.
 */
public class QueryKB {

    public static void runSparQL(){
         Model model = ModelFactory.createDefaultModel();
        try {
            model.read(new FileInputStream("res/rdf/td5.n3"),null, "TURTLE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, String> prefixMap = new HashMap<>();
        prefixMap.put("rdf", null);
        prefixMap.put("rdfs", null);
        prefixMap.put("foaf", null);
        prefixMap.put("lgd", null);
        prefixMap.put("lgdo", null);

        String prefix = buildPrefixString(prefixMap, model);

        //runSelectQuery("SELECT DISTINCT ?c WHERE {?c rdf:type rdfs:Class}", model, prefix);
        //runSelectQuery("SELECT DISTINCT ?p WHERE {?p rdf:type rdf:Property}", model, prefix);
        /*runSelectQuery(
            "SELECT DISTINCT ?c WHERE {" +
                "?c rdf:type rdfs:Class ." +
                "?x rdfs:domain ?c ." +
                "?y rdfs:domain ?c ." +
                "FILTER (?x != ?y)" +
            "}",
        model, prefix);*/

        //runGeoDataQuery("SELECT * WHERE {?c a lgdo:Country ; lgdo:capital_city ?city; lgdo:wikipedia ?name .} ORDER BY ?city", prefix);

        //runSelectQuery("SELECT ?x ?y ?c WHERE {?x ?y ?c . ?c a lgdo:Country .}", model, prefix);

        // query etape 3
        // 1
        runSelectQuery("SELECT DISTINCT ?p WHERE {?s foaf:firstName \"Jean\" . ?s foaf:knows ?p .}", model, prefix);
        // 2
        runSelectQuery("SELECT DISTINCT ?p WHERE {" +
            "?s foaf:firstName \"Jean\" ." +
            "?s foaf:topic_interest ?c ." +
            "?c a lgdo:Country ." +
            "?p foaf:topic_interest ?c ." +
            "FILTER(?p != ?s)" +
        "}", model, prefix);


    }


    public static void runSelectQuery(String queryString, Model model, String prefix) {
        Query query = QueryFactory.create(prefix+" "+queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
        ResultSet r = queryExecution.execSelect();
        ResultSetFormatter.out(r);
        queryExecution.close();
    }



    public static void runGeoDataQuery(String queryString, String prefix){
        Query query = QueryFactory.create(prefix+ " " +queryString);
        System.setProperty("http.proxyHost","proxyweb.utc.fr");
        System.setProperty("http.proxyPort","3128");
        System.out.println("Query sent");
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService( "http://linkedgeodata.org/sparql",query );
        ResultSet r = queryExecution.execSelect();
        ResultSetFormatter.out(r);
        queryExecution.close();

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
