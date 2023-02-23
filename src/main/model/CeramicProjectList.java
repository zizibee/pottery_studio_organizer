package model;

import java.util.ArrayList;

// Represents a list of ceramic projects
public class CeramicProjectList {
    private ArrayList<CeramicProject> ceramicProjectList;

    // EFFECTS: ceramic project list is empty
    public CeramicProjectList() {
        ceramicProjectList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given project to list if its title is not used by any other project in list
    public void addProject(CeramicProject c) {
        String title = c.getTitle();
        for (int i = 0; i < ceramicProjectList.size(); i++) {
            if (ceramicProjectList.get(i).getTitle().equals(title)) {
                return;
            }
        }
        ceramicProjectList.add(c);
    }

    // MODIFIES: this
    // EFFECTS: removes project with given title from list and returns altered list
    //          if project with title does not exist, return null
    public ArrayList<CeramicProject> removeProject(String title) {
        for (int i = 0; i < ceramicProjectList.size(); i++) {
            CeramicProject c = ceramicProjectList.get(i);
            if (c.getTitle().equals(title)) {
                ceramicProjectList.remove(c);
                return ceramicProjectList;
            }
        }
        return null;
    }

    // EFFECTS: returns project in list with given title or null if it does not exist
    public CeramicProject getProject(String title) {
        for (int i = 0; i < ceramicProjectList.size(); i++) {
            CeramicProject c = ceramicProjectList.get(i);
            if (c.getTitle().equals(title)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: next step must be either "bisque fire" or "glaze fire"
    // EFFECTS: return a list of projects matching given clay type and next step
    public ArrayList<CeramicProject> groupForFiring(String clayType, String nextStep) {
        ArrayList<CeramicProject> group = new ArrayList<>();
        for (int i = 0; i < ceramicProjectList.size(); i++) {
            CeramicProject c = ceramicProjectList.get(i);
            if (c.getClayType().equals(clayType) && c.getNextStep().equals(nextStep)) {
                group.add(c);
            }
        }
        return group;
    }
}
