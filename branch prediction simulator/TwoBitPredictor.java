public class TwoBitPredictor extends Predictor {

    public int n;
    public int[] bpTables;

    public TwoBitPredictor(int n) {
        this.n = n;
        this.predictionCount = 0;
        this.misPredictionCount = 0;
        this.bpTables = new int[2 << n];
        for (int i = 0; i < this.bpTables.length; i++) {
            bpTables[i] = 0;
        }
    }

    //@Override
   //void shownResult() {
        //ystem.out.println("FINAL bpTables");
        //for (int i = 0; i < bpTables.length; i++) {
           // System.out.println(i + "\t" + bpTables[i]);
       // }
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

        // update bp tables
        if (groundTruth && bpTables[index] < 1) {
            bpTables[index]++;
        } else if (!groundTruth && bpTables[index] > -2) {
            bpTables[index]--;
        }

        //update mispredictions
        if (prediction != groundTruth) {
            this.misPredictionCount++;
        }


    }
}
