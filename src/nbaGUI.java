import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class nbaGUI extends JFrame {

    public nbaGUI(List<Map<String, String>> playerData) {
        setTitle("NBA Player Stats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // 2 rows, 2 columns to ensure equal size for all panels

        // Create and add panels
        JPanel tablePanel = new TablePanel(playerData);  // Use the new TablePanel class
        JPanel statsPanel = createStatsPanel();
        JPanel detailsPanel = createDetailsPanel();
        JPanel chartPanel = createChartPanel();

        // Add the panels to the grid layout
        add(tablePanel);
        add(statsPanel);
        add(detailsPanel);
        add(chartPanel);

        // Set visibility
        setVisible(true);
    }

    // Placeholder method to create the StatsPanel
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Stats Panel"));
        panel.setLayout(new FlowLayout());
        return panel;
    }

    // Placeholder method to create the DetailsPanel
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Details Panel"));
        panel.setLayout(new FlowLayout());
        return panel;
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

