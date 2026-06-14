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
    private boolean largeFamily;                         // ¿tiene tarjeta de familia numerosa?
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

    /** ¿El usuario ha declarado tener tarjeta de familia numerosa? */
    public boolean isLargeFamily() {
        return largeFamily;
    }

    public void setLargeFamily(boolean largeFamily) {
        this.largeFamily = largeFamily;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    /** Vacía origen, destino y el descuento para empezar una compra nueva desde cero. */
    public void reset() {
        this.origin = null;
        this.destination = null;
        this.largeFamily = false;
    }

    /**
     * Precio del viaje actual (lo calcula la red de trenes con origen y destino).
     * Si el usuario tiene tarjeta de familia numerosa se aplica un 20% de
     * descuento: precio - precio*0.2 = precio*0.8. Se deja a 2 decimales (euros);
     * con las tarifas del fichero el resultado es exacto, no hace falta redondear.
     */
    public BigDecimal getPrice() {
        BigDecimal price = trainNetwork.getPrice(origin, destination);
        if (largeFamily) {
            price = price.multiply(new BigDecimal("0.8")).setScale(2);
        }
        return price;
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
