package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Carga, guarda y ofrece las estaciones de tren y el precio entre dos de ellas.
 * Las estaciones se leen de un CSV; el cálculo del precio se delega en un
 * TariffCalculator que esta clase crea y posee (composición).
 *
 * Guardamos las estaciones en dos estructuras a la vez, cada una con su porqué:
 *   - un TreeSet: las mantiene SIN repetir y ORDENADAS alfabéticamente, que es
 *     justo como las queremos para el carrusel del kiosco.
 *   - un HashMap por nombre: para buscar una estación por su nombre al instante.
 *
 * Para cambiar de dónde se leen las estaciones, toca STATIONS_FILE (el fichero
 * de tarifas se configura dentro de TariffCalculator).
 */
public class TrainNetwork {

    private static final String STATIONS_FILE = "config/stations.csv";

    private final TreeSet<TrainStation> stationSet = new TreeSet<>();        // únicas y ordenadas
    private final HashMap<String, TrainStation> stationMap = new HashMap<>(); // búsqueda por nombre
    private final TariffCalculator calculator;

    public TrainNetwork() {
        loadStationGraph(STATIONS_FILE);
        // La lista de zonas (ordenada) se construye a partir de las estaciones
        // ya cargadas y se le pasa a la calculadora de tarifas.
        calculator = new TariffCalculator(buildOrderedZoneList());
    }

    /** Precio entre dos estaciones (lo resuelve la calculadora de tarifas). */
    public BigDecimal getPrice(TrainStation origin, TrainStation destination) {
        return calculator.calculatePrice(origin, destination);
    }

    /** Todas las estaciones, únicas y ordenadas alfabéticamente (para el carrusel). */
    public TrainStation[] getStationArray() {
        return stationSet.toArray(new TrainStation[0]);
    }

    /** Busca una estación por su nombre exacto (devuelve null si no existe). */
    public TrainStation getStation(String stationName) {
        return stationMap.get(stationName);
    }

    /**
     * Lee el CSV de estaciones (formato: "Línea","Estacion","Zona"). Una misma
     * estación puede aparecer en varias líneas; como TrainStation se identifica
     * por el nombre, el TreeSet la guarda una sola vez.
     */
    private void loadStationGraph(String fileName) {
        // Lectura con el patrón clásico try/catch/finally: el recurso se declara
        // fuera, se usa en el try y SIEMPRE se cierra en el finally con close().
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine(); // saltamos la cabecera
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                // Quitamos comillas y separamos por comas: línea, nombre, zona.
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
        } finally {
            // El cierre también puede lanzar IOException, así que se protege.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Zonas distintas de todas las estaciones, ordenadas alfabéticamente. */
    private List<String> buildOrderedZoneList() {
        TreeSet<String> zones = new TreeSet<>();
        for (TrainStation station : stationSet) {
            zones.add(station.getZone());
        }
        return new ArrayList<>(zones);
    }
}
