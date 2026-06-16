package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcula el precio de un viaje según el numero de zonas que se atraviesan.
 * Lee la tabla de precios de un fichero
 */
public class TariffCalculator {

    private static final String TARIFFS_FILE = "config/tariffs.csv";

    private final List<String> zoneList;
    private final List<BigDecimal> tariffs = new ArrayList<>();

    public TariffCalculator(List<String> zoneList) {
        this.zoneList = zoneList;
        loadTariffs(TARIFFS_FILE);
    }

    /** Precio entre dos estaciones (si se pasa del máximo, se cobra la tarifa maxima)*/
    public BigDecimal calculatePrice(TrainStation origin, TrainStation destination) {
        int zonesCrossed = countZones(origin, destination);
        int maxZones = tariffs.size() - 1;
        if (zonesCrossed > maxZones) {
            zonesCrossed = maxZones;
        }
        return tariffs.get(zonesCrossed);
    }

    /** Zonas que se atraviesan: misma línea, la distancia entre zonas, distinta línea, su suma. */
    private int countZones(TrainStation origin, TrainStation destination) {
        int originIndex = zoneList.indexOf(origin.getZone());
        int destinationIndex = zoneList.indexOf(destination.getZone());
        if (origin.getLine().equals(destination.getLine())) {
            return Math.abs(originIndex - destinationIndex);
        }
        return originIndex + destinationIndex;
    }

    /** Lee el CSV de tarifas, la posición en la lista coincide con el número de zonas. */
    private void loadTariffs(String fileName) {
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
                BigDecimal price = new BigDecimal(fields[1].trim());
                tariffs.add(price);
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
}
