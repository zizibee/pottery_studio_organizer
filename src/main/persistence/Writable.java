package persistence;

import org.json.JSONObject;

// Based on Writable class from JsonSerializationDemo example application

// Interface allowing implementations to return themselves as a JSON object
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
