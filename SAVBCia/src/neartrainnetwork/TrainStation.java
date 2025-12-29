package neartrainnetwork;

import java.util.Objects;

/**
 * Representa una estación de tren de la red de Cercanías.
 * Implementa Comparable para permitir ordenación alfabética.
 */
public class TrainStation implements Comparable<TrainStation> {
    private final String name;
    private final String zone;
    private final String line;
    
    /**
     * Constructor de una estación de tren.
     * @param name Nombre de la estación
     * @param zone Zona tarifaria (A, B1, B2, B3, C1, C2, E1, Zona Verde)
     * @param line Línea de cercanías (C-1, C-2, etc.)
     */
    public TrainStation(String name, String zone, String line) {
        this.name = name;
        this.zone = zone;
        this.line = line;
    }
    
    public String getName() {
        return name;
    }
    
    public String getZone() {
        return zone;
    }
    
    public String getLine() {
        return line;
    }
    
    /**
     * Compara estaciones alfabéticamente por nombre.
     */
    @Override
    public int compareTo(TrainStation other) {
        return this.name.compareTo(other.name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TrainStation other = (TrainStation) obj;
        return Objects.equals(name, other.name) && 
               Objects.equals(zone, other.zone) && 
               Objects.equals(line, other.line);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, zone, line);
    }
}