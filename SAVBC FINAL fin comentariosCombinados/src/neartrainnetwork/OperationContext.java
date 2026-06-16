package neartrainnetwork;

import java.math.BigDecimal;

/**
 * Guarda los datos de la compra en curso (origen, destino, idioma y descuento)
 * Las pantallas se van pasando este objeto y lo van rellenando. 
 * También daacceso al gestor de idiomas y a la red de trenes.
 */
public class OperationContext {

    private TrainStation origin;
    private TrainStation destination;
    private boolean largeFamily;
    private Translator translator;
    private final TranslatorManager translatorManager;
    private final TrainNetwork trainNetwork;

    public OperationContext(TranslatorManager translatorManager, TrainNetwork trainNetwork) {
        this.translatorManager = translatorManager;
        this.trainNetwork = trainNetwork;
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

    public boolean isLargeFamily() {
        return largeFamily;
    }

    public void setLargeFamily(boolean largeFamily) {
        this.largeFamily = largeFamily;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    /** Deja origen, destino y descuento a cero para empezar una compra nueva. */
    public void reset() {
        this.origin = null;
        this.destination = null;
        this.largeFamily = false;
    }

    /** Precio del viaje; si hay tarjeta de familia numerosa, aplica un 20% de descuento. */
    public BigDecimal getPrice() {
        BigDecimal price = trainNetwork.getPrice(origin, destination);
        if (largeFamily) {
            price = price.multiply(new BigDecimal("0.8")).setScale(2);
        }
        return price;
    }

    /** Resumen traducido de la operación (origen, destino y precio). */
    public String getDescription() {
        BigDecimal price = getPrice();
        return translator.translate("Origen") + ": " + origin.getName() + "\n"
             + translator.translate("Destino") + ": " + destination.getName() + "\n"
             + translator.translate("El precio de la compra es") + " " + price + " " + "\u20AC";
    }
}
