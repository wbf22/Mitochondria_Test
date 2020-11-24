public class Human {

    private boolean married;
    private Human partner;
    private Couple parents;


    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Human getPartner() {
        return partner;
    }

    public void setPartner(Human partner) {
        this.partner = partner;
    }

    public Couple getParents() {
        return parents;
    }

    public void setParents(Couple parents) {
        this.parents = parents;
    }
}
