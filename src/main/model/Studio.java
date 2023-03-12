package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a studio having a collection of projects
public class Studio implements Writable {
    private String name;
    private CeramicProjectList inProgressProjects;
    private CeramicProjectList finishedProjects;

    // EFFECTS: constructs studio with a name and empty list of in-progress and finished projects
    public Studio(String name) {
        this.name = name;
        inProgressProjects = new CeramicProjectList();
        finishedProjects = new CeramicProjectList();
    }

    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: adds project to this studio
    public void addProject(CeramicProject project) {
        if (project.getNextStep().equals("NONE")) {
            finishedProjects.addProject(project);
        } else {
            inProgressProjects.addProject(project);
        }
    }

    // EFFECTS: returns an !!!(unmodifiable) list of in-progress projects in this studio
    public CeramicProjectList getInProgressProjects() {
        //return Collections.unmodifiableList(this.inProgressProjects);
        return this.inProgressProjects;
    }

    // EFFECTS: returns an !!!(unmodifiable) list of in-progress projects in this studio
    public CeramicProjectList getFinishedProjects() {
        //return Collections.unmodifiableList(this.inProgressProjects);
        return this.finishedProjects;
    }

    // EFFECTS: returns number of projects in this studio
    public int numThingies() {
        return inProgressProjects.length() + finishedProjects.length();
    }

    @Override
    // EFFECTS: !!!
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("in-progress projects", inProgressProjectsToJson());
        json.put("finished projects", finishedProjectsToJson());
        return json;
    }

    // EFFECTS: returns in-progress projects in this studio as a JSON array
    private JSONArray inProgressProjectsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < inProgressProjects.length(); i++) {
            jsonArray.put(inProgressProjects.getProjectFromIndex(i).toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns finished progress projects in this studio as a JSON array
    private JSONArray finishedProjectsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < finishedProjects.length(); i++) {
            jsonArray.put(finishedProjects.getProjectFromIndex(i).toJson());
        }
        return jsonArray;
    }
}
