import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {

    // Labels to display player details
    private JLabel nameLabel, positionLabel, ageLabel, teamLabel, ppgLabel, rebLabel, astLabel, tovLabel;

    public DetailsPanel() {
        setLayout(new GridLayout(8, 1)); // 8 rows for displaying details

        // Initialize the labels with placeholders
        nameLabel = new JLabel("Name: ");
        positionLabel = new JLabel("Position: ");
        ageLabel = new JLabel("Age: ");
        teamLabel = new JLabel("Team: ");
        ppgLabel = new JLabel("Points Per Game (PPG): ");
        rebLabel = new JLabel("Rebounds (REB): ");
        astLabel = new JLabel("Assists (AST): ");
        tovLabel = new JLabel("Turnovers (TOV): ");

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
    }
}
