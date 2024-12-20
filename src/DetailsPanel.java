import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DetailsPanel extends JPanel implements TableSelectionListener {

    // Labels to display player details
    private JLabel nameLabel, positionLabel, ageLabel, teamLabel, ppgLabel, rebLabel, astLabel, tovLabel;

    public DetailsPanel() {
        setLayout(new GridLayout(8, 1)); // 8 rows for displaying details

        // Initialize the labels with placeholders
        nameLabel = new JLabel("Name: N/A");
        positionLabel = new JLabel("Position: N/A");
        ageLabel = new JLabel("Age: N/A");
        teamLabel = new JLabel("Team: N/A");
        ppgLabel = new JLabel("Points Per Game (PPG): N/A");
        rebLabel = new JLabel("Rebounds (REB): N/A");
        astLabel = new JLabel("Assists (AST): N/A");
        tovLabel = new JLabel("Turnovers (TOV): N/A");

        // Add the labels to the panel
        add(nameLabel);
        add(positionLabel);
        add(ageLabel);
        add(teamLabel);
        add(ppgLabel);
        add(rebLabel);
        add(astLabel);
        add(tovLabel);

        // Set a border for the panel
        setBorder(BorderFactory.createTitledBorder("Player Details"));
    }

    // Observer Pattern: Update details when notified of a selection change
    @Override
    public void onSelectionChanged(Object selectedData) {
        if (selectedData instanceof Map) {
            Map<String, String> playerData = (Map<String, String>) selectedData;

            String name = playerData.getOrDefault("PName", "N/A");
            String position = playerData.getOrDefault("POS", "N/A");
            String age = playerData.getOrDefault("Age", "N/A");
            String team = playerData.getOrDefault("Team", "N/A");
            String ppg = playerData.getOrDefault("PPG", "N/A");
            String reb = playerData.getOrDefault("REB", "N/A");
            String ast = playerData.getOrDefault("AST", "N/A");
            String tov = playerData.getOrDefault("TOV", "N/A");

            updateDetails(name, position, age, team, ppg, reb, ast, tov);
        } else {
            clearDetails();
        }
    }

    // Method to update the details based on the selected player
    public void updateDetails(String name, String position, String age, String team, String ppg, String reb, String ast, String tov) {
        nameLabel.setText("Name: " + name);
        positionLabel.setText("Position: " + position);
        ageLabel.setText("Age: " + age);
        teamLabel.setText("Team: " + team);
        ppgLabel.setText("Points Per Game (PPG): " + ppg);
        rebLabel.setText("Rebounds (REB): " + reb);
        astLabel.setText("Assists (AST): " + ast);
        tovLabel.setText("Turnovers (TOV): " + tov);

        // Revalidate and repaint to ensure UI updates
        revalidate();
        repaint();
    }

    // Method to clear the details (e.g., when no player is selected)
    public void clearDetails() {
        nameLabel.setText("Name: N/A");
        positionLabel.setText("Position: N/A");
        ageLabel.setText("Age: N/A");
        teamLabel.setText("Team: N/A");
        ppgLabel.setText("Points Per Game (PPG): N/A");
        rebLabel.setText("Rebounds (REB): N/A");
        astLabel.setText("Assists (AST): N/A");
        tovLabel.setText("Turnovers (TOV): N/A");

        // Revalidate and repaint to ensure UI updates
        revalidate();
        repaint();
    }
}

