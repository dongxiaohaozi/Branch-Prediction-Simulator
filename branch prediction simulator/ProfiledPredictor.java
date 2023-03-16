import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfiledPredictor extends Predictor{

    public class ProfileData {
        public int taken_count=0;
        public int untaken_count=0;
    }


    public int n;
    public int[] bpTables;
    Map<Long, ProfileData> preDeal = new HashMap<>();


    public ProfiledPredictor(int n, String path) throws FileNotFoundException {
        this.n = n;
        this.predictionCount = 0;
        this.misPredictionCount = 0;
        this.bpTables = new int[2 << n];
        for(int i=0;i<this.bpTables.length;i++)
        {
            bpTables[i]=0;
        }

        //pre-deal data
        File trace = new File(path);
        Scanner scan = new Scanner(trace);
        while (scan.hasNextLine()) {

            String instr = scan.nextLine();
            String[] tokens = instr.split(" ");
            Long dec = Long.parseLong(tokens[0]);
            long index = dec;

            if (!preDeal.containsKey(index)) {
                preDeal.put(index, new ProfileData());
            }

            if (tokens[1].equals("1") ) {
                preDeal.get(index).taken_count++;
            } else {
                preDeal.get(index).untaken_count++;
            }
        }
        scan.close();
    }

    //@Override
    //void shownResult() {
        //System.out.println("FINAL bpTables");
        //for(int i = 0; i < bpTables.length; i++) {
            //System.out.println(i + "\t" + bpTables[i]);
        //}
    //}

    @Override
    void predict(Long pc, boolean groundTruth) {

        this.predictionCount++;
        //get the index of bp tables( hash)
        int index = (int) (pc % bpTables.length);
        boolean prediction = false;

        // make prediction
        if (bpTables[index] >= 0) {
            prediction = true;
        }

        boolean predictionProfile = profilePredict(pc);
        if (predictionProfile == prediction) {

        } else {
            double rate = 1;
            double tmp = Math.random();
            if (tmp < rate) {
                prediction = predictionProfile;
            }
        }

        // update bp tables
        if(groundTruth && bpTables[index]<1)bpTables[index]++;
        else if(!groundTruth && bpTables[index]>-2)bpTables[index]--;

        //update mispredictions
        if(prediction != groundTruth) this.misPredictionCount++;


    }


    boolean profilePredict(long index) {
        if (preDeal.get(index).taken_count >= preDeal.get(index).untaken_count) {
            return true;
        } else {
            return false;
        }
    }
}
