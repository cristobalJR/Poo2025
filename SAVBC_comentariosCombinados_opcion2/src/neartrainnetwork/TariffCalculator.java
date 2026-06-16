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
// EXTENSION ->
/**
 * Carga la tabla de precios del fichero y calcula el precio de un viaje entre
 * dos estaciones según el número de ZONAS que se atraviesan.
 *
 * La lógica de zonas es la "simplificada" del manual y se apoya en la lista de
 * zonas ordenada alfabéticamente (p. ej. [A, B1, B2, B3, C1, C2, E1, Zona Verde]).
 * El "índice" de una zona es su posición en esa lista (A=0, B1=1, B2=2...):
 *   - Misma línea:    valor absoluto de (índice(origen) - índice(destino))
 *   - Distinta línea:  índice(origen) + índice(destino)
 * Si el resultado supera el máximo de zonas que hay en el fichero de tarifas,
 * se cobra la tarifa máxima.
 *
 * La tabla de precios se guarda en una lista donde la posición = nº de zonas y
 * el valor = precio. Así, tariffs.get(3) es el precio de atravesar 3 zonas.
 *
 * El dinero se maneja con BigDecimal (no con double) para no arrastrar errores
 * de redondeo con los decimales.
 */
public class TariffCalculator {

    private static final String TARIFFS_FILE = "config/tariffs.csv";

    private final List<String> zoneList;                        // EXTENSION -> zonas ordenadas
    private final List<BigDecimal> tariffs = new ArrayList<>();  // EXTENSION -> precio por nº de zonas

    public TariffCalculator(List<String> zoneList) {
        this.zoneList = zoneList;
        loadTariffs(TARIFFS_FILE);
    }

    /** Precio entre dos estaciones (si se pasa del máximo, se cobra la tarifa maxima)*/
    // EXTENSION ->
    /** Precio del viaje entre dos estaciones. */
    public BigDecimal calculatePrice(TrainStation origin, TrainStation destination) {
        int zonesCrossed = countZones(origin, destination);
        int maxZones = tariffs.size() - 1; // EXTENSION -> máximo nº de zonas que hay en la tabla
        if (zonesCrossed > maxZones) {
            zonesCrossed = maxZones;       // EXTENSION -> si nos pasamos, se aplica la tarifa máxima
        }
        return tariffs.get(zonesCrossed);
    }

    /** Zonas que se atraviesan: misma línea, la distancia entre zonas, distinta línea, su suma. */
    // EXTENSION ->
    /** Calcula cuántas zonas se atraviesan, según la regla del manual. */
    private int countZones(TrainStation origin, TrainStation destination) {
        int originIndex = zoneList.indexOf(origin.getZone());
        int destinationIndex = zoneList.indexOf(destination.getZone());
        if (origin.getLine().equals(destination.getLine())) {
            return Math.abs(originIndex - destinationIndex); // EXTENSION -> misma línea
        }
        return originIndex + destinationIndex;               // EXTENSION -> distinta línea
    }

    /** Lee el CSV de tarifas, la posición en la lista coincide con el número de zonas. */
    // EXTENSION ->
    /**
     * Lee el fichero de tarifas (formato: "Zonas atravesadas","Precio"). Solo
     * nos quedamos con el precio de cada fila; su posición en la lista coincide
     * con el número de zonas (fila 0 -> 0 zonas, fila 1 -> 1 zona...).
     */
    private void loadTariffs(String fileName) {
        // EXTENSION -> Patrón clásico try/catch/finally con cierre explícito en el finally.
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine(); // EXTENSION -> saltamos la cabecera
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                // EXTENSION -> Quitamos las comillas y separamos por comas; fields[1] es el precio.
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
