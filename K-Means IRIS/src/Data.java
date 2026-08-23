import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Data {
    private final ArrayList<Node> nodes;
    private final ArrayList<Node> testNodes;
    private final HashSet<Cluster> clusters;

    public Data() {
        this.nodes = new ArrayList<>();
        this.testNodes = new ArrayList<>();
        this.clusters = new HashSet<>();

        load("Iris.csv", nodes);
        load("Test_iris.csv", testNodes);
    }

    public void createClusters(){
        for(int i = 0; i < Settings.clusters; i++){
            Double[] values = new Double[nodes.getFirst().getSize()];
            Node randomNode = nodes.get((int) (Math.random() * nodes.size()));
            for(int v = 0; v < values.length; v++) {
                values[v] = randomNode.getValues()[v];
            }
            clusters.add(new Cluster(values));
        }
    }

    public void load(String string, ArrayList<Node> list) {
        try {
            Scanner sc = new Scanner(new File(string));
            sc.nextLine();

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                Double[] values = new Double[line.length - 1];
                String name = null;

                for(int i = 0; i < line.length  ; i++) {
                    try{
                        values[i] = Double.parseDouble(line[i]);
                    }catch (NumberFormatException e) {
                        name = line[i];
                    }
                }
                list.add(new Node(name, values));
            }
            sc.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public ArrayList<Node> getTestNodes(){
        return testNodes;
    }
    public HashSet<Cluster> getClusters() {
        return clusters;
    }
}
