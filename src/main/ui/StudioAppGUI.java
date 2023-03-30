package ui;

import model.CeramicProject;
import model.CeramicProjectList;
import model.Studio;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Studio organizer application and GUI
public class StudioAppGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/studio.json";
    private Studio studio;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private CeramicProjectList inProgressProjects;
    private CeramicProjectList finishedProjects;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu;
    private JMenuItem loadFile;
    private JMenuItem saveFile;

    private JMenu editMenu;
    private JMenuItem addProjectEdit;
    private JMenuItem removeProjectEdit;

    private JPanel mainPanel = new JPanel();
    private JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(textPane);

    private DefaultMutableTreeNode myProjects;
    private DefaultMutableTreeNode inProgress;
    private DefaultMutableTreeNode finished;
    private JTree projectTree;

    ImageIcon complete = new ImageIcon("./data/complete.png", "Checkmark");

    // MODIFIES: this
    // EFFECTS: constructs studio frame and initializes GUI and studio application
    public StudioAppGUI() {
        super("My Pottery Studio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        textPane.setEditable(false);
        initializeMenu();
        initializeTree();
        addComponents();
        setImageSize();

        studio = new Studio("My Studio");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeLists();
    }

    // MODIFIES: this
    // EFFECTS: resize complete image icon
    private void setImageSize() {
        Image image = complete.getImage();
        Image resized = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        complete = new ImageIcon(resized);
    }

    // MODIFIES: this
    // EFFECTS: instantiate menu items, add action listeners for menu items, and add menu items to menu bar
    private void initializeMenu() {
        fileMenu = new JMenu("File");
        loadFile = new JMenuItem("Load previous projects");
        saveFile = new JMenuItem("Save current projects and changes");

        editMenu = new JMenu("Edit");
        addProjectEdit = new JMenuItem("Add a new project");
        removeProjectEdit = new JMenuItem("Remove a project");

        loadFile.addActionListener(this);
        saveFile.addActionListener(this);
        addProjectEdit.addActionListener(this);
        removeProjectEdit.addActionListener(this);
        menuBar.add(fileMenu);
        fileMenu.add(loadFile);
        fileMenu.add(saveFile);
        menuBar.add(editMenu);
        editMenu.add(addProjectEdit);
        editMenu.add(removeProjectEdit);
    }

    // MODIFIES: this
    // EFFECTS: instantiate tree nodes, add project collection nodes to main JTree node,
    //          add selection listener and response for projectTree
    private void initializeTree() {
        myProjects = new DefaultMutableTreeNode("My Projects");
        inProgress = new DefaultMutableTreeNode("In-progress Projects");
        finished = new DefaultMutableTreeNode("Finished Projects");
        projectTree = new JTree(myProjects);

        myProjects.add(inProgress);
        myProjects.add(finished);

        projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        projectTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            if (node.isLeaf()) {
                Object nodeObject = node.getUserObject();
                if (node.getParent().equals(inProgress)) {
                    getProjectFromNode(inProgressProjects, nodeObject);
                } else if (node.getParent().equals(finished)) {
                    getProjectFromNode(finishedProjects, nodeObject);
                }
            }
        });
    }

    // REQUIRES: parentList is one of finishedProjects or inProgressProjects
    // MODIFIES: this
    // EFFECTS: go through given project list to find CeramicProject with given nodeObject string,
    //          display project details when found
    private void getProjectFromNode(CeramicProjectList parentList, Object nodeObject) {
        String title = (String) nodeObject;
        for (int i = 0; i < parentList.length(); i++) {
            CeramicProject c = parentList.getProjectFromIndex(i);
            if (c.getTitle().equals(title)) {
                displayProjectDetails(parentList, c);
            }
        }
    }

    // REQUIRES: parentList is one of finishedProjects or inProgressProjects
    // MODIFIES: this
    // EFFECTS: print title, clay type, stage, and next step for given project to textArea
    //          if no next steps for project, print that it is complete and display 'complete' icon
    private void displayProjectDetails(CeramicProjectList parentList, CeramicProject c) {
        if (parentList.equals(finishedProjects)) {
            textPane.setText("\n\tProject \"" + c.getTitle() + "\" has clay type " + c.getClayType()
                    + " and is in the " + c.getStage() + " stage." + "\n\tProject is finished.");
            textPane.insertIcon(complete);
        } else {
            textPane.setText("\n\tProject \"" + c.getTitle() + "\" has clay type " + c.getClayType()
                    + " and is in the " + c.getStage() + " stage." + "\n\tNext step to complete is: "
                    + c.getNextStep() + ".");
        }
        textPane.updateUI();
    }

    // MODIFIES: this
    // EFFECTS: add GUI components to frame
    private void addComponents() {
        getContentPane().add(BorderLayout.SOUTH, mainPanel);
        getContentPane().add(BorderLayout.NORTH, menuBar);
        getContentPane().add(BorderLayout.WEST, projectTree);
        getContentPane().add(BorderLayout.CENTER, scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: initialize in-progress and finished project lists
    private void initializeLists() {
        inProgressProjects = studio.getInProgressProjects();
        finishedProjects = studio.getFinishedProjects();
    }

    // MODIFIES: this
    // EFFECTS: create and add new project to chosen list using popup windows and show confirmation message,
    //          if project cannot be added, display message to indicate failure
    private void addProject() {
        String title = JOptionPane.showInputDialog(this, "Enter title of new project");
        Object[] clayTypes = {"stoneware", "earthenware", "porcelain"};
        String selectedClayType = popupWindow(clayTypes);

        Object[] stageOptions = {"greenware", "bisqueware", "glazeware"};
        String selectedStage = popupWindow(stageOptions);

        CeramicProject newProject = new CeramicProject(title, selectedClayType, selectedStage, null);
        addToProjectList(newProject);
    }

    // MODIFIES: this
    // EFFECTS: adds given project to project collection selected by user through a popup window,
    //          display completion or failure message
    private void addToProjectList(CeramicProject c) {
        String nextStep = null;

        CeramicProjectList selectedList = selectList();
        String listMessage = chooseListMessage(selectedList);

        int initialLength = selectedList.length();
        selectedList.addProject(c);

        if (selectedList.length() == initialLength) {
            JOptionPane.showMessageDialog(this, "New project not added: a project with title \""
                    + c.getTitle() + "\" already exists in the " + listMessage + " project collection.");
        } else {
            ProjectTreeItem newProjectItem = new ProjectTreeItem(c.getTitle());
            if (selectedList == studio.getFinishedProjects()) {
                nextStep = addToFinishedProjects(c);
                finished.add(newProjectItem);
            } else {
                inProgress.add(newProjectItem);
            }
            projectTree.updateUI();
            studio.addProject(new CeramicProject(c.getTitle(), c.getClayType(), c.getStage(), nextStep));
            JOptionPane.showMessageDialog(this, "Project \"" + c.getTitle()
                    + "\" has been added to the " + listMessage + " project collection.");
        }
    }

    // EFFECTS: finishes given project and returns "NONE"
    private String addToFinishedProjects(CeramicProject project) {
        project.finish();
        return "NONE";
    }

    // EFFECTS: display popup window with given option types and return selection as a String
    private String popupWindow(Object[] types) {
        return (String) JOptionPane.showInputDialog(null, "Choose one",
                "Input", JOptionPane.INFORMATION_MESSAGE, null, types, types[0]);
    }

    // EFFECTS: display popup window with project collection options, return chosen list
    private CeramicProjectList selectList() {
        Object[] lists = {"In-progress Collection", "Finished Collection"};
        Object selection = JOptionPane.showInputDialog(null, "Add my project to...",
                "Input", JOptionPane.INFORMATION_MESSAGE, null, lists, lists[0]);

        if (selection.equals("In-progress Collection")) {
            return studio.getInProgressProjects();
        } else {
            return studio.getFinishedProjects();
        }
    }

    // EFFECTS: returns a string of the type of given ceramic project list
    private String chooseListMessage(CeramicProjectList selectedList) {
        if (selectedList == studio.getInProgressProjects()) {
            return "in-progress";
        } else {
            return "finished";
        }
    }

    // MODIFIES: this
    // EFFECTS: allow title to be entered by user and remove project from chosen list and display confirmation message,
    //          if project cannot be removed, display failure message
    private void removeProject() {
        CeramicProjectList selectedList = selectList();
        DefaultMutableTreeNode selectedNode;
        String listMessage;
        if (selectedList == studio.getInProgressProjects()) {
            selectedNode = inProgress;
            listMessage = "in-progress";
        } else {
            selectedNode = finished;
            listMessage = "finished";
        }
        String title = JOptionPane.showInputDialog(this, "Enter title of project");

        ArrayList<CeramicProject> editedList = selectedList.removeProject(title);
        if (editedList == null) {
            JOptionPane.showMessageDialog(this, "Project not removed: project with title \""
                    + title + "\" does not exist in the " + listMessage + " project collection.");
        } else {
            removeProjectNode(selectedNode, title);
            projectTree.updateUI();
            JOptionPane.showMessageDialog(this, "Project \"" + title
                    + "\" has been removed from the " + listMessage + " project collection.");
            textPane.setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: finds child in given parent node with given title and removes from parent node
    private void removeProjectNode(DefaultMutableTreeNode node, String title) {
        for (int i = 0; i < node.getLeafCount(); i++) {
            ProjectTreeItem child = (ProjectTreeItem) node.getChildAt(i);
            if (child.getName().equals(title)) {
                child.removeFromParent();
            }
        }
    }

    // EFFECTS: saves the studio to file and display completion or failure message
    private void saveStudioToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(studio);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + studio.getName()
                    + " to " + JSON_STORE + ".");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads studio from file, adds projects to JTree and displays completion or failure message
    private void loadStudioFromFile() {
        try {
            this.studio = jsonReader.read();
            initializeLists();
            addProjectsFromFile();
            JOptionPane.showMessageDialog(this, "Loaded " + studio.getName() + " from "
                    + JSON_STORE + ".");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: for each project collection loaded from file, go through and create nodes in appropriate tree node
    //          and update projectTree UI
    private void addProjectsFromFile() {
        for (int i = 0; i < inProgressProjects.length(); i++) {
            CeramicProject c = inProgressProjects.getProjectFromIndex(i);
            ProjectTreeItem projectItem = new ProjectTreeItem(c.getTitle());
            inProgress.add(projectItem);
        }
        for (int i = 0; i < finishedProjects.length(); i++) {
            CeramicProject c = finishedProjects.getProjectFromIndex(i);
            ProjectTreeItem projectItem = new ProjectTreeItem(c.getTitle());
            finished.add(projectItem);
        }
        projectTree.updateUI();
    }

    // EFFECTS: call appropriate helper methods for items in menu bar
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadFile) {
            loadStudioFromFile();
        } else if (e.getSource() == saveFile) {
            saveStudioToFile();
        } else if (e.getSource() == addProjectEdit) {
            addProject();
        } else if (e.getSource() == removeProjectEdit) {
            removeProject();
        }
    }
}
