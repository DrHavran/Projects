package Models;

import java.util.ArrayList;

public class Node {
    private Node leftBranch, rightBranch;
    private ArrayList<Entry> entries;
    private String nodeAttribute;
    private Check checkReq;
    // [Option] is purely for visualization purposes
    private String option;

    public Node() {
        nodeAttribute = "";
        this.entries = new ArrayList<>();
    }

    public boolean check(Entry entry){
        String attribute = entry.getAttributes().get(nodeAttribute).value();
        if(attribute != null){
            return checkReq.check(attribute);
        }
        return false;
    }

    public void addPoint(Entry entry) {
        entries.add(entry);
    }
    public void setCheckReq(Check req) {
        checkReq = req;
    }
    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
    public void setNodeAttribute(String nodeAttribute) {
        this.nodeAttribute = nodeAttribute;
    }
    public void setLeftBranch(Node leftBranch) {
        this.leftBranch = leftBranch;
    }
    public void setRightBranch(Node rightBranch) {
        this.rightBranch = rightBranch;
    }
    public Node getLeftBranch() {
        return leftBranch;
    }
    public Node getRightBranch() {
        return rightBranch;
    }
    public String getNodeAttribute() {
        return nodeAttribute;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public ArrayList<Entry> getEntries() {
        return entries;
    }
}