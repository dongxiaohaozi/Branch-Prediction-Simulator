import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sim {

    public static void main(String[] args) throws FileNotFoundException {

        Predictor predictor;
        File traceFile;

        // choose the predictor
        if (args[0].equals("always_taken")) {
            predictor = new AlwaysTaken();
            traceFile = new File(args[1]);
        }
        else if(args[0].equals("two_bit"))
        {
            predictor = new TwoBitPredictor(Integer.parseInt(args[1]));
            traceFile = new File(args[2]);
        }
        else if(args[0].equals("gshare"))
        {
            predictor = new GShare(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
            traceFile = new File(args[3]);
        }
        else if(args[0].equals("profiled"))
        {
            predictor = new ProfiledPredictor(Integer.parseInt(args[1]),args[2]);
            traceFile = new File(args[2]);
        }
        else {
            System.out.println("illegal predictor strategy");
            return;
        }

        //start to predict
        Scanner scanner = new Scanner(traceFile);
        //int k = -1;
        while (scanner.hasNext()) {
           // k++;
            String tuple = scanner.nextLine();
            String[] tokens = tuple.split(" ");

            Long pc = Long.valueOf(tokens[0]);
            boolean groundTruth = tokens[1].equals("1");
            //if (k % 4 != 0)
               // continue;
            predictor.predict(pc, groundTruth);


        }

        scanner.close();
        //print the prediction results
        double misPredictionRate = (double)predictor.misPredictionCount / (double) predictor.predictionCount;


        System.out.println("RESULT");
        System.out.println("number of predictions:\t" + predictor.predictionCount);
        System.out.println("number of mispredictions:\t" + predictor.misPredictionCount);
        System.out.println("misprediction rate:\t" + String.format("%.2f", misPredictionRate * 100) + "%");
        //predictor.shownResult();


    }


}
