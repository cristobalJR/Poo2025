package neartrainnetwork;

/**
 * Una estación de tren:nombre, zona tarifaria y línea. Dos estaciones se consideran iguales 
 * si tienen el mismo nombre
 */
public class TrainStation implements Comparable<TrainStation> {

    private final String name;
    private final String zone;
    private final String line;

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

    @Override
    public int compareTo(TrainStation other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TrainStation)) {
            return false;
        }
        TrainStation other = (TrainStation) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
