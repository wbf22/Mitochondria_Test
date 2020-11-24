import java.util.ArrayList;

public class Generation {
    private int generationNumber;
    private ArrayList<Couple> couples;
    private ArrayList<Human> others;

    public Generation(int generationNumber, ArrayList<Couple> couples, ArrayList<Human> others) {
        this.generationNumber = generationNumber;
        this.couples = couples;
        this.others = others;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public void addCouple(Couple couple){
        couples.add(couple);
    }

    public ArrayList<Couple> getCouples() {
        return couples;
    }

    public void addOther(Human other){
        others.add(other);
    }

    public ArrayList<Human> getOthers() {
        return others;
    }

    public int getSize(){

        return couples.size() * 2 + others.size();
    }
}
