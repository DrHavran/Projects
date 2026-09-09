import Models.Check;
import Models.Entry;
import Models.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private final Data data;

    public Logic() {
        this.data = new Data();

        for(Node root : data.getRoots()){
            generateATree(root);
        }

        printTrees();
        testTrees(data.getRoots());
    }

    private void generateATree(Node root) {
        ArrayList<Node> queue = new ArrayList<>();
        queue.add(root);

        while(!queue.isEmpty()) {
            Node current = queue.removeFirst();

            double bestWeight = Double.MAX_VALUE;
            String bestOption = "";
            String bestAttribute = "";
            Check bestReq = null;

            ArrayList<String> attributes  = new ArrayList<>(data.getEntries().getFirst().getAttributeNames());

            /*
             *  The try catch is here to separate numerical and string attributes
             *  - Numerical attributes are applied with "<"
             *  - String attributes are applied with "="
             */
            for(String attribute : attributes){
               try{
                   ArrayList<Double> values = new ArrayList<>();
                   for(Entry entry : current.getEntries()){
                       // This part here crashed if the attribute is String based
                       values.add(Double.parseDouble(entry.getAttributes().get(attribute).value()));
                   }
                   values = values.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
                   ArrayList<Double> options = new ArrayList<>();
                   // The values for numerical values are always the average of two adjacent values
                   for(int i = 0; i < values.size()-1; i++){
                       options.add((values.get(i) + values.get(i+1)) / 2);
                   }

                   for(Double option : options){
                       Check req = (i) -> (Double.parseDouble((String) i) < option);
                       double weight = sortAndCheckWeight(current, attribute, req);
                       if(weight < bestWeight){
                           bestWeight = weight;
                           bestAttribute = attribute;
                           bestOption = " < " + option;
                           bestReq = req;
                       }
                   }
               }catch (NumberFormatException e){
                   HashSet<String> options = new HashSet<>();
                   for(Entry entry : current.getEntries()){
                       options.add(entry.getAttributes().get(attribute).value());
                   }
                   for(String option : options){
                       Check req = (i) -> (i.equals(option));
                       double weight = sortAndCheckWeight(current, attribute, req);
                       if(weight < bestWeight){
                           bestWeight = weight;
                           bestAttribute = attribute;
                           bestOption = " = " + option;
                           bestReq = req;
                       }
                   }
               }
            }
            Node left = new Node();
            Node right = new Node();

            current.setLeftBranch(left);
            current.setRightBranch(right);
            current.setOption(bestOption);
            current.setCheckReq(bestReq);
            current.setNodeAttribute(bestAttribute);

            System.out.println("Best split on: " + bestAttribute + bestOption);
            for(Entry entry : current.getEntries()){
                if(current.check(entry)){
                    left.addPoint(entry);
                }else{
                    right.addPoint(entry);
                }
            }

            if(data.isClassificationNumerical()){
                if(left.getEntries().size() > Settings.minLeafs){
                    queue.add(left);
                }
                if(right.getEntries().size() > Settings.minLeafs){
                    queue.add(right);
                }
            }else{
                if(countGini(left.getEntries()) != 0){
                    queue.add(left);
                }
                if(countGini(right.getEntries()) != 0){
                    queue.add(right);
                }
            }
        }
    }

    private double sortAndCheckWeight(Node current, String attribute, Check req){
        ArrayList<Entry> leftBranch = new ArrayList<>();
        ArrayList<Entry> rightBranch = new ArrayList<>();

        for(Entry entry : current.getEntries()){
            if(req.check(entry.getAttributes().get(attribute).value())){
                leftBranch.add(entry);
            }else{
                rightBranch.add(entry);
            }
        }

        return countWeight(leftBranch, rightBranch);
    }
    private double countWeight(ArrayList<Entry> left, ArrayList<Entry> right){
        double total = left.size() + right.size();
        return (left.size()/total) * countGini(left) + (right.size()/total) * countGini(right);
    }
    private double countGini(ArrayList<Entry> list){
        HashMap<String, Double> types = new HashMap<>();
        for(Entry entry : list){
            if(types.containsKey(entry.getClassification())){
                types.replace(entry.getClassification(), types.get(entry.getClassification()) + 1);
            }else{
                types.put(entry.getClassification(), 1.0);
            }
        }

        double total = types.values().stream()
                .mapToDouble(value -> Math.pow(value/list.size(), 2))
                .sum();

        return 1 - total;
    }

    private Node getLeaf(Node node, Entry entry){
        Node current = node;

        while(current.getLeftBranch() != null){
            if(current.check(entry)){
                current = current.getLeftBranch();
            }else {
                current = current.getRightBranch();
            }
        }

        return current;
    }

    /**
     * These three functions are for visualizing the tree only
     */
    private void printTrees() {
        System.out.println();
        for(Node root : data.getRoots()){
            printNode(root, "", true);
        }
    }

    private void printNode(Node node, String prefix, boolean isLast) {
        if (node == null) return;

        System.out.print(prefix);

        if(isLast){
            if(!prefix.isEmpty()){
                System.out.print("└── no  ");
            }else {
                System.out.print("└── ");
            }
        }else{
            System.out.print("├── yes ");
        }

        if (node.getLeftBranch() != null) {
            System.out.println("[" + node.getNodeAttribute() + node.getOption() + "]");

            String childPrefix = prefix + (isLast ? "    " : "│   ");
            printNode(node.getLeftBranch(), childPrefix, false);
            printNode(node.getRightBranch(), childPrefix, true);
        } else {
            System.out.print("[");
            node.getEntries().forEach(entry ->
                    System.out.print(entry.getName() + " ")
            );
            if(data.isClassificationNumerical()){
                double count = 0;
                for(Entry entry : node.getEntries()){
                    count += Double.parseDouble(entry.getClassification());
                }
                System.out.print("- average: " + count/node.getEntries().size());
            }else{
                System.out.print("- " + node.getEntries().getFirst().getClassification());
            }
            System.out.println("]");
        }
    }

    private void testTrees(ArrayList<Node> roots){
        System.out.println();
        for(Entry entry : data.getTestEntries()){
            ArrayList<Double> numberResults = new ArrayList<>();
            HashMap<String, Integer> stringResults = new HashMap<>();
            for(Node root : roots){

                Node leaf = getLeaf(root, entry);

                if(data.isClassificationNumerical()){
                    double count = 0;
                    for(Entry data : leaf.getEntries()){
                        count += Double.parseDouble(data.getClassification());
                    }
                    numberResults.add(count/leaf.getEntries().size());
                }else{
                    String type = leaf.getEntries().getFirst().getClassification();
                    if(roots.size() != 1){
                        System.out.println("subtree guess: " + type);
                    }
                    if(stringResults.containsKey(type)){
                        stringResults.replace(type, stringResults.get(type) + 1);
                    }else{
                        stringResults.put(type, 1);
                    }
                }
            }

            if(data.isClassificationNumerical()){
                double count = numberResults.stream().mapToDouble(i -> i).sum();
                System.out.print("The tree guessed that " + entry.getName() + " is around " + count/numberResults.size());
                System.out.println(", the real answer was " + entry.getClassification());
            }else{
                int maxNumb = 0;
                String maxString = "";

                for(Map.Entry<String, Integer> data : stringResults.entrySet()){
                    if(data.getValue() > maxNumb){
                        maxString = data.getKey();
                        maxNumb = data.getValue();
                    }
                }

                String predicted = maxString;
                String actual = entry.getClassification();
                System.out.print("The tree guessed that " + entry.getName() + " is " + predicted);
                if (Objects.equals(predicted, actual)) {
                    System.out.println(" and it's true!");
                } else {
                    System.out.println(" and it's false!, correct option is " + actual);
                }
            }
        }
    }
}