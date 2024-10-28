import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChartPanel extends JPanel {

    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    public ChartPanel() {
        // Initialize dataset and chart
        dataset = new DefaultCategoryDataset();
        chart = createChart(dataset);

        // Create ChartPanel from JFreeChart and add it to this JPanel
        org.jfree.chart.ChartPanel jfreeChartPanel = new org.jfree.chart.ChartPanel(chart);
        jfreeChartPanel.setPreferredSize(new Dimension(500, 270));
        setLayout(new BorderLayout());
        add(jfreeChartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        // Create a bar chart based on the dataset
        return ChartFactory.createBarChart(
                "Player Stats",   // chart title
                "Category",       // domain axis label
                "Value",          // range axis label
                dataset,          // data
                PlotOrientation.VERTICAL,
                true,             // include legend
                true,
                false
        );
    }

    // Method to update the dataset based on the data from StatsPanel
    public void updateChart(Map<String, Double> statsData) {
        // Clear previous dataset
        dataset.clear();

        // Add new data to the dataset
        for (Map.Entry<String, Double> entry : statsData.entrySet()) {
            dataset.addValue(entry.getValue(), "Player Stats", entry.getKey());
        }

        // Notify the chart that the dataset has been updated
        chart.fireChartChanged();
    }
}
