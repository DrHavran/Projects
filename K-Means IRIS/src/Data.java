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

    // K-means++
    public void createClusters(){
        // Pick a random node
        clusters.add(new Cluster(copyValues(nodes.get((int)(Math.random() * nodes.size())))));

        while(clusters.size() < Settings.clusters){
            double[] d2 = new double[nodes.size()];
            double sum = 0;
            for(int i = 0; i < nodes.size(); i++){
                double best = Double.MAX_VALUE;
                for(Cluster c : clusters){
                    best = Math.min(best, distSq(nodes.get(i).getValues(), c.getValues()));
                }
                d2[i] = best;
                sum += best;
            }

            double r = Math.random() * sum;
            double acc = 0;
            int pick = 0;
            for(int i = 0; i < nodes.size(); i++){
                acc += d2[i];
                if(acc >= r){ pick = i; break; }
            }
            clusters.add(new Cluster(copyValues(nodes.get(pick))));
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

    private Double[] copyValues(Node node){
        Double[] src = node.getValues();
        Double[] out = new Double[src.length];
        for(int i = 0; i < src.length; i++){
            out[i] = src[i];
        }
        return out;
    }

    private double distSq(Double[] a, Double[] b){
        double sum = 0;
        for(int i = 0; i < a.length; i++){
            double d = a[i] - b[i];
            sum += d * d;
        }
        return sum;
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
