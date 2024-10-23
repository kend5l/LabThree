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

        // Column names for the table (only these four)
        String[] columnNames = {"Player Name", "Team", "Position", "Age"};

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table model with data from the CSV
        playerData.forEach(row -> {
            String playerName = row.get("PName");
            String team = row.get("Team");
            String position = row.get("POS");
            String age = row.get("Age");

            // Add the row to the table (displaying only name, team, position, and age)
            tableModel.addRow(new Object[]{playerName, team, position, age});
        });

        // Create the table with the model
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane (with the table) to this panel
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to return the JTable, so it can be accessed by nbaGUI
    public JTable getTable() {
        return table;
    }
}
