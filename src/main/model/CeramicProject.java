package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a ceramic project with a title, clay type, clay stage and next step to complete
public class CeramicProject implements Writable {
    private final String title;
    private final String clayType;
    private String stage;
    private String nextStep;

    // REQUIRES: clay type must be one of: earthenware, stoneware, or porcelain
    //           current stage must be one of: greenware, bisqueware, or glazeware
    // EFFECTS: instantiate a ceramic project with a title and clay type,
    //          and set current stage to greenware and next step to bisque fire
    public CeramicProject(String title, String clayType, String stage, String nextStep) {
        this.title = title;
        this.clayType = clayType;
        this.stage = stage;
        if (nextStep == null) {
            String n;
            if (stage.equals("greenware")) {
                n = "bisque fire";
            } else if (stage.equals("bisqueware")) {
                n = "glaze fire";
            } else {
                n = "post-glaze work";
            }
            this.nextStep = n;
        } else {
            this.nextStep = nextStep;
        }
    }

    // MODIFIES: this
    // EFFECTS: change stage and next step according to initial next step
    //          if step is bisque fire, change stage to bisqueware and next step to glaze fire
    //          if step is glaze fire, change stage to glazeware and next step to post-glaze work
    //          if step is post-glaze work, change next step to none and finish
    public void update() {
        if (nextStep.equals("bisque fire")) {
            this.stage = "bisqueware";
            this.nextStep = "glaze fire";
        } else if (nextStep.equals("glaze fire")) {
            this.stage = "glazeware";
            this.nextStep = "post-glaze work";
        } else {
            finish();
        }
    }

    // MODIFIES: this
    // EFFECTS: changes next step field to completed
    public void finish() {
        this.nextStep = "NONE";
    }

    public String getTitle() {
        return title;
    }

    public String getClayType() {
        return clayType;
    }

    public String getStage() {
        return stage;
    }

    public String getNextStep() {
        return nextStep;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("clay type", this.clayType);
        json.put("stage", this.getStage());
        json.put("next step", this.getNextStep());
        return json;
    }
}
