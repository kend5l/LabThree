import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StatsPanel extends JPanel {

    // labels to display stats
    private JLabel avgAgeLabel;
    private JLabel avgGamesPlayedLabel;
    private JLabel avgPointsPerGameLabel;

    private double avgAge;
    private double avgGamesPlayed;
    private double avgPointsPerGame;

    private List<ChangeListener> listeners = new ArrayList<>();

    public StatsPanel() {
        setLayout(new GridLayout(3, 1));

        // initialize labels
        avgAgeLabel = new JLabel("Average Age: N/A");
        avgGamesPlayedLabel = new JLabel("Average Games Played: N/A");
        avgPointsPerGameLabel = new JLabel("Average Points Per Game: N/A");

        // add labels to the panel
        add(avgAgeLabel);
        add(avgGamesPlayedLabel);
        add(avgPointsPerGameLabel);

        // set a border for the panel
        setBorder(BorderFactory.createTitledBorder("Stats (In relation to filter)"));
    }

    // method to update the stats based on the filtered data
    public void updateStats(List<Map<String, String>> filteredData) {
        if (filteredData.isEmpty()) {
            avgAgeLabel.setText("Average Age: N/A");
            avgGamesPlayedLabel.setText("Average Games Played: N/A");
            avgPointsPerGameLabel.setText("Average Points Per Game: N/A");
            notifyListeners();
            return;
        }

        // calculate the average age
        avgAge = filteredData.stream()
                .mapToInt(row -> Integer.parseInt(row.get("Age")))
                .average()
                .orElse(0.0);

        // calculate the average games played
        avgGamesPlayed = filteredData.stream()
                .mapToInt(row -> Integer.parseInt(row.get("GP")))
                .average()
                .orElse(0.0);

        // calculate the average points per game (PPG)
        avgPointsPerGame = filteredData.stream()
                .mapToDouble(row -> {
                    double totalPoints = Double.parseDouble(row.get("PTS"));
                    int gamesPlayed = Integer.parseInt(row.get("GP"));
                    return totalPoints / gamesPlayed;
                })
                .average()
                .orElse(0.0);

        // update the labels with the calculated stats
        avgAgeLabel.setText(String.format("Average Age: %.2f", avgAge));
        avgGamesPlayedLabel.setText(String.format("Average Games Played: %.2f", avgGamesPlayed));
        avgPointsPerGameLabel.setText(String.format("Average Points Per Game: %.2f", avgPointsPerGame));

        notifyListeners();  // notify listeners that the stats have been updated
    }

    // method to provide the stats for the chart
    public Map<String, Double> getStatsData() {
        Map<String, Double> statsData = new HashMap<>();
        statsData.put("Average Age", avgAge);
        statsData.put("Average Games Played", avgGamesPlayed);
        statsData.put("Average Points Per Game", avgPointsPerGame);
        return statsData;
    }

    // add a change listener to notify when stats are updated
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    // notify all registered listeners
    private void notifyListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }
}
