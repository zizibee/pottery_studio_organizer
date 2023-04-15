package persistence;

import org.json.JSONObject;

// Interface allowing implementations to return themselves as a JSON object
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
