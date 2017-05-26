package Ex2;

import Ex2.activator.LinearActivator;
import Ex2.models.NeuralNetwork;
import Ex2.utils.DataSetChart;
import Ex2.utils.ErrorChart;
import Ex2.utils.FileUtils;
import org.jfree.data.xy.XYSeries;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic1DMatrix;

import java.io.FileNotFoundException;

/**
 * Created by marcinus on 19.03.17.
 */
public class Approximation {

    public void performApproximation() {
        try {
            Matrix[] matrices = new FileUtils().loadDataFromSingleFileSupervised("input.txt");
            ErrorChart errorChart = new ErrorChart();

            NeuralNetwork nn = new NeuralNetwork(matrices[0].columns(), matrices[1].columns(), false,
                    new int[]{30}, new LinearActivator(), 0.00001, 0.9);

            int iterations = 1000;
            XYSeries errorSeries = nn.train(matrices[0], matrices[1], iterations, 0.01);
            errorChart.addSeries(errorSeries);

            errorChart.generateChart();
            show2DDataAndApproximation(matrices, nn);

            //test(nn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void test(NeuralNetwork nn) throws FileNotFoundException {
        Matrix[] matrices = new FileUtils().loadDataFromSingleFileSupervised("approximation_test.txt");
        System.out.print("TEST: " + nn.test(matrices[0], matrices[1]));
    }

    private void show2DDataAndApproximation(Matrix[] matrices, NeuralNetwork nn) {
        DataSetChart chart = new DataSetChart(2);
        for (int i = 0; i < matrices[0].rows(); i++) {
            chart.addEntry(0, matrices[0].get(i, 0), matrices[1].get(i, 0));
        }
        double minArg = matrices[0].min();
        double maxArg = matrices[0].max();
        double step = (maxArg - minArg) / 100;
        for (double i = minArg; i < maxArg; i += step) {
            Matrix inputMatrix = new Basic1DMatrix(1, 1, new double[]{i});
            chart.addEntry(1, i, nn.predict(inputMatrix).get(0, 0));
        }

        chart.generateChart("approxData.jpg");
    }

    private void show2DData(Matrix[] matrices) {
        DataSetChart chart = new DataSetChart(1);
        for (int i = 0; i < matrices[0].rows(); i++) {
            chart.addEntry(0, matrices[0].get(i, 0), matrices[1].get(i, 0));
        }
        chart.generateChart("approxData.jpg");
    }
}
