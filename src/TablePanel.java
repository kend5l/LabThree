import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public TablePanel(List<Map<String, String>> playerData) {
        setLayout(new BorderLayout());

        // Column names for the table
        String[] columnNames = {"Player Name", "Position", "Team", "Age"};

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table model with data from the CSV
        playerData.forEach(row -> {
            // Assuming your CSV file has these columns: "Player Name", "Position", "Team", "Age"
            String playerName = row.get("PName");
            String position = row.get("POS");
            String team = row.get("Team");
            String age = row.get("Age");

            // Add the row to the table
            tableModel.addRow(new Object[]{playerName, position, team, age});
        });

        // Create the table with the model
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane (with the table) to this panel
        add(scrollPane, BorderLayout.CENTER);
    }

    // Optional: You can add methods to interact with the table, e.g., for sorting or filtering
}

