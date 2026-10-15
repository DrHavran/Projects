import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private final ArrayList<Node> nodes;
    private final ArrayList<Node> testNodes;

    public Data() {
        this.nodes = new ArrayList<>();
        this.testNodes = new ArrayList<>();

        load("Iris.csv", nodes);
        load("Test_iris.csv", testNodes);
    }

    public void load(String string, ArrayList<Node> list) {
        try {
            Scanner sc = new Scanner(new File(string));
            String[] parts = sc.nextLine().split(",");
            int nameIndex = 0;
            for(int i = 0; i < parts.length; i++){
                if(parts[i].equals(Settings.nameType)){
                    nameIndex = i;
                    break;
                }
            }

            while (sc.hasNextLine()) {
                parts = sc.nextLine().split(",");
                Double[] values = new Double[parts.length - 1];
                String name = parts[nameIndex];

                for(int i = 0; i < parts.length; i++) {
                    int offset = 0;
                    if(i == nameIndex) {
                        continue;
                    }else if (i > nameIndex){
                        offset = -1;
                    }
                    values[i + offset] = Double.parseDouble(parts[i]);
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
}
