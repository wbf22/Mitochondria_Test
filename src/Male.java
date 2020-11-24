public class Male extends Human{
    private int y_chromosome;
    private int mitochondria;

    public Male(int y_chromosome, int mitochondria) {
        this.y_chromosome = y_chromosome;
        this.mitochondria = mitochondria;
    }

    public int getY_chromosome() {
        return y_chromosome;
    }

    public int getMitochondria() {
        return mitochondria;
    }

}
