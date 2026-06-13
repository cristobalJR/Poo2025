package neartrainnetwork;

import java.math.BigDecimal;

/**
 * "Carpeta" con los datos de la compra en curso. Las pantallas se van pasando
 * este objeto y lo van rellenando (primero el origen, luego el destino...).
 *
 * Además da acceso a los servicios compartidos que necesitan las pantallas: el
 * gestor de idiomas (para la pantalla de idioma) y la red de trenes (para
 * calcular el precio). Si una pantalla nueva necesita algo compartido, lo más
 * cómodo es exponerlo aquí, que es el objeto que viaja por todas las pantallas.
 */
public class OperationContext {

    private TrainStation origin;
    private TrainStation destination;
    private Translator translator;                       // idioma actual
    private final TranslatorManager translatorManager;   // servicio de idiomas
    private final TrainNetwork trainNetwork;             // servicio de red / precios

    public OperationContext(TranslatorManager translatorManager, TrainNetwork trainNetwork) {
        this.translatorManager = translatorManager;
        this.trainNetwork = trainNetwork;
        // Arrancamos con el idioma actual del gestor (por defecto, español).
        this.translator = translatorManager.getCurrentTranslator();
    }

    public TrainStation getOrigin() {
        return origin;
    }

    public TrainStation getDestination() {
        return destination;
    }

    public Translator getTranslator() {
        return translator;
    }

    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }

    public TrainNetwork getTrainNetwork() {
        return trainNetwork;
    }

    public void setOrigin(TrainStation origin) {
        this.origin = origin;
    }

    public void setDestination(TrainStation destination) {
        this.destination = destination;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    /** Vacía origen y destino para empezar una compra nueva desde cero. */
    public void reset() {
        this.origin = null;
        this.destination = null;
    }

    /** Precio del viaje actual (lo calcula la red de trenes con origen y destino). */
    public BigDecimal getPrice() {
        return trainNetwork.getPrice(origin, destination);
    }

    /**
     * Resumen legible y TRADUCIDO de la operación (origen, destino y precio).
     * Se usa como descripción en la pantalla de pago y como base del billete.
     * Nota: aquí origen y destino ya deben estar fijados (se llama en el pago).
     */
    public String getDescription() {
        BigDecimal price = getPrice();
        return translator.translate("Origen") + ": " + origin.getName() + "\n"
             + translator.translate("Destino") + ": " + destination.getName() + "\n"
             + translator.translate("El precio de la compra es") + " " + price + " " + "\u20AC";
    }
}
