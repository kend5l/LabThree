import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class nbaGUI extends JFrame {

    private DetailsPanel detailsPanel;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;

    public nbaGUI(List<Map<String, String>> playerData) {

        setTitle("NBA Player Stats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        // Initialize StatsPanel and ChartPanel
        statsPanel = new StatsPanel();
        chartPanel = new ChartPanel();

        // Create and add panels
        TablePanel tablePanel = new TablePanel(playerData, statsPanel);
        detailsPanel = new DetailsPanel();

        // Add the panels to the grid layout
        add(tablePanel);
        add(statsPanel);
        add(detailsPanel);
        add(chartPanel);

        // Set up Observer Pattern
        tablePanel.addSelectionListener(detailsPanel); // DetailsPanel updates on selection change
        tablePanel.addSelectionListener(statsPanel);   // StatsPanel updates on selection change
        statsPanel.addChangeListener(chartPanel);      // ChartPanel updates when stats change

        // Set initial values in StatsPanel and ChartPanel based on the full player data
        statsPanel.updateStats(playerData);
        chartPanel.updateChart(statsPanel.getStatsData());

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Read CSV and initialize GUI
            List<Map<String, String>> playerData = ProcessCSV.readCSV("src/2023_nba_player_stats.csv");
            SwingUtilities.invokeLater(() -> new nbaGUI(playerData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
