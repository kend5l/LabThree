import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class nbaGUI extends JFrame {

    private DetailsPanel detailsPanel;  // Reference to DetailsPanel
    private StatsPanel statsPanel;      // Reference to StatsPanel

    public nbaGUI(List<Map<String, String>> playerData) {
        setTitle("NBA Player Stats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // 2 rows, 2 columns to ensure equal size for all panels

        // Initialize StatsPanel
        statsPanel = new StatsPanel();

        // Create and add panels
        TablePanel tablePanel = new TablePanel(playerData, statsPanel);  // Pass StatsPanel to TablePanel
        detailsPanel = new DetailsPanel(); // Initialize the DetailsPanel

        JPanel chartPanel = createChartPanel();

        // Add the panels to the grid layout
        add(tablePanel);
        add(statsPanel);  // Add StatsPanel
        add(detailsPanel);
        add(chartPanel);

        // Set visibility
        setVisible(true);

        // Add a listener for row selection in the table
        tablePanel.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Get the selected row index in the table
                    int selectedRow = tablePanel.getTable().getSelectedRow();

                    if (selectedRow != -1) {
                        // Extract the player data from the currently displayed (filtered) table
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

                        // Update the details panel with the selected player information, including PPG
                        detailsPanel.updateDetails(name, position, age, team, String.format("%.2f", ppg), reb, ast, tov);
                    }
                }
            }
        });
    }

    // Placeholder method to create the ChartPanel
    private JPanel createChartPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Chart Panel"));
        panel.setLayout(new FlowLayout());
        return panel;
    }

    public static void main(String[] args) {
        try {
            List<Map<String, String>> playerData = ProcessCSV.readCSV("src/2023_nba_player_stats.csv");
            SwingUtilities.invokeLater(() -> new nbaGUI(playerData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
