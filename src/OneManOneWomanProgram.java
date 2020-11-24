import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class OneManOneWomanProgram {
    private int NUMBER_OF_GENERATIONS = 70;
    private int INITIAL_GENERATION_SIZE = 1000;
    private int MORTALITY = 5;
    private int MARRIAGE_RATES = 90;
    private int AVERAGE_NUMBER_OF_KIDS_PER_COUPLE = 5;
    private int LIKELYHOOD_OF_DISASTER_EACH_GENERATION = 4;

    public void runAll(){
        //initialization

        ArrayList<Human> initialGeneration = getInitialGenerations();
        Generation firstGeneration = performMarriages(initialGeneration, 0);


        //processing generations
        Generation lastGeneration;
        lastGeneration = firstGeneration;
        for(int i = 0; i <= NUMBER_OF_GENERATIONS; i++){
            Generation nextGeneration = makeNextGeneration(lastGeneration);
            lastGeneration = nextGeneration;

            if (i%100 == 0 ) System.out.println(i + "th generation");
            else System.out.println(i + " humans: " + lastGeneration.getSize());

            if (lastGeneration.getSize() == 0) i = NUMBER_OF_GENERATIONS + 1;

        }

        //count up results
        System.out.println("Total Popultaion: " + lastGeneration.getSize());
        countUpMitochondria(lastGeneration);
        countUpY_Chromosome(lastGeneration);

        System.out.println("Finished");
    }

    private void countUpMitochondria(Generation lastGeneration) {
        ArrayList<Couple> couples = lastGeneration.getCouples();
        ArrayList<Human> others = lastGeneration.getOthers();
        int[] counts = new int[INITIAL_GENERATION_SIZE];

        for (Couple couple : couples){
            int manMito = couple.getMan().getMitochondria();
            int womanMito = couple.getWoman().getMitochondria();
            counts[manMito]++;
            counts[womanMito]++;


        }

        for (Human human : others){

            if (human.getClass() == Male.class){
                int humanMito = ((Male) human).getMitochondria();
                counts[humanMito]++;
            }else{
                int humanMito = ((Female) human).getMitochondria();
                counts[humanMito]++;
            }
        }

        for (int i = 0; i < counts.length; i++){
            if(counts[i] != 0){
                System.out.println("Mitochrondia " + i + ": " + counts[i]);
            }
        }


    }

    private void countUpY_Chromosome(Generation lastGeneration){
        ArrayList<Couple> couples = lastGeneration.getCouples();
        ArrayList<Human> others = lastGeneration.getOthers();
        int[] counts = new int[INITIAL_GENERATION_SIZE];

        for (Couple couple : couples){
            int manY = couple.getMan().getY_chromosome();
            counts[manY]++;


        }

        for (Human human : others){

            if (human.getClass() == Male.class){
                int manY = ((Male) human).getY_chromosome();
                counts[manY]++;
            }
        }

        for (int i = 0; i < counts.length; i++){
            if(counts[i] != 0){
                System.out.println("Y_Chromesome " + i + ": " + counts[i]);
            }
        }
    }

    private Human createHuman(int mitochondria, int Y_chromosome){
        Random random = new Random();
        boolean female = random.nextBoolean();
        if(female){
            return new Female(mitochondria);
        }else{
            return new Male(mitochondria, Y_chromosome);
        }

    }

    public ArrayList<Human> getInitialGenerations(){
        ArrayList<Human> firstGeneration = new ArrayList<>();
        for (int i = 0; i < INITIAL_GENERATION_SIZE; i++){
            firstGeneration.add(createHuman(i,i));
        }
        return firstGeneration;
    }

    public Generation performMarriages(ArrayList<Human> lastGeneration, int generationNumber){
        ArrayList<Couple> newCouples = new ArrayList<>();
        Random random = new Random();
        Vector<Human> lastGenerationArray = new Vector<>();
        lastGenerationArray.addAll(lastGeneration);

        for (int s = 0; s < lastGenerationArray.size(); s++){
            Human human = lastGenerationArray.elementAt(s);
            int likelyHoodOfMarriage = random.nextInt(100);
            boolean dontMarry = false;
            if (likelyHoodOfMarriage > MARRIAGE_RATES) dontMarry = true;
            if (!dontMarry && !human.isMarried() && human.getClass() == Male.class){
                int i = 0;
                boolean notMarriedOrSomeLeft = true;
                while(notMarriedOrSomeLeft){
                    if (!lastGenerationArray.elementAt(i).isMarried() &&  lastGenerationArray.elementAt(s).getClass() != lastGenerationArray.elementAt(i).getClass()){
                        lastGenerationArray.elementAt(i).setMarried(true);
                        lastGenerationArray.elementAt(s).setMarried(true);
                        Couple newCouple = new Couple((Male) human, (Female) lastGenerationArray.elementAt(i));
                        newCouples.add(newCouple);
                        if (human.getParents() != null) human.getParents().addCouple(newCouple);
                        notMarriedOrSomeLeft = false;
                    }
                    if (i + 1 < lastGenerationArray.size()){
                        i++;
                    }else{
                        notMarriedOrSomeLeft = false;
                    }

                }
            }


        }

        ArrayList<Human> others = new ArrayList<>();
        for (int i = 0; i < lastGenerationArray.size(); i++){
            if (!lastGenerationArray.elementAt(i).isMarried()) others.add(lastGenerationArray.elementAt(i));
        }

        return new Generation(generationNumber, newCouples, others);

    }

    public Generation makeNextGeneration(Generation lastGeneration){

        ArrayList<Human> nextGenerationHumans = new ArrayList<>();
        ArrayList<Couple> couples = lastGeneration.getCouples();
        Random random = new Random();
        for (Couple couple : couples){
            int numberOfKids = random.nextInt(AVERAGE_NUMBER_OF_KIDS_PER_COUPLE * 2);
            for(int i = 0; i < numberOfKids; i++){
                Human newKid = createHuman(couple.getWoman().getMitochondria(), couple.getMan().getY_chromosome());
                couple.addChild(newKid);
                newKid.setParents(couple);
                nextGenerationHumans.add(newKid);
            }

        }


        ArrayList<Human> afterDeaths = calculateChildhoodSurvivors(nextGenerationHumans);

        Generation nextGeneration = performMarriages(afterDeaths, lastGeneration.getGenerationNumber() + 1);


        return nextGeneration;
    }

    private ArrayList<Human> calculateChildhoodSurvivors(ArrayList<Human> nextGenerationHumans) {
        Random random = new Random();
        ArrayList<Human> survivors = new ArrayList<>();
        for (Human human : nextGenerationHumans){
            if(random.nextInt(10) < 10 - MORTALITY){
                survivors.add(human);
            }

        }


        int catastropheEventNumber = random.nextInt(100);
        ArrayList<Human> catastropheSurvivors = new ArrayList<>();
        int likelyhoodFromDiseaseOrWar = nextGenerationHumans.size() / 5000;
        if (catastropheEventNumber <= LIKELYHOOD_OF_DISASTER_EACH_GENERATION + likelyhoodFromDiseaseOrWar){
            int damage = random.nextInt(50) + likelyhoodFromDiseaseOrWar * 2;
            System.out.println("Catastrophe! Approximately " + damage + "% died");
            for (Human human : survivors){
                if (random.nextInt(100) > damage){
                    catastropheSurvivors.add(human);
                }
            }

            return catastropheSurvivors;
        }



        return survivors;
    }


}
