import java.io.File;
import java.util.*;

public class Robot {
    private String status;
    private String stopStatus;
    private int index;
    private final HashMap<String, ArrayList<Rule>> rules;

    public Robot(String file) {
        this.rules = new HashMap<>();
        loadRules(file);
    }

    public void addRule(String status, int numb, int change, String move, String newStatus){
        if(rules.containsKey(status)){
            rules.get(status).add(new Rule(numb, change, move, newStatus));
        }else{
            ArrayList<Rule> list = new ArrayList<>();
            list.add(new Rule(numb, change, move, newStatus));
            rules.put(status, list);

        }
    }

    public int checkField(int numb){
        System.out.println(status);
        for(Rule rule : rules.get(status)){
            if(rule.checkNumber(numb)){
                if(Objects.equals(rule.getMove(), "R")){
                    moveRight();
                }else if(Objects.equals(rule.getMove(), "L")){
                    moveLeft();
                }
                status = rule.getNewStatus();
                if(rule.getChangeNumb() == -1){
                    return numb;
                }else{
                    return rule.getChangeNumb();
                }
            }
        }
        return numb;
    }

    private void loadRules(String file){
        try{
            Scanner sc = new Scanner(new File("programs/" + file + ".txt"));
            String[] line = sc.nextLine().split(",");
            this.index = Integer.parseInt(line[0]);
            this.status = line[1];
            this.stopStatus = line[2];

            while(sc.hasNextLine()){
                String lineSc = sc.nextLine();
                if(lineSc.contains("//")){
                    lineSc = lineSc.split("//")[0].trim();
                }
                line = lineSc.split(",");
                if(line.length != 1){
                    addRule(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], line[4]);
                }
            }

        }catch (Exception e){
            e.getSuppressed();
        }
    }

    private void moveRight(){
        index++;
    }
    private void moveLeft(){
        if(index != 0){
            index--;
        }
    }
    public int getIndex() {
        return index;
    }
    public String getStatus() {
        return status;
    }
    public boolean checkEnd(){
        return status.equals(stopStatus);
    }
}
