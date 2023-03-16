public class AlwaysTaken extends Predictor {

    public AlwaysTaken() {
        this.predictionCount = 0;
        this.misPredictionCount = 0;
    }

    //@Override
    //void shownResult() {

    //}

    @Override
    void predict(Long pc, boolean groundTruth) {
        this.predictionCount++;
        boolean prediction = true;

        if(prediction!=groundTruth)
        {
            this.misPredictionCount++;
        }
    }
}
