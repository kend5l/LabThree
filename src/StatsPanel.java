import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {

    // Labels to display stats
    private JLabel avgAgeLabel;
    private JLabel avgGamesPlayedLabel;
    private JLabel avgPointsPerGameLabel;

    public StatsPanel() {
        setLayout(new GridLayout(3, 1)); // 3 rows for displaying stats

        // Initialize labels
        avgAgeLabel = new JLabel("Average Age: ");
        avgGamesPlayedLabel = new JLabel("Average Games Played: ");
        avgPointsPerGameLabel = new JLabel("Average Points Per Game: ");

        // Add labels to the panel
        add(avgAgeLabel);
        add(avgGamesPlayedLabel);
        add(avgPointsPerGameLabel);

        // Set a border for the panel
        setBorder(BorderFactory.createTitledBorder("Stats"));
    }

    // Method to update the stats based on the filtered data
    public void updateStats(List<Map<String, String>> filteredData) {
        if (filteredData.isEmpty()) {
            avgAgeLabel.setText("Average Age: N/A");
            avgGamesPlayedLabel.setText("Average Games Played: N/A");
            avgPointsPerGameLabel.setText("Average Points Per Game: N/A");
            return;
        }

        // Calculate the average age
        double avgAge = filteredData.stream()
                .mapToInt(row -> Integer.parseInt(row.get("Age")))
                .average()
                .orElse(0.0);

        // Calculate the average games played
        double avgGamesPlayed = filteredData.stream()
                .mapToInt(row -> Integer.parseInt(row.get("GP")))
                .average()
                .orElse(0.0);

        // Calculate the average points per game (PPG)
        double avgPointsPerGame = filteredData.stream()
                .mapToDouble(row -> {
                    double totalPoints = Double.parseDouble(row.get("PTS"));
                    int gamesPlayed = Integer.parseInt(row.get("GP"));
                    return totalPoints / gamesPlayed;
                })
                .average()
                .orElse(0.0);

        // Update the labels with the calculated stats
        avgAgeLabel.setText(String.format("Average Age: %.2f", avgAge));
        avgGamesPlayedLabel.setText(String.format("Average Games Played: %.2f", avgGamesPlayed));
        avgPointsPerGameLabel.setText(String.format("Average Points Per Game: %.2f", avgPointsPerGame));
    }
}

