package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcula el precio del billete según las zonas atravesadas.
 * Carga las tarifas desde el archivo tariffs.csv.
 */
public class TariffCalculator {
    private final List<String> zoneList;
    private final List<BigDecimal> tariffs;
    
    /**
     * Constructor que inicializa el calculador con una lista de zonas.
     * @param zoneList Lista ordenada de zonas (A, B1, B2, B3, C1, C2, E1, Zona Verde)
     */
    public TariffCalculator(List<String> zoneList) {
        this.zoneList = zoneList;
        this.tariffs = new ArrayList<>();
        loadTariffs("config/tariffs.csv");
    }
    
    /**
     * Carga las tarifas desde el archivo CSV.
     */
    private void loadTariffs(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Saltar cabecera
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    // Limpiar comillas y espacios
                    String priceStr = parts[1].replace("\"", "").trim();
                    BigDecimal price = new BigDecimal(priceStr);
                    tariffs.add(price);
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando tarifas: " + e.getMessage());
        }
    }
    
    /**
     * Calcula el precio del billete entre dos estaciones.
     * @param origin Estación de origen
     * @param destination Estación de destino
     * @return Precio del billete en euros
     */
    public BigDecimal calculatePrice(TrainStation origin, TrainStation destination) {
        int zonesCrossed = calculateZonesCrossed(origin, destination);
        
        // Si excede el máximo, usar la tarifa más alta
        if (zonesCrossed >= tariffs.size()) {
            return tariffs.get(tariffs.size() - 1);
        }
        
        return tariffs.get(zonesCrossed);
    }
    
    /**
     * Calcula el número de zonas atravesadas según la lógica especificada:
     * - Misma línea: distancia entre zonas en la lista ordenada
     * - Diferente línea: suma de distancias desde la zona A
     */
    private int calculateZonesCrossed(TrainStation origin, TrainStation destination) {
        String originZone = origin.getZone();
        String destZone = destination.getZone();
        String originLine = origin.getLine();
        String destLine = destination.getLine();
        
        int originIndex = zoneList.indexOf(originZone);
        int destIndex = zoneList.indexOf(destZone);
        
        // Si alguna zona no está en la lista, devolver 0
        if (originIndex == -1 || destIndex == -1) {
            return 0;
        }
        
        // Misma línea: distancia absoluta entre índices
        if (originLine.equals(destLine)) {
            return Math.abs(destIndex - originIndex);
        }
        
        // Diferentes líneas: suma de distancias desde la zona A (índice 0)
        return originIndex + destIndex;
    }
}