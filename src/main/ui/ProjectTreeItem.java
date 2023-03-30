package ui;

import javax.swing.tree.DefaultMutableTreeNode;

// Tree node item extending DefaultMutableTreeNode
public class ProjectTreeItem extends DefaultMutableTreeNode {
    private String name;

    // EFFECTS: creates a ProjectTreeItem according to parent type, sets name to given name
    public ProjectTreeItem(String name) {
        super(name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
