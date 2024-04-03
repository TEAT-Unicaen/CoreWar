package coreWar.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import coreWar.trainingManager.TrainingImporter;

public class GraphVisualisation {
    public static void main(String[] args) throws IOException {
        List<List<Integer>> scores = TrainingImporter.importScores();
        System.out.println(scores.get(0));
        List<Integer> xData = new ArrayList<Integer>();
        List<Double> yData = new ArrayList<Double>();
        for (int i = 0; i < scores.size(); i++) {
            xData.add(i);
            yData.add(calculateAverage(scores.get(i)));
        }
        System.out.println(scores.size());

        // Create Chart
        XYChart chart = QuickChart.getChart("Evolution of individual's scores generated by G.A. for CoreWar", "Generation", "mean of scores", "mean of scores by generation",xData, yData);

        // Show it
        //new SwingWrapper(chart).displayChart();

        // Save it
        BitmapEncoder.saveBitmapWithDPI(chart, String.format("./graphOfScoreFor%dGen", scores.size()), BitmapFormat.PNG, 300);


        List<Double> yData2 = new ArrayList<Double>();
        for (int i = 0; i < scores.size(); i++) {
            yData2.add(Double.valueOf(calculateMax(scores.get(i))));
        }

        // Create Chart
        XYChart chart2 = QuickChart.getChart("Evolution of the winner's scores generated by G.A. for CoreWar", "Generation", "winner of scores", "winner scores by generation",xData, yData2);

        // Show it
        //new SwingWrapper(chart2).displayChart();

        // Save it
        BitmapEncoder.saveBitmapWithDPI(chart2, String.format("./graphOfWinnerScoreFor%dGen", scores.size()), BitmapFormat.PNG, 300);
    }

    public static Double calculateAverage(List<Integer> numbers) {
        Double sum = 0.0;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum / numbers.size();
    }

    public static Integer calculateMax(List<Integer> numbers) {
        Integer max = numbers.get(0);
        for (Integer number : numbers) {
            if (max < number)
            max = number;
        }
        return max;
    }

}