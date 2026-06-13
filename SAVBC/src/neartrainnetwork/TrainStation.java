package neartrainnetwork;

/**
 * Models a single commuter train station: its name, fare zone and line.
 *
 * A station is identified by its name. This keeps it consistent with the way
 * {@link TrainNetwork} stores stations: a {@code HashMap<String, TrainStation>}
 * keyed by name and a {@code TreeSet<TrainStation>} that must not hold the same
 * station twice. Because of that, {@code equals}, {@code hashCode} and
 * {@code compareTo} are all based on the name.
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

    /** Natural order is alphabetical by station name (used by the TreeSet). */
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

    /** Shown on the kiosk buttons, so it must be the readable station name. */
    @Override
    public String toString() {
        return name;
    }
}
