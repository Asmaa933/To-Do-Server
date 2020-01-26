package help;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.*;

/**
 *
 * @author remon
 */

public class JSONHelper{

	private static final String JSON_OBJECT_TYPE = "type";


	/**
     *
     * @param type of the JSON
     * @param key the key of the JSON
     * @param value the value associated with the JSON key
     * @return JSON object
     */
    public static JSONObject getJSON(String type, String key, String value) {
        JSONObject jObj = new JSONObject();
        jObj.put(JSON_OBJECT_TYPE, type);
        jObj.put(key, value);
        return jObj;
    }

    /**
     * 
     * @param type of the JSON
     * @param m Map represents key & value pairs
     * @return JSON object
     */
    public static JSONObject getJSON(String type, Map m) {
        m.put(JSON_OBJECT_TYPE, type);
        JSONObject jo = new JSONObject(m);
        return jo;
    }

    /**
     * 
     * @param jo JSON Object
     * @return String representation of JSON Object
     */
    public static String getStringFromJSON(JSONObject jo) {
        return jo.toString();
    }

}