import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class nbaGUI extends JFrame {

    private DetailsPanel detailsPanel;  // Reference to DetailsPanel
    private StatsPanel statsPanel;      // Reference to StatsPanel
    private ChartPanel chartPanel;      // Reference to ChartPanel

    public nbaGUI(List<Map<String, String>> playerData) {
        setTitle("NBA Player Stats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // 2 rows, 2 columns to ensure equal size for all panels

        // Initialize StatsPanel and ChartPanel
        statsPanel = new StatsPanel();
        chartPanel = new ChartPanel();

        // Create and add panels
        TablePanel tablePanel = new TablePanel(playerData, statsPanel);  // Pass StatsPanel to TablePanel
        detailsPanel = new DetailsPanel(); // Initialize the DetailsPanel

        // Add the panels to the grid layout
        add(tablePanel);
        add(statsPanel);  // Add StatsPanel
        add(detailsPanel);
        add(chartPanel);  // Add the real ChartPanel

        // Set initial values in StatsPanel and ChartPanel based on the full player data
        statsPanel.updateStats(playerData);  // Assuming playerData is the initial data
        Map<String, Double> initialStatsData = statsPanel.getStatsData();
        chartPanel.updateChart(initialStatsData);  // Display initial values in the chart

        // Set visibility
        setVisible(true);

        // Add a listener for row selection in the table to update DetailsPanel only
        tablePanel.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Get the selected row index in the table
                    int selectedRow = tablePanel.getTable().getSelectedRow();

                    if (selectedRow != -1) {
                        // Extract player data from the selected row
                        String name = (String) tablePanel.getTable().getValueAt(selectedRow, 0);
                        String team = (String) tablePanel.getTable().getValueAt(selectedRow, 1);
                        String position = (String) tablePanel.getTable().getValueAt(selectedRow, 2);
                        String age = (String) tablePanel.getTable().getValueAt(selectedRow, 3);

                        // Get the actual player data from the filtered list in the TablePanel
                        Map<String, String> player = tablePanel.getFilteredData().get(selectedRow);
                        String totalPoints = player.get("PTS");
                        String gamesPlayed = player.get("GP");
                        String reb = player.get("REB");
                        String ast = player.get("AST");
                        String tov = player.get("TOV");

                        // Calculate Points Per Game (PPG)
                        double ppg = Double.parseDouble(totalPoints) / Double.parseDouble(gamesPlayed);

                        // Update the details panel with the selected player information
                        detailsPanel.updateDetails(name, team, position, age, String.format("%.2f", ppg), reb, ast, tov);
                    }
                }
            }
        });

        // Add a listener to the StatsPanel to update the ChartPanel when stats change
        statsPanel.addChangeListener(e -> {
            Map<String, Double> updatedStatsData = statsPanel.getStatsData();
            chartPanel.updateChart(updatedStatsData);  // Update chart with new stats data
        });
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
