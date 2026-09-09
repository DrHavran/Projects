package Models;

import java.util.HashMap;
import java.util.Set;

public class Entry {
    private final String name;
    private final String classification;
    private final HashMap<String, Attribute> attributes;

    public Entry(String name, String classification) {
        this.name = name;
        this.classification = classification;
        this.attributes = new HashMap<>();
    }

    public void addAttribute(Attribute attribute){
        attributes.put(attribute.name, attribute);
    }

    public String getName() {
        return name;
    }
    public String getClassification() {
        return classification;
    }
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }
    public Set<String> getAttributeNames(){
        return attributes.keySet();
    }

    public record Attribute(String name, String value){}
}