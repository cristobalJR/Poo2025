package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Loads, stores and provides the train stations and the price between any two
 * of them. Stations are loaded from a CSV file; the price is delegated to a
 * {@link TariffCalculator} that this class owns (composition).
 *
 * Two structures hold the stations:
 *   - a TreeSet, to keep them unique and alphabetically ordered for the kiosk;
 *   - a HashMap keyed by name, for fast lookup by station name.
 */
public class TrainNetwork {

    private static final String STATIONS_FILE = "config/stations.csv";

    private final TreeSet<TrainStation> stationSet = new TreeSet<>();
    private final HashMap<String, TrainStation> stationMap = new HashMap<>();
    private final TariffCalculator calculator;

    public TrainNetwork() {
        loadStationGraph(STATIONS_FILE);
        calculator = new TariffCalculator(buildOrderedZoneList());
    }

    public BigDecimal getPrice(TrainStation origin, TrainStation destination) {
        return calculator.calculatePrice(origin, destination);
    }

    public TrainStation[] getStationArray() {
        return stationSet.toArray(new TrainStation[0]);
    }

    public TrainStation getStation(String stationName) {
        return stationMap.get(stationName);
    }

    private void loadStationGraph(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            reader.readLine(); // skip header: "Línea","Estacion","Zona"
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.replace("\"", "").split(",");
                String trainLine = fields[0].trim();
                String name = fields[1].trim();
                String zone = fields[2].trim();
                TrainStation station = new TrainStation(name, zone, trainLine);
                stationSet.add(station);
                stationMap.put(name, station);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Distinct zones of all stations, ordered lexicographically. */
    private List<String> buildOrderedZoneList() {
        TreeSet<String> zones = new TreeSet<>();
        for (TrainStation station : stationSet) {
            zones.add(station.getZone());
        }
        return new ArrayList<>(zones);
    }
}
