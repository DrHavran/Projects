import Models.Entry;
import Models.Node;

import java.io.File;
import java.util.*;

public class Data {
    private final ArrayList<Entry> entries;
    private final ArrayList<Entry> testEntries;
    private final ArrayList<Node> roots;

    public Data() {
        this.entries = new ArrayList<>();
        this.testEntries = new ArrayList<>();
        this.roots = new ArrayList<>();

        loadData(entries, "train_" + Settings.dataset);
        loadData(testEntries, "test_" + Settings.dataset);
        System.out.println(entries.size() + " points loaded");
        System.out.println(testEntries.size() + " test points loaded");

        loadRoots();
    }

    private void loadData(ArrayList<Entry> list, String fileName) {
        try{
            Scanner sc = new Scanner(new File("data/" + fileName + ".csv"));
            HashMap<String, Integer> headerInformation = loadHeaderInformation(sc.nextLine());

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");

                String name = data[headerInformation.get(Settings.name)];
                String classification = data[headerInformation.get(Settings.type)];
                Entry entry = new Entry(name, classification);

                headerInformation.keySet().stream()
                        .filter(columnValue -> !columnValue.equals(Settings.name))
                        .filter(columnValue -> !columnValue.equals(Settings.type))
                        .forEach(columnValue -> {
                            int columnNumber = headerInformation.get(columnValue);
                            String value = data[columnNumber];

                            entry.addAttribute( new Entry.Attribute(
                                    columnValue, value
                            ));
                        });
                list.add(entry);
            }
        }catch (Exception e){
            System.out.println("Data.java loadData() - " + e.getMessage());
        }
    }

    private void loadRoots(){
        if(Settings.bagging){
            for(int i = 0; i < Settings.trees; i++){
                Node root = new Node();
                roots.add(root);
            }
            for(int i = 0; i < Settings.trees; i++){
                for(Entry entry : entries){
                    roots.get((int) (Math.random() * roots.size())).addPoint(entry);
                }
            }
        }else{
            Node root = new Node();
            root.setEntries(entries);
            roots.add(root);
        }
    }

    private HashMap<String, Integer> loadHeaderInformation(String line){
        HashMap<String, Integer> headerInformation = new HashMap<>();
        String[] parts = line.split(",");

        for(int i = 0; i < parts.length; i++){
            headerInformation.put(parts[i], i);
        }

        return headerInformation;
    }

    public boolean isClassificationNumerical(){
        try{
            double check = Double.parseDouble(entries.getFirst().getClassification());
            System.out.println(check);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public ArrayList<Node> getRoots(){ return roots; }
    public ArrayList<Entry> getTestEntries() {
        return testEntries;
    }
    public ArrayList<Entry> getEntries() {
        return entries;
    }
}