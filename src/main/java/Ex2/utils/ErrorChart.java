package Ex2.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

/**
 * Created by marcinus on 16.03.17.
 */
public class ErrorChart {

    XYSeriesCollection dataset = new XYSeriesCollection();

    public void addSeries(XYSeries series) {
        dataset.addSeries(series);
    }

    public void generateChart() {
        generateChart("chart.jpg");
    }

    public void generateChart(String outputName) {

        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres spadku błędu", // Title
                "iteracja", // x-axis Label
                "błąd", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                false, // Use tooltips
                false // Configure chart to generate URLs?
        );

        try {
            ChartUtilities.saveChartAsJPEG(new File(outputName), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }

}
