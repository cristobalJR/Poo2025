package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Gestiona la red de estaciones de tren y el cálculo de precios.
 * Carga las estaciones desde stations.csv y utiliza TariffCalculator para los precios.
 */
public class TrainNetwork {
    private final TreeSet<TrainStation> stationSet;
    private final HashMap<String, TrainStation> stationMap;
    private final TariffCalculator calculator;
    
    /**
     * Constructor que carga la red de estaciones desde el archivo.
     */
    public TrainNetwork() {
        this.stationSet = new TreeSet<>();
        this.stationMap = new HashMap<>();
        
        // Cargar estaciones
        loadStationGraph("config/stations.csv");
        
        // Extraer zonas únicas y ordenarlas
        List<String> zoneList = extractSortedZones();
        
        // Crear calculador de tarifas
        this.calculator = new TariffCalculator(zoneList);
    }
    
    /**
     * Carga las estaciones desde el archivo CSV.
     */
    private void loadStationGraph(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Saltar cabecera
            
            while ((line = br.readLine()) != null) {
                // Parsear CSV con comillas
                String[] parts = parseCsvLine(line);
                
                if (parts.length >= 3) {
                    String lineName = parts[0].replace("\"", "").trim();
                    String stationName = parts[1].replace("\"", "").trim();
                    String zone = parts[2].replace("\"", "").trim();
                    
                    TrainStation station = new TrainStation(stationName, zone, lineName);
                    
                    // Usar TreeSet para mantener orden alfabético
                    stationSet.add(station);
                    
                    // HashMap para búsqueda rápida por nombre
                    // Si ya existe, mantener la primera (evita duplicados)
                    if (!stationMap.containsKey(stationName)) {
                        stationMap.put(stationName, station);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando estaciones: " + e.getMessage());
        }
    }
    
    /**
     * Parsea una línea CSV teniendo en cuenta las comillas.
     */
    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }
    
    /**
     * Extrae las zonas únicas de todas las estaciones y las ordena lexicográficamente.
     */
    private List<String> extractSortedZones() {
        Set<String> uniqueZones = new TreeSet<>(); // TreeSet para orden automático
        
        for (TrainStation station : stationSet) {
            uniqueZones.add(station.getZone());
        }
        
        return new ArrayList<>(uniqueZones);
    }
    
    /**
     * Calcula el precio del billete entre dos estaciones.
     */
    public BigDecimal getPrice(TrainStation origin, TrainStation destination) {
        return calculator.calculatePrice(origin, destination);
    }
    
    /**
     * Devuelve un array con todas las estaciones ordenadas alfabéticamente.
     */
    public TrainStation[] getStationArray() {
        return stationSet.toArray(new TrainStation[0]);
    }
    
    /**
     * Obtiene una estación por su nombre.
     * @param stationName Nombre de la estación
     * @return La estación o null si no existe
     */
    public TrainStation getStation(String stationName) {
        return stationMap.get(stationName);
    }
}