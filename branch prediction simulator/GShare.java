public class GShare extends Predictor {

    public int m, n;
    public int[] bpTables;
    int ghr;


    public GShare(int m, int n) {
        this.m = m;
        this.n = n;
        this.predictionCount = 0;
        this.misPredictionCount = 0;
        this.bpTables = new int[2 << m];
        for (int i = 0; i < this.bpTables.length; i++) {
            bpTables[i] = 0;
        }
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

        int mBits = (int) (pc % bpTables.length);

        int mnBits = (int) (mBits/Math.pow(2,n));

        int nBits = (int) (pc % ((int) Math.pow(2,n)));

        int xor = nBits ^ ghr;

        int index = mnBits << n;
        index = index + xor;

        boolean prediction = false;
        if(bpTables[index] >= 0) prediction = true;


        // update bp tables
        if(groundTruth && bpTables[index]<1)bpTables[index]++;
        else if(!groundTruth && bpTables[index]>-2)bpTables[index]--;

        //update mispredictions
        if(prediction != groundTruth) this.misPredictionCount++;

        //update GHR
        ghr = ghr >> 1;
        // if the actual outcome was true, insert 1 as MSB
        if(groundTruth) ghr = ghr + (int) Math.pow(2,n-1);
        // otherwise inserting zero is effectively adding 0;

    }
}
