package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the price table from disk and computes the price of a journey between
 * two stations, based on the number of fare zones crossed.
 *
 * Zone-counting rules (simplified, as described in the design manual):
 *   - Same line:      |index(originZone) - index(destinationZone)|
 *   - Different line:  index(originZone) + index(destinationZone)
 * where the index is the position of the zone in the lexicographically ordered
 * zone list. If the number of zones exceeds the maximum present in the tariff
 * file, the maximum tariff is applied.
 */
public class TariffCalculator {

    private static final String TARIFFS_FILE = "config/tariffs.csv";

    private final List<String> zoneList;
    private final List<BigDecimal> tariffs = new ArrayList<>();

    public TariffCalculator(List<String> zoneList) {
        this.zoneList = zoneList;
        loadTariffs(TARIFFS_FILE);
    }

    public BigDecimal calculatePrice(TrainStation origin, TrainStation destination) {
        int zonesCrossed = countZones(origin, destination);
        int maxZones = tariffs.size() - 1;
        if (zonesCrossed > maxZones) {
            zonesCrossed = maxZones;
        }
        return tariffs.get(zonesCrossed);
    }

    private int countZones(TrainStation origin, TrainStation destination) {
        int originIndex = zoneList.indexOf(origin.getZone());
        int destinationIndex = zoneList.indexOf(destination.getZone());
        if (origin.getLine().equals(destination.getLine())) {
            return Math.abs(originIndex - destinationIndex);
        }
        return originIndex + destinationIndex;
    }

    private void loadTariffs(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.replace("\"", "").split(",");
                BigDecimal price = new BigDecimal(fields[1].trim());
                tariffs.add(price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
