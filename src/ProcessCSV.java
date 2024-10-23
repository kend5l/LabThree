import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ProcessCSV {

    public static void main(String[] args) throws IOException {

        // reads csv file and parses data
        List<Map<String, String>> playerData = readCSV("src/2023_nba_player_stats.csv");
        nbaGUI gui = new nbaGUI(playerData);

    }

    // method to read csv file and return data as a list of maps
    // each row in the file is a map
    public static List<Map<String, String>> readCSV(String filename) throws IOException {

        List<Map<String, String>> records = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));

        // split the header line with a comma
        String[] headers = lines.get(0).split(",");

        // Process each line as a map with column names
        records = lines.stream()
                .skip(1) // Skip the header row
                .map(line -> line.split(",")) // split each line by comma
                .filter(values -> values.length == headers.length) // filter rows with correct length
                .map(values -> {
                    Map<String, String> record = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        record.put(headers[i], values[i]);
                    }
                    return record;
                })
                .collect(Collectors.toList());

        return records;

    }
}