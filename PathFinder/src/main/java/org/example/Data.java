package org.example;

import org.example.Models.Node;
import org.example.Models.Path;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Data {
    private final HashMap<String, Node> nodes;
    private final ArrayList<Path> paths;

    public Data() {
        this.nodes = new HashMap<>();
        this.paths = new ArrayList<>();
    }

    public void loadFile(String file){
        try{
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(
                    new FileInputStream(GlobalSettings.import_folder + file + ".osm")
            );

            while(reader.hasNext()){
                int event = reader.next();

                if(event == XMLStreamReader.START_ELEMENT){
                    String type = reader.getLocalName();

                    if(Objects.equals(type, "node")){
                        String id = reader.getAttributeValue(null, "id");
                        double latitude = Double.parseDouble(reader.getAttributeValue(null, "lat"));
                        double longitude = Double.parseDouble(reader.getAttributeValue(null, "lon"));

                        nodes.put(
                                id,
                                new Node(
                                        id, latitude, longitude
                                )
                        );
                    }else if(Objects.equals(type, "way")){
                        Node first = null;
                        Node second = null;
                        while(reader.hasNext()){
                            event = reader.next();

                            if(event == XMLStreamReader.START_ELEMENT
                                    && reader.getLocalName().equals("nd")){
                                if(first == null){
                                    first = nodes.get(reader.getAttributeValue(null, "ref"));
                                }else if (second == null) {
                                    second = nodes.get(reader.getAttributeValue(null, "ref"));
                                    createPaths(first, second);
                                }else{
                                    first = second;
                                    second = nodes.get(reader.getAttributeValue(null, "ref"));
                                    createPaths(first, second);
                                }
                            }else if(event == XMLStreamReader.END_ELEMENT
                                    && reader.getLocalName().equals("way")){
                                break;
                            }
                        }
                    }
                }
            }
            reader.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void createPaths(Node first, Node second){
        if (first == null || second == null) return;
        Path path = new Path(first, second);
        first.addPath(path);
        second.addPath(path);
        paths.add(path);
    }
    public HashMap<String, Node> getNodes() {
        return nodes;
    }
    public ArrayList<Path> getPaths() {
        return paths;
    }
    public Node getNode(String id){
        return nodes.get(id);
    }
}