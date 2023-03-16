public abstract class Predictor {


    public int predictionCount;
    public int misPredictionCount;


    //abstract void shownResult();
    abstract void predict(Long pc,boolean groundTruth);
}
