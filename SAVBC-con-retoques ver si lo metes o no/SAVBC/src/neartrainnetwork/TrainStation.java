package neartrainnetwork;

/**
 * Una estación de tren: su nombre, su zona tarifaria (A, B1, B2... Zona Verde)
 * y la línea a la que pertenece (C-1, C-5...).
 *
 * IDENTIDAD POR NOMBRE: dos estaciones se consideran "la misma" si tienen el
 * mismo nombre. Esto importa porque en el fichero una misma estación aparece en
 * varias líneas; al guardarlas en estructuras que evitan duplicados
 * (HashMap por nombre y TreeSet) queremos que cuente una sola vez. Por eso
 * equals(), hashCode() y compareTo() se basan SOLO en el nombre.
 *
 * Implementa Comparable (compara por nombre) para poder ordenarlas
 * alfabéticamente sin esfuerzo (por ejemplo dentro de un TreeSet).
 *
 * toString() devuelve el nombre: así, al ponerla en un botón del kiosco, se ve
 * directamente el nombre de la estación.
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

    /** Orden natural: alfabético por nombre (lo usa el TreeSet para ordenar). */
    @Override
    public int compareTo(TrainStation other) {
        return this.name.compareTo(other.name);
    }

    /** Dos estaciones son iguales si tienen el mismo nombre. */
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

    /** Coherente con equals(): el hash depende solo del nombre. */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /** Se muestra en los botones del kiosco, así que devolvemos el nombre. */
    @Override
    public String toString() {
        return name;
    }
}
