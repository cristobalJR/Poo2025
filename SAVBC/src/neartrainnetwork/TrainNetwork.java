package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Carga las estaciones desde un CSV y da el precio entre dos de ellas (lo
 * calcula con un TariffCalculator). Las guarda ordenadas y sin repetir, y
 * también en un mapa por nombre para buscarlas rápido.
 */
public class TrainNetwork {

    private static final String STATIONS_FILE = "config/stations.csv";
    private static final String WORKS_FILE = "config/Obras.txt";

    private final TreeSet<TrainStation> stationSet = new TreeSet<>();
    private final HashMap<String, TrainStation> stationMap = new HashMap<>();
    private final Set<String> stationsUnderWorks = new HashSet<>();
    private final TariffCalculator calculator;

    public TrainNetwork() {
        loadStationGraph(STATIONS_FILE);
        loadStationsUnderWorks(WORKS_FILE);
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

    public boolean isStationUnderWorks(TrainStation station) {
        return stationsUnderWorks.contains(station.getName());
    }

    /** Lee el CSV de estaciones (columnas: línea, estación y zona). */
    private void loadStationGraph(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
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
            System.out.println(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    /** Lee las estaciones en obras (un nombre por línea). */
    private void loadStationsUnderWorks(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                stationsUnderWorks.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    /** Devuelve las zonas distintas de las estaciones, ordenadas. */
    private List<String> buildOrderedZoneList() {
        TreeSet<String> zones = new TreeSet<>();
        for (TrainStation station : stationSet) {
            zones.add(station.getZone());
        }
        return new ArrayList<>(zones);
    }
}
