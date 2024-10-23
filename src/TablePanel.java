import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Map<String, String>> allPlayerData;  // To hold original unfiltered data
    private TableRowSorter<DefaultTableModel> sorter; // For sorting the table

    private JComboBox<String> positionFilterBox;
    private JComboBox<String> ageFilterBox;
    private JComboBox<String> gamesPlayedFilterBox;
    private JComboBox<String> teamFilterBox;  // Team filter

    public TablePanel(List<Map<String, String>> playerData) {
        this.allPlayerData = playerData;  // Store the original data

        setLayout(new BorderLayout());

        // Create filter panel
        JPanel filterPanel = createFilterPanel();

        // Column names for the table
        String[] columnNames = {"Player Name", "Team", "Position", "Age", "Games Played"};

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter); // Attach the sorter to the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Populate table initially with all data
        populateTable(allPlayerData);

        // Add the filter panel and table to the layout
        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Create the filter panel with dropdowns
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        // Add position filter
        panel.add(new JLabel("Position:"));
        positionFilterBox = new JComboBox<>(getUniqueValues("POS", true));
        panel.add(positionFilterBox);

        // Add age filter
        panel.add(new JLabel("Age:"));
        ageFilterBox = new JComboBox<>(getUniqueValues("Age", true));
        panel.add(ageFilterBox);

        // Add games played filter (ascending sort)
        panel.add(new JLabel("Games Played:"));
        gamesPlayedFilterBox = new JComboBox<>(getUniqueValues("GP", true));
        panel.add(gamesPlayedFilterBox);

        // Add team filter
        panel.add(new JLabel("Team:"));
        teamFilterBox = new JComboBox<>(getUniqueValues("Team", true));
        panel.add(teamFilterBox);

        // Add "No Filter" option
        JButton noFilterButton = new JButton("No Filter");
        noFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyNoFilter();
            }
        });
        panel.add(noFilterButton);

        // Add a filter button
        JButton filterButton = new JButton("Apply Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        panel.add(filterButton);

        return panel;
    }

    // Populate the table with the player data
    private void populateTable(List<Map<String, String>> filteredData) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add rows to the table model
        filteredData.forEach(row -> {
            String playerName = row.get("PName");
            String team = row.get("Team");
            String position = row.get("POS");
            String age = row.get("Age");
            String gamesPlayed = row.get("GP");

            tableModel.addRow(new Object[]{playerName, team, position, age, gamesPlayed});
        });
    }

    // Apply sorting and filtering based on the selected options
    private void applyFilters() {
        String positionFilter = (String) positionFilterBox.getSelectedItem();
        String ageFilter = (String) ageFilterBox.getSelectedItem();
        String gamesPlayedFilter = (String) gamesPlayedFilterBox.getSelectedItem();
        String teamFilter = (String) teamFilterBox.getSelectedItem();

        // Filter the data based on the selected filters
        List<Map<String, String>> filteredData = allPlayerData.stream()
                .filter(row -> positionFilter.equals("All") || row.get("POS").equals(positionFilter))
                .filter(row -> ageFilter.equals("All") || row.get("Age").equals(ageFilter))
                .filter(row -> teamFilter.equals("All") || row.get("Team").equals(teamFilter))
                .sorted((row1, row2) -> Integer.compare(Integer.parseInt(row1.get("GP")), Integer.parseInt(row2.get("GP"))))
                .collect(Collectors.toList());

        // Update the table with the filtered data
        populateTable(filteredData);
    }

    // Reset filters and display all data
    private void applyNoFilter() {
        sorter.setSortKeys(null); // Clear sorting
        populateTable(allPlayerData); // Refresh the table with all data
    }

    // Helper method to get unique values for a specific column
    private String[] getUniqueValues(String column, boolean includeAllOption) {
        List<String> values = allPlayerData.stream()
                .map(row -> row.get(column))
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if (includeAllOption) {
            values.add(0, "All"); // Add the "All" option at the top
        }

        return values.toArray(new String[0]);
    }

    // Method to return the JTable, so it can be accessed by nbaGUI
    public JTable getTable() {
        return table;
    }
}
