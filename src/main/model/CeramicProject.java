package model;

// Represents a ceramic project with a title, clay type, current status and next step to complete
public class CeramicProject {
    private String title;
    private String clayType;
    private String status;
    private String nextStep;

    // REQUIRES: clay type must be one of: earthenware, stoneware, or porcelain
    //           current status must be one of: greenware, bisqueware, or glazeware
    // EFFECTS: instantiate a ceramic project with a title and clay type,
    //          and set current status to greenware and next step to bisque fire
    public CeramicProject(String title, String clayType, String status) {
        this.title = title;
        this.clayType = clayType;
        this.status = status;
        String n;
        if (status.equals("greenware")) {
            n = "bisque fire";
        } else if (status.equals("bisqueware")) {
            n = "glaze fire";
        } else {
            n = "post-glaze work";
        }
        this.nextStep = n;
    }

    /*
    // EFFECTS: print out fields of project
    public void statusReport() {
        System.out.println("Project " + title + " has clay type " + clayType + " and status " + status + ".");
        System.out.println("\t Next step to complete is: " + nextStep + ".");
    } */

    // REQUIRES: next step must be either "bisque fire" or "glaze fire"
    // MODIFIES: this
    // EFFECTS: change status and next step according to initial next step
    //          if step is bisque fire, change status to bisqueware and next step to glaze fire
    //          if step is glaze fire, change status to glazeware and next step to post-glaze work
    public void update() {
        if (nextStep.equals("bisque fire")) {
            this.status = "bisqueware";
            this.nextStep = "glaze fire";
        } else if (nextStep.equals("glaze fire")) {
            this.status = "glazeware";
            this.nextStep = "post-glaze work";
        }
    }

    // MODIFIES: this
    // EFFECTS: changes next step field to completed
    public void finish() {
        this.nextStep = "COMPLETE";
    }

    public String getTitle() {
        return title;
    }

    public String getClayType() {
        return clayType;
    }

    public String getStatus() {
        return status;
    }

    public String getNextStep() {
        return nextStep;
    }
}
