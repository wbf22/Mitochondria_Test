import java.util.ArrayList;

public class Couple {
    Male man;
    Female woman;
    ArrayList<Human> children;
    ArrayList<Couple> marriedChildren;


    public Couple(Male man, Female woman) {
        this.man = man;
        this.woman = woman;
        children = new ArrayList<>();
        marriedChildren = new ArrayList<>();
    }

    public Male getMan() {
        return man;
    }

    public Female getWoman() {
        return woman;
    }

    public void addChild(Human human){
        children.add(human);
    }

    public ArrayList<Human> getChildren() {
        return children;
    }

    public void addCouple(Couple couple){
        marriedChildren.add(couple);
    }

    public ArrayList<Couple> getMarriedChildren() {
        return marriedChildren;
    }
}
