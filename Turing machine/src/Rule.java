public class Rule {
    private final int numb;
    private final int changeNumb;
    private final String move;
    private final String newStatus;

    public Rule(int numb, int change, String move, String newStatus) {
        this.numb = numb;
        this.changeNumb = change;
        this.move = move;
        this.newStatus = newStatus;
    }

    public boolean checkNumber(int numb){
        return numb == this.numb || this.numb == -1;
    }

    public int getChangeNumb() {
        return changeNumb;
    }
    public String getMove() {
        return move;
    }
    public String getNewStatus() {
        return newStatus;
    }
}
