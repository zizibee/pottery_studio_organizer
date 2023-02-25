package ui;

import model.CeramicProject;
import model.CeramicProjectList;

import java.util.ArrayList;
import java.util.Scanner;

public class StudioApp {
    private CeramicProjectList inProgressProjects;
    private CeramicProjectList finishedProjects;
    private Scanner input;

    // EFFECTS: run the studio application
    public StudioApp() {
        runStudio();
    }

    // MODIFIES: this
    // EFFECTS: print menu and keep studio application running as long as user input is not "q"
    //          if q is selected, end application and print goodbye message
    private void runStudio() {
        boolean keepRunning = true;
        String userInput;

        init();
        System.out.println("\nWelcome to your pottery studio organizer!");

        while (keepRunning) {
            printMenu();
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("q")) {
                keepRunning = false;
            } else {
                processInput(userInput);
            }
        }
        System.out.println("\nExiting studio...");
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: calls appropriate methods for user input based on menu legend
    private void processInput(String userInput) {
        switch (userInput) {
            case "a":
                addProject();
                break;
            case "r":
                removeProject();
                break;
            case "s":
                statusReport();
                break;
            case "g":
                groupFiringSuggestion();
                break;
            case "u":
                updateProject();
                break;
            case "v":
                viewProjects();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize in-progress and finished project lists and input
    private void init() {
        inProgressProjects = new CeramicProjectList();
        finishedProjects = new CeramicProjectList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: prints user input options
    private void printMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add project");
        System.out.println("\tr -> remove a project");
        System.out.println("\ts -> receive status report for a project");
        System.out.println("\tg -> receive project firing grouping suggestion");
        System.out.println("\tu -> update a project");
        System.out.println("\tv -> view a project collection");
        System.out.println("\tq -> quit studio organizer");
    }

    // MODIFIES: this
    // EFFECTS: create and add new project to chosen list and print confirmation message,
    //          if project cannot be added, print message to indicate failure
    private void addProject() {
        String title = enterTitle();

        String selectedClayType = selectClayType();
        String selectedStatus = selectStatus();

        CeramicProject newProject = new CeramicProject(title, selectedClayType, selectedStatus);
        CeramicProjectList selectedList = selectList();
        String listMessage;
        if (selectedList == inProgressProjects) {
            listMessage = "in-progress";
        } else {
            listMessage = "finished";
        }

        int initialLength = selectedList.length();
        selectedList.addProject(newProject);

        if (selectedList.length() == initialLength) {
            System.out.println("New project not added: a project with title \"" + title
                    + "\" already exists in the " + listMessage + " project collection.");
        } else {
            System.out.println("Project \"" + title + "\" has been added to the " + listMessage
                    + " project collection.");
            if (selectedList == finishedProjects) {
                newProject.finish();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allow title to be entered by user and remove project from chosen list and print confirmation message,
    //          if project cannot be removed, print failure message
    private void removeProject() {
        CeramicProjectList selectedList = selectList();
        String listMessage;
        if (selectedList == inProgressProjects) {
            listMessage = "in-progress";
        } else {
            listMessage = "finished";
        }
        String title = enterTitle();

        ArrayList<CeramicProject> editedList = selectedList.removeProject(title);
        if (editedList == null) {
            System.out.println("Project not removed: project with title \"" + title
                    + "\" does not exist in the " + listMessage + " project collection.");
        } else {
            System.out.println("Project \"" + title + "\" has been removed from the " + listMessage
                    + " project collection.");
        }
    }

    // EFFECTS: allow title to be entered by user, print status report of project and return project,
    //          if project cannot be found, print failure message
    private void statusReport() {
        String title = enterTitle();

        CeramicProject found = inProgressProjects.getProjectFromTitle(title);
        if (found == null) {
            found = finishedProjects.getProjectFromTitle(title);
            if (found == null) {
                System.out.println("Project not found.");
                return;
            }
        }
        printProjectReport(found);
    }

    // EFFECTS: allow user to select clay type and next step, group projects in in-progress list matching chosen fields,
    //          and print list of grouped projects, if none print indicating message
    private void groupFiringSuggestion() {
        String selectedClayType = selectClayType();
        String selectedNextStep = selectNextStep();

        ArrayList<CeramicProject> firingGroup = inProgressProjects.groupForFiring(selectedClayType, selectedNextStep);

        System.out.println("\nIn-progress " + selectedClayType + " projects that can be "
                + selectedNextStep + "d together:");
        if (firingGroup.size() == 0) {
            System.out.println("\tNONE");
            return;
        }
        for (CeramicProject ceramicProject : firingGroup) {
            System.out.println("\t- " + ceramicProject.getTitle());
        }
    }

    // MODIFIES: this
    // EFFECTS: allow user to input project title - print failure message and return if project not found,
    //          allow user to select action: if c, update project and print status report
    //                                       if f, finish project and move to finished list, remove from in-progress
    private void updateProject() {
        String title = enterTitle();

        CeramicProject found = inProgressProjects.getProjectFromTitle(title);
        if (found == null) {
            System.out.println("Project not found in in-progress project collection.");
            return;
        }
        printProjectReport(found);

        String selectedAction = selectAction();
        if (selectedAction.equals("c")) {
            found.update();
        } else if (selectedAction.equals("f")) {
            found.finish();
        }

        if (found.getNextStep().equals("NONE - FINISHED")) {
            finishedProjects.addProject(found);
            inProgressProjects.removeProject(found.getTitle());
        }
        printProjectReport(found);
    }

    // EFFECTS: allow user to select either c, f, or m and return selection
    private String selectAction() {
        String selection = "";
        System.out.println("\nSelect an action:");

        while (!(selection.equals("c") || selection.equals("f") || selection.equals("m"))) {
            System.out.print("\tc -> complete next step");
            System.out.print("\n\tf -> move project to finished collection");
            System.out.print("\n\tm -> return to menu\n");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: allow user to select either finished or in-progress list, print status reports for every project in list
    private void viewProjects() {
        CeramicProjectList selected = selectList();
        String message;
        if (selected == inProgressProjects) {
            message = "In-progress";
        } else {
            message = "Finished";
        }
        System.out.println("\n" + message + " projects:");
        if (selected.length() == 0) {
            System.out.println("\tNONE");
            return;
        }
        for (int i = 0; i < selected.length(); i++) {
            printProjectReport(selected.getProjectFromIndex(i));
        }
    }

    // EFFECTS: allow user to select either i for in-progress projects or f for finished projects, return chosen list
    private CeramicProjectList selectList() {
        System.out.println("Choose a project collection:");
        String selection = "";

        while (!(selection.equals("i") || selection.equals("f"))) {
            System.out.print("\ti -> in-progress project collection");
            System.out.print("\n\tf -> finished project collection\n");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("i")) {
            return inProgressProjects;
        } else {
            return finishedProjects;
        }
    }

    private String enterTitle() {
        System.out.print("Enter the title of this new project: ");
        return input.next();
    }

    // EFFECTS: allow user to select either e - return "earthenware", s - return "stoneware", or p - return "porcelain"
    private String selectClayType() {
        String selection = "";
        System.out.println("\nSelect a clay type:");

        while (!(selection.equals("e") || selection.equals("s") || selection.equals("p"))) {
            System.out.print("\te -> earthenware");
            System.out.print("\n\ts -> stoneware");
            System.out.print("\n\tp -> porcelain\n");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("e")) {
            return "earthenware";
        } else if (selection.equals("s")) {
            return "stoneware";
        } else {
            return "porcelain";
        }
    }

    // EFFECTS: allow user to select either g - return "greenware", b - "bisqueware", or z - "glazeware"
    private String selectStatus() {
        String selection = "";
        System.out.println("\nSelect a clay status:");

        while (!(selection.equals("g") || selection.equals("b") || selection.equals("z"))) {
            System.out.print("\tg -> greenware");
            System.out.print("\n\tb -> bisqueware");
            System.out.print("\n\tz -> glazeware\n");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("g")) {
            return "greenware";
        } else if (selection.equals("b")) {
            return "bisqueware";
        } else {
            return "glazeware";
        }
    }

    // EFFECTS: allow user to select either b - return "bisque fire", or g - return "glaze fire"
    private String selectNextStep() {
        String selection = "";
        System.out.println("\nSelect a firing step:");

        while (!(selection.equals("b") || selection.equals("g"))) {
            System.out.print("\tb -> bisque fire");
            System.out.print("\n\tg -> glaze fire\n");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("b")) {
            return "bisque fire";
        }  else {
            return "glaze fire";
        }
    }

    // EFFECTS: print title, clay type, status, and next step for given project
    private void printProjectReport(CeramicProject c) {
        System.out.println("\n\tProject \"" + c.getTitle() + "\" has clay type " + c.getClayType() + " and status "
                + c.getStatus() + ".");
        System.out.println("\tNext step to complete is: " + c.getNextStep() + ".");
    }
}
